package eu.elderspaces.activities;

import javax.servlet.ServletContextEvent;

import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import eu.elderspaces.activities.services.ProductionJerseyServletModule;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ProductionServiceConfig extends GuiceServletContextListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceConfig.class);
    
    @Override
    protected Injector getInjector() {
    
        return Guice.createInjector(new ProductionJerseyServletModule());
    }
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    
        // recreating injector still provides same istances
        // http://stackoverflow.com/questions/8356640/guice-how-to-share-the-same-singleton-instance-through-multiple-injectors-modu
        
        final Injector injector = Guice.createInjector(new ProductionJerseyServletModule());
        final Node node = injector.getInstance(Node.class);
        node.stop();
    }
    
}
