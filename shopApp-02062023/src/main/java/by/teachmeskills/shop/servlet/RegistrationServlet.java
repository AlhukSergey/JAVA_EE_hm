package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.DateParser;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        String birthday = req.getParameter("birthday");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            HttpRequestCredentialsValidator.validateCredentialNotNull(name);
            HttpRequestCredentialsValidator.validateCredentialNotNull(surname);
            HttpRequestCredentialsValidator.validateCredentialNotNull(birthday);
            HttpRequestCredentialsValidator.validateCredentialNotNull(email);
            HttpRequestCredentialsValidator.validateCredentialNotNull(password);
        } catch (RequestCredentialsNullException e) {
            System.out.println(e.getMessage());
        }

        User user = User.newBuilder()
                .withId()
                .withName(name)
                .withSurname(surname)
                .withBirthday(DateParser.parseToDate(birthday))
                .withEmail(email)
                .withPassword(password)
                .build();

        CRUDUtils.createUser(user);

        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }
}
