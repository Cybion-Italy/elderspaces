package eu.elderspaces.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class JSONDeserializationTestCase {
    
    private static final Logger LOGGER = Logger.getLogger(JSONDeserializationTestCase.class);
    private static final String json = "";
    File jsonFile;
    ObjectMapper mapper = new ObjectMapper();
    String userDir = System.getProperty("user.dir");
    String resourcesDir = userDir + "/src/test/resources/";
    
    private static String POST_ACTIVITY = "post_activity.json";
    
    @BeforeClass
    public void setup() {
    
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
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
}
