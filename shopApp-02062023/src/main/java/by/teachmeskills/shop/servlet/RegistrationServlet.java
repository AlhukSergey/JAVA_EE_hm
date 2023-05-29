package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.EncryptionUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/registration.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(name, surname, email, password);

        ServletContext ctx = getServletContext();
        String sqlInsert = "INSERT INTO users (id, name, surname, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            DBConnectionManager dbConnectionManager = (DBConnectionManager) ctx.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement psInsert = connection.prepareStatement(sqlInsert);

            psInsert.setString(1, user.getID());
            psInsert.setString(2, user.getName());
            psInsert.setString(3, user.getSurname());
            psInsert.setBigDecimal(4, BigDecimal.valueOf(user.getBalance()));
            psInsert.setString(5, user.getEmail());
            psInsert.setString(6, EncryptionUtils.encrypt(user.getPassword()));
            psInsert.execute();

            psInsert.close();
            dbConnectionManager.closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }
}
