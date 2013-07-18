package eu.elderspaces.persistence;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepositoryTestCase extends ElasticSearchNodeProvider {
    
    private ActivityStreamRepository elasticSearchActivityStream;
    
    @BeforeClass
    public void setUpModule() {
    
        // the es node provider offers a local node for integration testing,
        // where the repository is connected
        final Injector injector = Guice.createInjector(new ActivityStreamTestModule());
        this.elasticSearchActivityStream = injector.getInstance(ActivityStreamRepository.class);
    }
    
    @Test
    public void shouldStoreActivityStreams() throws ActivityStreamRepositoryException {
    
        assertTrue(true);
        assertNotNull(this.elasticSearchActivityStream);
        // TODO major: activityStream should have 1) a String id 2) a timestamp?
        final ActivityStream activity = null;
        // this.elasticSearchActivityStream.store(activity);
    }
}
