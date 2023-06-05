package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.CRUDUtils;
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

        ServletContext context = getServletContext();
        CRUDUtils.createUser(user, context);

        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }
}
