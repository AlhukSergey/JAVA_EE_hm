package by.teachmeskills.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        String url = ctx.getInitParameter("dburl");
        String login = ctx.getInitParameter("dbuser");
        String password = ctx.getInitParameter("dbpass");

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
