package eu.elderspaces.activities.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserCall;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.activities.persistence.InMemoryActivityRepository;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

@Test
public class SimpleActivityManagerTestCase {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SimpleActivityManagerTestCase.class);
    private static final String PUBLISHED = "2013-03-29T3:41:48+0100";
    private static final String VERB = "create";
    private static final String PERSON_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String PERSON_DISPLAY_NAME = "Mr. Ederly Hans";
    private static final String PERSON_ID = "13913365:elderspaces.iwiw.hu";
    private static final String ACTIVITY_TITLE = "said :";
    private static final String ACTIVITY_BODY = "Hello from Athens!";
    
    private ActivityManager activityManager;
    private ActivityRepository activityRepository;
    
    @BeforeClass
    public void startUp() {
    
        this.activityRepository = new InMemoryActivityRepository();
        this.activityManager = new SimpleActivityManager(activityRepository);
    }
    
    @Test
    public void storeCall() throws InvalidUserCall, ActivityRepositoryException {
    
        final Entity activityObject = new Post(ACTIVITY_BODY, ACTIVITY_TITLE);
        final Person actor = new Person(PERSON_ID, PERSON_DISPLAY_NAME, PERSON_THUMBNAIL_URL);
        final Call call = new Call(VERB, activityObject, null, actor, PUBLISHED);
        
        final boolean stored = activityManager.storeCall(call);
        LOGGER.info("Storing call: " + call);
        Assert.assertTrue(stored);
    }
}
