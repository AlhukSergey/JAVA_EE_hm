package by.teachmeskills.servlet;

import by.teachmeskills.model.Calculator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/calculator")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> results = new ArrayList<>();
        Calculator calculator = new Calculator(5, 10);
        results.add(String.valueOf(calculator.sum()));
        results.add(String.valueOf(calculator.substr()));
        results.add(String.valueOf(calculator.multiply()));
        results.add(String.valueOf(calculator.div()));

        request.setAttribute("data", results);
        RequestDispatcher rd = request.getRequestDispatcher("/results.jsp");
        rd.forward(request, response);
    }
}
