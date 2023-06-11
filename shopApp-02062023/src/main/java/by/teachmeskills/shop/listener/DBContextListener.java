package by.teachmeskills.shop.listener;

import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool pool = ConnectionPool.getInstance();
        CRUDUtils.setConnection(pool);
        System.out.println("Database connection initialized for application.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool pool = ConnectionPool.getInstance();
        CRUDUtils.closeConnection(pool);
        System.out.println("Database connection closed for application.");
    }
}
