package eu.elderspaces.persistence;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.Person;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepositoryTestCase {
    
    private static final Logger LOGGER = Logger
            .getLogger(ElasticSearchActivityStreamRepositoryTestCase.class);
    
    private ActivityStreamRepository elasticSearchActivityStream;
    private EmbeddedElasticsearchServer server;
    
    @BeforeClass
    public void setUpModule() {
    
        final Injector injector = Guice.createInjector(new ActivityStreamTestModule());
        
        this.server = injector.getInstance(EmbeddedElasticsearchServer.class);
        this.elasticSearchActivityStream = injector.getInstance(ActivityStreamRepository.class);
    }
    
    @AfterClass
    public void shutdown() throws ActivityStreamRepositoryException {
    
        elasticSearchActivityStream.shutDownRepository();
        server.shutdown();
    }
    
    @Test
    public void shouldStoreActivityStreams() throws ActivityStreamRepositoryException {
    
        assertTrue(true);
        assertNotNull(this.elasticSearchActivityStream);
        
        final ActivityStream activityStream = new ActivityStream();
        activityStream.setActor(new Person("1", "url", "name"));
        activityStream.setObject(new Activity("2", "body", "title"));
        activityStream.setPublished(new Date());
        activityStream.setTarget(null);
        activityStream.setVerb("create");
        
        LOGGER.debug("storing...");
        String id = this.elasticSearchActivityStream.store(activityStream);
        assertNotNull(id);
        
        LOGGER.debug("counting...");
        long count = elasticSearchActivityStream.getTotalActivityStreamSize();
        assertEquals(count, 1);
        LOGGER.debug("count: " + count);
        
        LOGGER.debug("storing...");
        activityStream.setVerb("delete");
        id = this.elasticSearchActivityStream.store(activityStream);
        assertNotNull(id);
        
        LOGGER.debug("counting...");
        count = elasticSearchActivityStream.getTotalActivityStreamSize();
        assertEquals(count, 2);
        LOGGER.debug("count: " + count);
        
        LOGGER.debug("getting last stored...");
        final ActivityStream storedActivityStream = elasticSearchActivityStream
                .getActivityStream(id);
        
        LOGGER.debug("checking stored object...");
        assertNotNull(storedActivityStream);
        assertTrue(activityStream.equals(storedActivityStream));
        
    }
}
