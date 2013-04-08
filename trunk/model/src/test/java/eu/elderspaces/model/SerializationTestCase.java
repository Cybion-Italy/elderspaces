package eu.elderspaces.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SerializationTestCase {
    
    private static final String PUBLISHED = "2013-03-29T3:41:48+0100";
    private static final String VERB = "create";
    private static final String PERSON_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String PERSON_DISPLAY_NAME = "Mr. Ederly Hans";
    private static final String PERSON_ID = "13913365:elderspaces.iwiw.hu";
    private static final String ACTIVITY_TITLE = "said :";
    private static final String ACTIVITY_BODY = "Hello from Athens!";
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationTestCase.class);
    
    @Test
    public void serializeActivity() throws JsonGenerationException, JsonMappingException,
            IOException {
    
        final Person actor = new Person(PERSON_ID, PERSON_DISPLAY_NAME, PERSON_THUMBNAIL_URL);
        final Entity activityObject = new Post(ACTIVITY_BODY, ACTIVITY_TITLE, actor);
        final Activity activity = new Activity(actor, VERB, activityObject, null, PUBLISHED);
        
        final String activityString = mapper.writeValueAsString(activity);
        LOGGER.info("Activity serialized: " + activityString);
        Assert.assertNotNull(activityString);
        Assert.assertNotSame("", activityString);
    }
}
