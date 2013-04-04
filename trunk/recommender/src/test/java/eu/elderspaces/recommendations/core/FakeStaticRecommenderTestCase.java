package eu.elderspaces.recommendations.core;

import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class FakeStaticRecommenderTestCase extends AbstractRecommenderTestCase {
    
    @BeforeClass
    public void startUp() {
    
        recommender = new FakeStaticRecommender();
        LOGGER = LoggerFactory.getLogger(FakeStaticRecommenderTestCase.class);
    }
    
}
