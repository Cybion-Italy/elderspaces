package eu.elderspaces.recommendations.services;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author micheleminno
 */
public class ProductionServiceConfig extends GuiceServletContextListener {
    
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ProductionJerseyServletModule());
    }
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        //TODO clean up Twitter4j threads
        //took from granatum:
        /* final Injector injector = (Injector) servletContextEvent.getServletContext().getAttribute(Injector.class.getName()); */
        /* final QuartzScheduler scheduler = injector.getInstance(QuartzScheduler.class); */
        
    }
}
