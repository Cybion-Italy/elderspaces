package eu.elderspaces.recommendations.core;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public abstract class AbstractRecommenderTestCase {
    
    protected static final String USER_ID = "13913365:elderspaces.iwiw.hu";
    
    protected static Logger LOGGER;
    
    protected Recommender recommender;
    
    private ObjectMapper mapper;
    
    @BeforeClass
    public void initialize() {
    
        mapper = new ObjectMapper();
        specificImplementationClassInitialize();
    }
    
    @BeforeMethod
    public void initializeDataStructures() {
    
        specificImplementationMethodInitialize();
    }
    
    protected abstract void specificImplementationClassInitialize();
    
    protected abstract void specificImplementationMethodInitialize();
    
    @AfterClass
    public void shutDown() {
    
        specificImplementationShutDown();
    }
    
    protected abstract void specificImplementationShutDown();
    
    @Test
    public void getFriends() throws RecommenderException, JsonGenerationException,
            JsonMappingException, IOException {
    
        final PaginatedResult results = recommender.getRecommendedEntities(USER_ID, Person.class);
        LOGGER.info("Recommendations computed: " + mapper.writeValueAsString(results));
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getEvents() throws RecommenderException, JsonGenerationException,
            JsonMappingException, IOException {
    
        final PaginatedResult results = recommender.getRecommendedEntities(USER_ID, Event.class);
        LOGGER.info("Recommendations computed: " + mapper.writeValueAsString(results));
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
    @Test
    public void getClubs() throws RecommenderException, JsonGenerationException,
            JsonMappingException, IOException {
    
        final PaginatedResult results = recommender.getRecommendedEntities(USER_ID, Club.class);
        LOGGER.info("Recommendations computed: " + mapper.writeValueAsString(results));
        Assert.assertFalse(results.getEntries().isEmpty());
    }
    
}
