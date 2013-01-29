package eu.elderspaces.activities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import eu.elderspaces.activities.services.ProductionJerseyServletModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ProductionServiceConfig extends GuiceServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceConfig.class);

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ProductionJerseyServletModule()
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
