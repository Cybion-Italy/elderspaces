package eu.elderspaces.recommendations.core;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class FakeStaticRecommenderTestCase extends AbstractRecommenderTestCase {
    
    @Override
    protected void specificImplementationClassInitialize() {
    
        LOGGER = LoggerFactory.getLogger(FakeStaticRecommenderTestCase.class);
        LOGGER.debug("Test class initialization");
        recommender = new FakeStaticRecommender();
        
    }
    
    @Override
    protected void specificImplementationMethodInitialize() {
    
        LOGGER.debug("Empty test method initialization");
        
    }
    
    @Override
    protected void specificImplementationShutDown() {
    
        LOGGER.debug("Empty test shutdown");
        
    }
    
}
