package eu.elderspaces.activities.persistence;

import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ElasticSearchActivityRepositoryTestCase extends AbstractActivityRepositoryTestCase {
    
    @BeforeClass
    public void startUp() {
    
        activityRepository = new ElasticSearchActivityRepository();
        LOGGER = LoggerFactory.getLogger(ElasticSearchActivityRepositoryTestCase.class);
    }
    
    @AfterClass
    public void tearDownObjects() {
    
        activityRepository.shutDownRepository();
    }
}
