package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.model.Category;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/registration.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("NAME", req.getParameter("name"));
        userData.put("SURNAME", req.getParameter("surname"));
        userData.put("BIRTHDAY", req.getParameter("birthday"));
        userData.put("EMAIL", req.getParameter("email"));
        userData.put("PASSWORD", req.getParameter("password"));

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = "Введены некорректные  данные. " + e.getMessage();
            req.setAttribute("info", varInfo);
            RequestDispatcher rd = req.getRequestDispatcher("/registration.jsp");
            rd.forward(req, resp);
        }

        User user = User.newBuilder()
                .withId()
                .withName(userData.get("NAME"))
                .withSurname(userData.get("SURNAME"))
                .withBirthday(DateParser.parseToDate(userData.get("BIRTHDAY")))
                .withEmail(userData.get("EMAIL"))
                .withPassword(userData.get("PASSWORD"))
                .build();

        CRUDUtils.createUser(user);


        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        String varInfo = "Добро пожаловать, " + user.getName() + ".";
        req.setAttribute("info", varInfo);

        showCategories(req);
        RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
        rd.forward(req, resp);
    }

    private void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute("categories", categories);
    }
}
