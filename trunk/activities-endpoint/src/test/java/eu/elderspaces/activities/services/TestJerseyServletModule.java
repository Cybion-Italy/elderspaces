package eu.elderspaces.activities.services;

import it.cybion.commons.PropertiesHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.name.Names;
import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.elderspaces.activities.core.ActivityManager;
import eu.elderspaces.activities.core.SimpleActivityManager;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestJerseyServletModule extends JerseyServletModule {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TestJerseyServletModule.class);
    
    @Override
    protected void configureServlets() {
    
        LOGGER.debug("configuring servlets");
        final Map<String, String> initParams = new HashMap<String, String>();
        // TODO check if jersey wadl can be configured here
        // initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES,
        // "eu.granatum.wp5.services");
        
        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
                ClasspathResourceConfig.class.getName());
        
        final Properties properties = PropertiesHelper
                .readFromClasspath("/activities-endpoint.properties");
        // binds the keynames as @Named annotations
        Names.bindProperties(binder(), properties);
        
        // add bindings to mockups of backend classes
        
        // bind REST services
        bind(StatusService.class);
        bind(ActivitiesService.class);
        bind(ActivityManager.class).to(SimpleActivityManager.class);
        
        // add bindings for Jackson json serialization
        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        // Route all requests through GuiceContainer
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(GuiceContainer.class, initParams);
        LOGGER.debug("configured servlets");
    }
}
