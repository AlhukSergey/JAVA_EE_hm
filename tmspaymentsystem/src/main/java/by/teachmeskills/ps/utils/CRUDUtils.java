package by.teachmeskills.ps.utils;

import by.teachmeskills.ps.model.AccountStatus;
import by.teachmeskills.ps.model.BankAccount;
import by.teachmeskills.ps.model.Merchant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String ADD_BANK_ACCOUNT_QUERY = "INSERT INTO bank_account (id, merchant_id, status, account_number, created_at) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_MERCHANT_BANK_ACCOUNTS_QUERY = "SELECT * FROM bank_account WHERE merchant_id = ? ORDER BY status ASC, created_at ASC";
    private static final String GET_BANK_ACCOUNT_QUERY = "SELECT FROM bank_account WHERE id = ?";
    private static final String UPDATE_STATUS_BANK_ACCOUNT_QUERY = "UPDATE bank_account SET status = ? WHERE id = ?";
    private static final String UPDATE_NUMBER_BANK_ACCOUNT_QUERY = "UPDATE bank_account SET account_number = ? WHERE id = ?";
    private static final String DELETE_BANK_ACCOUNT_QUERY = "UPDATE bank_account SET status = ? WHERE id = ?";

    private static final String GET_MERCHANT_QUERY = "SELECT * FROM merchant WHERE id = ?";
    private static final String GET_ALL_MERCHANTS_QUERY = "SELECT * FROM merchant";
    private static final String DELETE_MERCHANT_QUERY = "DELETE FROM merchant WHERE id = ?";
    private static final String ADD_MERCHANT_QUERY = "INSERT INTO merchant (id, name, created_at) VALUES (?, ?, ?)";

    private CRUDUtils() {
    }

    public static List<BankAccount> getMerchantBankAccounts(Merchant merchant) {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_MERCHANT_BANK_ACCOUNTS_QUERY)) {
            psGet.setString(1, merchant.getId());
            ResultSet set = psGet.executeQuery();
            while (set.next()) {
                bankAccounts.add(new BankAccount(set.getString(1),
                        set.getString(2),
                        ConverterUtils.toAccountStatus(set.getString(3)),
                        EncryptionUtils.decrypt(set.getString(4)),
                        set.getTimestamp(5).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankAccounts;
    }

    public static BankAccount updateStatusMerchantBankAccount(BankAccount bankAccount) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(UPDATE_STATUS_BANK_ACCOUNT_QUERY)
        ) {
            psUpdate.setString(1, bankAccount.getStatus().toString());
            psUpdate.setString(2, bankAccount.getId());
            psUpdate.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }

    public static BankAccount updateNumberMerchantBankAccount(BankAccount bankAccount) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(UPDATE_NUMBER_BANK_ACCOUNT_QUERY)
        ) {
            psUpdate.setString(1, EncryptionUtils.encrypt(bankAccount.getAccountNumber()));
            psUpdate.setString(2, bankAccount.getId());
            psUpdate.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }

    public static BankAccount createBankAccount(BankAccount bankAccount) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(ADD_BANK_ACCOUNT_QUERY)) {
            psInsert.setString(1, bankAccount.getId());
            psInsert.setString(2, bankAccount.getMerchantId());
            psInsert.setString(3, bankAccount.getStatus().name());
            psInsert.setString(4, EncryptionUtils.encrypt(bankAccount.getAccountNumber()));
            psInsert.setTimestamp(5, Timestamp.valueOf(bankAccount.getCreatedTime()));
            psInsert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }

    public static BankAccount getBankAccountById(String id) {
        BankAccount bankAccount = null;
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_BANK_ACCOUNT_QUERY)) {
            psGet.setString(1, id);
            ResultSet set = psGet.executeQuery();
            while (set.next()) {
                bankAccount = new BankAccount(set.getString(1),
                        set.getString(2),
                        ConverterUtils.toAccountStatus(set.getString(3)),
                        EncryptionUtils.decrypt(set.getString(4)),
                        set.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }

    public static void deleteBankAccountById(String id) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(DELETE_BANK_ACCOUNT_QUERY)) {
            psUpdate.setString(1, AccountStatus.DELETED.toString());
            psUpdate.setString(2, id);
            psUpdate.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Merchant createMerchant(Merchant merchant) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(ADD_MERCHANT_QUERY)) {
            psInsert.setString(1, merchant.getId());
            psInsert.setString(2, merchant.getName());
            psInsert.setTimestamp(3, Timestamp.valueOf(merchant.getCreatedAt()));
            psInsert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return merchant;
    }

    public static List<Merchant> getAllMerchants() {
        List<Merchant> merchants = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_ALL_MERCHANTS_QUERY)) {
            ResultSet set = psGet.executeQuery();
            while (set.next()) {
                merchants.add(new Merchant(set.getString(1),
                        set.getString(2),
                        set.getTimestamp(3).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return merchants;
    }

    public static Merchant getMerchantById(String id) {
        Merchant merchant = null;
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_MERCHANT_QUERY)) {
            psGet.setString(1, id);
            ResultSet set = psGet.executeQuery();
            while (set.next()) {
                merchant = new Merchant(set.getString(1),
                        set.getString(2),
                        set.getTimestamp(3).toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return merchant;
    }

    public static void deleteMerchantById(String id) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement psDelete = connection.prepareStatement(DELETE_MERCHANT_QUERY)) {
            psDelete.setString(1, id);
            psDelete.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
