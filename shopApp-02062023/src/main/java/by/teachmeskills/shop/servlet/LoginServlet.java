package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.EncryptionUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        validateCredentials(email, password);

        ServletContext context = getServletContext();
        DBConnectionManager dbConnectionManager = (DBConnectionManager) context.getAttribute("DBManager");
        Connection connection = dbConnectionManager.getConnection();

        User user = findUser(connection, email);

        RequestDispatcher rd;
        String varInfo;
        if (user != null && user.getPassword().equals(password)) {
            req.setAttribute(user.getName(), user);
            varInfo = "Добро пожаловать, " + user.getName() + ".";
            req.setAttribute("info", varInfo);

            addCategories(connection, req);

            /*dbConnectionManager.closeConnection();*/
            rd = req.getRequestDispatcher("/home.jsp");
        } else {
            varInfo = "Введены неверные данные. Пожалуйста, введите данные повторны либо перейдите на страницу регистрации.";
            req.setAttribute("info", varInfo);
            rd = req.getRequestDispatcher("/login.jsp");
        }
        rd.forward(req, resp);
    }

    private void validateCredentials(String email, String password) {
        try {
            HttpRequestCredentialsValidator.validateCredentialNotNull(email);
            HttpRequestCredentialsValidator.validateCredentialNotNull(password);
        } catch (RequestCredentialsNullException e) {
            System.out.println(e.getMessage());
        }
    }

    private User findUser(Connection connection, String email) {
        User user = null;
        try {
            PreparedStatement psGet = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = new User(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBigDecimal(4).doubleValue(),
                        resultSet.getString(5),
                        EncryptionUtils.decrypt(resultSet.getString(6)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private void addCategories(Connection connection, HttpServletRequest req) {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement psGet = connection.prepareStatement("SELECT * FROM categories");
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
            req.setAttribute("categories", categories);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

