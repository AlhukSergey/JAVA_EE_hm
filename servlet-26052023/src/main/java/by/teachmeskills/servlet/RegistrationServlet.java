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
import java.sql.SQLException;

@WebServlet("/create")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(req, resp);
    }


    protected void processRequest(HttpServletRequest req) {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(name, age, email, password);

        ServletContext ctx = getServletContext();
        String sqlInsert = "INSERT INTO users (id, name, age, email, password) VALUES (?, ?, ?, ?, ?)";
        try {
            DBConnectionManager dbConnectionManager = (DBConnectionManager) ctx.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement psInsert = connection.prepareStatement(sqlInsert);

            psInsert.setString(1, user.getID());
            psInsert.setString(2, user.getName());
            psInsert.setInt(3, user.getAge());
            psInsert.setString(4, user.getEmail());
            psInsert.setString(5, EncryptionUtils.encrypt(user.getPassword()));
            psInsert.execute();

            psInsert.close();
            dbConnectionManager.closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
