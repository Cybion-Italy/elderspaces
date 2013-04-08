package eu.elderspaces.recommendations.core;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eu.elderspaces.activities.core.ActivityManager;
import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.activities.exceptions.NonExistentUser;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public abstract class AbstractRecommenderTestCase {
    
    protected static final String USER_ID = "13913365:elderspaces.iwiw.hu";
    
    protected static Logger LOGGER;
    
    protected Recommender recommender;
    protected ActivityManager activityManager;
    
    @BeforeClass
    public void initialize() throws InvalidUserActivity, ActivityRepositoryException {
    
        specificImplementationClassInitialize();
    }
    
    @BeforeMethod
    public void initializeDataStructures() {
    
        specificImplementationMethodInitialize();
    }
    
    protected abstract void specificImplementationClassInitialize() throws InvalidUserActivity,
            ActivityRepositoryException;
    
    protected abstract void specificImplementationMethodInitialize();
    
    @AfterClass
    public void shutDown() {
    
        specificImplementationShutDown();
    }
    
    protected abstract void specificImplementationShutDown();
    
    @Test
    public void getFriends() throws RecommenderException, NonExistentUser {
    
        final PaginatedResult<Person> results = recommender.getFriends(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getEvents() throws RecommenderException {
    
        final PaginatedResult<Event> results = recommender.getEvents(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getClubs() throws RecommenderException {
    
        final PaginatedResult<Club> results = recommender.getClubs(USER_ID);
        LOGGER.info("Recommendations computed: " + results);
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
}
