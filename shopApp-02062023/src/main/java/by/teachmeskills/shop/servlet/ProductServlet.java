package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.model.Product;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("id");

        Product product = CRUDUtils.getProductById(productId);
        req.setAttribute("product", product);

        RequestDispatcher rd = req.getRequestDispatcher("product.jsp");
        rd.forward(req, resp);
    }
}
