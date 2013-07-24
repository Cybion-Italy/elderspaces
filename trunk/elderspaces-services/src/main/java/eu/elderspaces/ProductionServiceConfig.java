package eu.elderspaces;

import it.cybion.commons.exceptions.RepositoryException;

import javax.servlet.ServletContextEvent;

import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import eu.elderspaces.persistence.EnrichedEntitiesRepository;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ProductionServiceConfig extends GuiceServletContextListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceConfig.class);
    
    @Override
    protected Injector getInjector() {
    
        return GuiceFactory.getInjector();
    }
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    
        // recreating injector still provides different instances even if
        // defined @Singleton
        // http://stackoverflow.com/questions/8356640/guice-how-to-share-the-same-singleton-instance-through-multiple-injectors-modu
        LOGGER.info("Gently shutting down services...");
        final Injector injector = GuiceFactory.getInjector();
        try {
            injector.getInstance(Node.class).client().close();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            injector.getInstance(Node.class).stop();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            injector.getInstance(SocialNetworkRepository.class).shutdown();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
        
        try {
            injector.getInstance(EntitiesRepository.class).shutdown();
        } catch (final RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        try {
            injector.getInstance(EnrichedEntitiesRepository.class).shutdown();
        } catch (final RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Services terminated!");
    }
}
