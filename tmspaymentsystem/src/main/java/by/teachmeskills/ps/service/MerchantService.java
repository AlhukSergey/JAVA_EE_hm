package by.teachmeskills.ps.service;

import by.teachmeskills.ps.exceptions.BankAccountNotFoundException;
import by.teachmeskills.ps.exceptions.MerchantNotFoundException;
import by.teachmeskills.ps.exceptions.NoBankAccountsFoundException;
import by.teachmeskills.ps.model.AccountStatus;
import by.teachmeskills.ps.model.BankAccount;
import by.teachmeskills.ps.model.Merchant;
import by.teachmeskills.ps.utils.CRUDUtils;

import java.util.List;
import java.util.Optional;

public class MerchantService {
    public void addBankAccount(Merchant merchant, String accountNumber) {
        BankAccount bankAccount = new BankAccount(merchant.getId(), accountNumber);
        validAccountNumber(bankAccount.getAccountNumber());
        List<BankAccount> bankAccounts = CRUDUtils.getMerchantBankAccounts(merchant);
        Optional<BankAccount> account = bankAccounts.stream().filter(s -> s.getAccountNumber().equals(bankAccount.getAccountNumber())).findAny();
        account.ifPresentOrElse(a -> Optional.of(a).filter(s -> s.getStatus().equals(AccountStatus.DELETED)).ifPresent(s -> {
            s.setStatus(AccountStatus.ACTIVE);
            CRUDUtils.updateStatusMerchantBankAccount(s);
        }), () -> CRUDUtils.createBankAccount(bankAccount));
    }

    public List<BankAccount> getMerchantBankAccounts(Merchant merchant) throws NoBankAccountsFoundException {
        List<BankAccount> accounts = CRUDUtils.getMerchantBankAccounts(merchant);
        if (accounts.isEmpty()) {
            throw new NoBankAccountsFoundException("There is no any bank accounts yet!");
        }
        return accounts;
    }

    public void updateBankAccount(String bankAccountId, String newAccountNumber, Merchant merchant) throws BankAccountNotFoundException, IllegalArgumentException {
        validAccountNumber(newAccountNumber);
        List<BankAccount> accounts = CRUDUtils.getMerchantBankAccounts(merchant);
        Optional<BankAccount> account = accounts.stream().filter(s -> s.getId().equals(bankAccountId)).findAny();
        account.ifPresentOrElse(a -> {
            a.setAccountNumber(newAccountNumber);
            CRUDUtils.updateNumberMerchantBankAccount(a);
        }, () -> {
            try {
                throw new BankAccountNotFoundException("No bank account found!");
            } catch (BankAccountNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteBankAccount(String bankAccountId, Merchant merchant) throws BankAccountNotFoundException {
        List<BankAccount> accounts = CRUDUtils.getMerchantBankAccounts(merchant);
        accounts.stream().filter(s -> s.getId().equals(bankAccountId)).findAny().ifPresentOrElse(a -> CRUDUtils.deleteBankAccountById(bankAccountId),
                () -> {
                    try {
                        throw new BankAccountNotFoundException("No bank account found!");
                    } catch (BankAccountNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void createMerchant(String merchantName) {
        Merchant merchant = new Merchant(merchantName);
        CRUDUtils.createMerchant(merchant);
    }

    public List<Merchant> getMerchants() throws MerchantNotFoundException {
        List<Merchant> merchants = CRUDUtils.getAllMerchants();
        if (merchants.isEmpty()) {
            throw new MerchantNotFoundException("No such merchant found!");
        }
        return merchants;
    }

    public String showMerchant(String merchantId) throws MerchantNotFoundException {
        Merchant merchant = CRUDUtils.getMerchantById(merchantId);
        if (merchant == null) {
            throw new MerchantNotFoundException("No such merchant found!");
        }
        return merchant.toString();
    }

    public void deleteMerchant(String id) throws MerchantNotFoundException {
        Merchant merchant = CRUDUtils.getMerchantById(id);
        if (merchant == null) {
            throw new MerchantNotFoundException("No such merchant found!");
        }
        CRUDUtils.deleteMerchantById(id);
    }

    public Merchant getMerchantById(String merchantId) throws MerchantNotFoundException {
        Merchant merchant = CRUDUtils.getMerchantById(merchantId);
        if (merchant == null) {
            throw new MerchantNotFoundException("No such merchant found!");
        }
        return merchant;
    }

    private void validAccountNumber(String bankAccount) throws IllegalArgumentException {
        if (!bankAccount.matches("[0-9]{8}")) {
            throw new IllegalArgumentException("The account number contains incorrect dates.");
        }
    }
}
