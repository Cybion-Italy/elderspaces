package eu.elderspaces.recommendations.services;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestServiceConfig extends GuiceServletContextListener {
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        // useful to teardown database stuff
    }
    
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new TestJerseyServletModule());
    }
}
