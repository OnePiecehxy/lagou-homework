package com.lagou.listener;

import com.lagou.utils.IocContainer;
import org.dom4j.DocumentException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        IocContainer iocContainer = new IocContainer();
        try {
            iocContainer.getIocContainer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
