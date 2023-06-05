package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.model.Product;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("id");
        String categoryName = req.getParameter("name");

        ServletContext context = getServletContext();
        List<Product> products = CRUDUtils.getCategoryProducts(categoryId, context);
        req.setAttribute("categoryName", categoryName);
        req.setAttribute("products", products);

        if (products.isEmpty()) {
            resp.getWriter().write("Продукты из данной категории еще не поступили в продажу.");
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("category.jsp");
            rd.forward(req, resp);
        }
    }
}
