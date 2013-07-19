package eu.elderspaces.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.elasticsearch.client.Client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import eu.elderspaces.model.utils.ActivityStreamObjectMapper;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ActivityStreamTestModule extends AbstractModule {
    
    @Override
    protected void configure() {
    
        bind(EmbeddedElasticsearchServer.class).toInstance(
                new EmbeddedElasticsearchServer("target/unit-test-elasticsearch"));
        bind(ActivityStreamRepository.class).to(ElasticSearchActivityStreamRepository.class);
        
    }
    
    @Provides
    public ObjectMapper loadTheObjectMapper() {
    
        final ObjectMapper mapper = ActivityStreamObjectMapper.getDefaultMapper();
        mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        return mapper;
    }
    
    @Provides
    public Client clientProvider(final EmbeddedElasticsearchServer server) {
    
        return server.getClient();
    }
    
}
