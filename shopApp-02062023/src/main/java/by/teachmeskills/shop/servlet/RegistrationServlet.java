package by.teachmeskills.shop.servlet;

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
        Map<String, String> credentials = new HashMap<>();
        credentials.put("NAME", req.getParameter("name"));
        credentials.put("SURNAME", req.getParameter("surname"));
        credentials.put("BIRTHDAY", req.getParameter("birthday"));
        credentials.put("EMAIL", req.getParameter("email"));
        credentials.put("PASSWORD", req.getParameter("password"));

        if (HttpRequestCredentialsValidator.validateCredentials(credentials)) {
            User user = User.newBuilder()
                    .withId()
                    .withName(credentials.get("NAME"))
                    .withSurname(credentials.get("SURNAME"))
                    .withBirthday(DateParser.parseToDate(credentials.get("BIRTHDAY")))
                    .withEmail(credentials.get("EMAIL"))
                    .withPassword(credentials.get("PASSWORD"))
                    .build();

            CRUDUtils.createUser(user);


            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            String varInfo = "Добро пожаловать, " + user.getName() + ".";
            req.setAttribute("info", varInfo);

            showCategories(req);
            RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
            rd.forward(req, resp);
        } else {
            String varInfo = "Введены неверные данные. Пожалуйста, введите данные повторно.";
            req.setAttribute("info", varInfo);
            RequestDispatcher rd = req.getRequestDispatcher("/registration.jsp");
            rd.forward(req, resp);
        }
    }

    private void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute("categories", categories);
    }
}
