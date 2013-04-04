package eu.elderspaces.recommendations.core;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public abstract class AbstractRecommenderTestCase {
    
    private static final String USER_ID = "1";
    
    protected static Logger LOGGER;
    
    protected Recommender recommender;
    
    @Test
    public void getFriends(final String userId) throws RecommenderException {
    
        final PaginatedResult<Person> results = recommender.getFriends(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getEvents(final String userId) throws RecommenderException {
    
        final PaginatedResult<Event> results = recommender.getEvents(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getClubs(final String userId) throws RecommenderException {
    
        final PaginatedResult<Club> results = recommender.getClubs(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
}
