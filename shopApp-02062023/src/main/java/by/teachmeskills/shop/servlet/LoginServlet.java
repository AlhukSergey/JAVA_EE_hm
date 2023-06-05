package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.forward(req, resp);
        } else {
            showCategories(req);
            RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        validateCredentials(email, password);

        ServletContext context = getServletContext();
        User user = CRUDUtils.getUser(email, context);

        RequestDispatcher rd;
        String varInfo;
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            varInfo = "Добро пожаловать, " + user.getName() + ".";
            req.setAttribute("info", varInfo);

            showCategories(req);
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

    private void showCategories(HttpServletRequest req) {
        ServletContext context = getServletContext();
        List<Category> categories = CRUDUtils.getCategories(context);
        req.setAttribute("categories", categories);
    }
}

