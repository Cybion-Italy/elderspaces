package eu.elderspaces.activities.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.elasticsearch.client.Client;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provides;
import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.activities.core.ActivityStreamManager;
import eu.elderspaces.activities.core.MultiLayerActivityStreamManager;
import eu.elderspaces.persistence.ActivityStreamRepository;
import eu.elderspaces.persistence.BluePrintsSocialNetworkRepository;
import eu.elderspaces.persistence.ElasticSearchActivityStreamRepository;
import eu.elderspaces.persistence.EmbeddedElasticsearchServer;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.LuceneEntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;

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
        
        // add bindings to mockups of backend classes
        
        // bind REST services
        bind(StatusService.class);
        bind(ActivitiesService.class);
        
        bind(EmbeddedElasticsearchServer.class).toInstance(
                new EmbeddedElasticsearchServer("elderspaces.test.cluster",
                        "target/unit-test-elasticsearch"));
        bind(ActivityStreamRepository.class).to(ElasticSearchActivityStreamRepository.class);
        
        bind(Neo4jGraph.class).toInstance(
                new Neo4jGraph(new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder()
                        .newGraphDatabase()));
        
        bind(SocialNetworkRepository.class).to(BluePrintsSocialNetworkRepository.class);
        
        bind(ActivityStreamManager.class).to(MultiLayerActivityStreamManager.class);
        
        // add bindings for Jackson json serialization
        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        // Route all requests through GuiceContainer
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(GuiceContainer.class, initParams);
        LOGGER.debug("configured servlets");
    }
    
    @Provides
    private Client elasticsearchClientProvider(final EmbeddedElasticsearchServer server) {
    
        return server.getClient();
    }
    
    @Provides
    private EntitiesRepository entitiesRepositoryProvider() {
    
        final Directory directory = new RAMDirectory();
        final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_36);
        
        return new LuceneEntitiesRepository(directory, analyzer);
    }
}
