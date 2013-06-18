package eu.elderspaces.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepositoryTestCase extends BaseElasticSearchProvider {

    private ActivityStreamRepository elasticSearchActivityStream;

    @BeforeClass
    public void setUpModule() {
        Injector injector = Guice.createInjector(new ActivityStreamTestModule());
        this.elasticSearchActivityStream = injector.getInstance(ActivityStreamRepository.class);
    }

    @Test
    public void shouldStoreActivityStreams() throws ActivityStreamRepositoryException {
        assertTrue(true);
        assertNotNull(this.elasticSearchActivityStream);
        this.elasticSearchActivityStream.store("just this");
    }

}
