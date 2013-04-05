package eu.elderspaces.activities.persistence;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class InMemoryActivityRepositoryTestCase extends AbstractActivityRepositoryTestCase {
    
    @Override
    protected void specificImplementationInitialize() {
    
        activityRepository = new InMemoryActivityRepository();
        LOGGER = LoggerFactory.getLogger(InMemoryActivityRepositoryTestCase.class);
        
    }
    
    @Override
    protected void specificImplementationShutDown() {
    
        // Do nothing
        
    }
}
