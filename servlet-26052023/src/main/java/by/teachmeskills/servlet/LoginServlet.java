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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = new ArrayList<>();


        /*Предположил, что в базе данных у пользователя на данную почту может существовать несколько аккаунтов, поэтому
        * использовал инструкцию для считывания из базы данных сразу несколько аккаунтов, и в случаи совпадении пароля в
        * любом из, перебрасываю на страницу приветствия*/

        User user;
        String varInfo;

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
                users.add(new User(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        EncryptionUtils.decrypt(resultSet.getString(5))));
            }

            resultSet.close();
            dbConnectionManager.closeConnection();

            user = users.stream().filter(s -> s.getPassword().equals(password)).findAny().orElse(null);

            while (user == null) {
                varInfo = "Введены ошибочные данные. Введите данные повторно, либо перейдите на страницу регистрации.";
                req.setAttribute("info", varInfo);
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
            }

            if (user == null) {


                //При повторном переходе на страницу "login" данные получается ввести, но вместо ожидаемого переброса на
                //страницу "home" соединение разрывается и программа останавливается.


                varInfo = "Введены ошибочные данные. Введите данные повторно, либо перейдите на страницу регистрации.";
                req.setAttribute("info", varInfo);
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
            } else {
                varInfo = "Данные верны. Добро пожаловать, " + user.getName() + ".";
                req.setAttribute("info", varInfo);
                RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
                rd.forward(req, resp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
