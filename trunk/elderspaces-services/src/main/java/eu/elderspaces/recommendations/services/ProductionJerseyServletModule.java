package eu.elderspaces.recommendations.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.elderspaces.activities.core.ActivityManager;
import eu.elderspaces.activities.core.SimpleActivityManager;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.activities.persistence.InMemoryActivityRepository;
import eu.elderspaces.activities.services.ActivitiesService;
import eu.elderspaces.activities.services.StatusService;
import eu.elderspaces.recommendations.core.FakeStaticRecommender;
import eu.elderspaces.recommendations.core.Recommender;
import eu.elderspaces.recommendations.core.SimpleRecommender;

/**
 * @author micheleminno
 */
public class ProductionJerseyServletModule extends JerseyServletModule {
    
    public Properties properties;
    
    @Override
    protected void configureServlets() {
    
        final Map<String, String> initParams = new HashMap<String, String>();
        
        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
                ClasspathResourceConfig.class.getName());
        
        // bind REST services
        bind(RecommendationService.class);
        bind(FakeRecommendationService.class);
        bind(Recommender.class).to(SimpleRecommender.class);
        bind(FakeStaticRecommender.class);
        bind(ActivityManager.class).to(SimpleActivityManager.class);
        bind(ActivityRepository.class).to(InMemoryActivityRepository.class);
        
        // Temporarily:
        bind(ActivitiesService.class);
        bind(StatusService.class);
        
        // add bindings for Jackson
        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        // Route all requests through GuiceContainer
        serve("/rest/*").with(GuiceContainer.class);
        filter("/rest/*").through(GuiceContainer.class, initParams);
    }
}
