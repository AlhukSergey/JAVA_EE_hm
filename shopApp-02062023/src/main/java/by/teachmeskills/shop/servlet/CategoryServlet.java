package by.teachmeskills.shop.servlet;

import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.Product;
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

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("id");
        String categoryName = req.getParameter("name");
        List<Product> products = addProducts(categoryId);
        req.setAttribute("categoryName", categoryName);
        req.setAttribute("products", products);

        if (products.isEmpty()) {
            resp.getWriter().write("Продукты из данной категории еще не поступили в продажу.");
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("category.jsp");
            rd.forward(req, resp);
        }
    }

    private List<Product> addProducts(String categoryId) {
        List<Product> products = new ArrayList<>();

        ServletContext context = getServletContext();
        try {
            DBConnectionManager dbConnectionManager = (DBConnectionManager) context.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();
            PreparedStatement psGet = connection.prepareStatement("SELECT * FROM products WHERE categoryId = ?");
            psGet.setInt(1, Integer.parseInt(categoryId));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
}
