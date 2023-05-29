package by.teachmeskills.shop.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        String url = ctx.getInitParameter("dbUrl");
        String login = ctx.getInitParameter("dbUser");
        String password = ctx.getInitParameter("dbPass");

        DBConnectionManager dbManager = new DBConnectionManager(url, login, password);
        ctx.setAttribute("DBManager", dbManager);
        System.out.println("Database connection initialized for application.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        DBConnectionManager dbManager = (DBConnectionManager) ctx.getAttribute("DBManager");
        dbManager.closeConnection();
        System.out.println("Database connection closed for application.");
    }
}
