package eu.elderspaces.model;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eu.elderspaces.model.utils.ActivityStreamObjectMapper;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class JSONDeserializationTestCase {
    
    private static final Logger LOGGER = Logger.getLogger(JSONDeserializationTestCase.class);
    private static final String json = "";
    File jsonFile;
    ObjectMapper mapper;
    String userDir = System.getProperty("user.dir");
    String resourcesDir = userDir + "/src/test/resources/";
    
    private static String POST_ACTIVITY = "post_activity.json";
    private static String CREATE_EVENT = "create_event.json";
    
    @BeforeClass
    public void setup() {
    
        mapper = ActivityStreamObjectMapper.getDefaultMapper();
    }
    
    @Test
    public void shouldDeserializePostActivity() throws JsonParseException, JsonMappingException,
            IOException {
    
        final File jsonFile = new File(resourcesDir + POST_ACTIVITY);
        final ActivityStream stream = mapper.readValue(jsonFile, ActivityStream.class);
        
        Assert.assertTrue(stream.getActor() != null);
        Assert.assertTrue(stream.getObject() != null);
        Assert.assertTrue(stream.getObject().getClass().equals(Activity.class));
        Assert.assertTrue(stream.getPublished() != null);
        
    }
    
    @Test
    public void shouldDeserializeEvent() throws JsonParseException, JsonMappingException,
            IOException {
    
        final File jsonFile = new File(resourcesDir + CREATE_EVENT);
        final ActivityStream stream = mapper.readValue(jsonFile, ActivityStream.class);
        
        Assert.assertTrue(stream.getActor() != null);
        Assert.assertTrue(stream.getObject() != null);
        Assert.assertTrue(stream.getObject().getClass().equals(Event.class));
        Assert.assertTrue(stream.getPublished() != null);
        
        final Event event = (Event) stream.getObject();
        Assert.assertTrue(event.getDescription() != null);
        Assert.assertTrue(event.getCategory() != null);
        Assert.assertTrue(event.getEndDate() != null);
        Assert.assertTrue(event.getId() != null);
        Assert.assertTrue(event.getName() != null);
        Assert.assertTrue(event.getShortDescription() != null);
        Assert.assertTrue(event.getStartDate() != null);
        Assert.assertTrue(stream.getPublished() != null);
    }
}
