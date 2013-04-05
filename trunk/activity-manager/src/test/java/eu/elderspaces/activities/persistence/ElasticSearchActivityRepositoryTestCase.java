package eu.elderspaces.activities.persistence;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class ElasticSearchActivityRepositoryTestCase extends AbstractActivityRepositoryTestCase {
    
    @Override
    protected void specificImplementationClassInitialize() {
    
        activityRepository = new ElasticSearchActivityRepository();
        LOGGER = LoggerFactory.getLogger(ElasticSearchActivityRepositoryTestCase.class);
        
    }
    
    @Override
    protected void specificImplementationShutDown() {
    
        activityRepository.shutDownRepository();
        
    }
    
    @Override
    protected void specificImplementationMethodInitialize() {
    
        activityRepository = new ElasticSearchActivityRepository();
        
    }
}
