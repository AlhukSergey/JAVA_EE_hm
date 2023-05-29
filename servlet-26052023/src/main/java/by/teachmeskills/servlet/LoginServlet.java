package by.teachmeskills.servlet;

import by.teachmeskills.listener.DBConnectionManager;
import by.teachmeskills.model.User;
import by.teachmeskills.utils.EncryptionUtils;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        ServletContext ctx = getServletContext();
        try {
            DBConnectionManager dbConnectionManager = (DBConnectionManager) ctx.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement psGet = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = new User(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        EncryptionUtils.decrypt(resultSet.getString(5)));
            }

            resultSet.close();

            if (user != null && user.getPassword().equals(password)) {
                dbConnectionManager.closeConnection();

                String varInfo = "Данные верны. Добро пожаловать, " + user.getName() + ".";
                req.setAttribute("info", varInfo);
                RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
                rd.forward(req, resp);
            } else {
                String varInfo = "Введены неверные данные. Пожалуйста, введите данные повторны либо перейдите на страницу регистрации.";
                req.setAttribute("info", varInfo);
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
