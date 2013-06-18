package eu.elderspaces.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import eu.elderspaces.model.utils.ActivityStreamObjectMapper;
import it.cybion.commons.PropertiesHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.Client;

import java.util.Properties;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ActivityStreamTestModule extends AbstractModule {

    @Override
    protected void configure() {
        final Properties properties = PropertiesHelper.readFromClasspath("/config.properties");
        // binds the keynames as @Named annotations
        Names.bindProperties(binder(), properties);

//        bind(ObjectMapper.class);
        bind(ActivityStreamRepository.class).to(ElasticSearchActivityStreamRepository.class);

    }

    @Provides
    public ObjectMapper loadTheObjectMapper() {

        return ActivityStreamObjectMapper.getDefaultMapper();
    }

}
