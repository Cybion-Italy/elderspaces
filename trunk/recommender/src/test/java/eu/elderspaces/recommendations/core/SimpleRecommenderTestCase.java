package eu.elderspaces.recommendations.core;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.inject.internal.Lists;

import eu.elderspaces.activities.core.SimpleActivityManager;
import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.activities.persistence.InMemoryActivityRepository;
import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Verbs;

@Test
public class SimpleRecommenderTestCase extends AbstractRecommenderTestCase {
    
    private static final String USER_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String USER_DISPLAY_NAME = "Mr. Ederly Hans";
    
    private static final String FRIEND1_ID = "Friend 1 id";
    private static final String FRIEND1_DISPLAY_NAME = "Friend 1 Display Name";
    private static final String FRIEND1_THUMBNAIL_URL = "Friend 1 Thumbnail Url";
    
    private Person user;
    private Person friend1;
    
    @Override
    protected void specificImplementationClassInitialize() throws InvalidUserActivity,
            ActivityRepositoryException {
    
        final ActivityRepository activityRepository = new InMemoryActivityRepository();
        user = new Person(USER_ID, USER_DISPLAY_NAME, USER_THUMBNAIL_URL);
        friend1 = new Person(FRIEND1_ID, FRIEND1_DISPLAY_NAME, FRIEND1_THUMBNAIL_URL);
        
        activityRepository.addUser(user);
        activityRepository.addUser(friend1);
        
        activityManager = new SimpleActivityManager(activityRepository);
        
        final Activity addFriend1Activity = new Activity(friend1, Verbs.CREATE, friend1, user, "");
        
        final Activity friendActivity1 = new Activity(user, Verbs.MAKE_FRIEND, friend1, null, "");
        
        final List<Activity> activities = Lists.newArrayList();
        
        activities.add(addFriend1Activity);
        activities.add(friendActivity1);
        
        for (final Activity activity : activities) {
            activityManager.storeActivity(activity);
        }
        
        recommender = new SimpleRecommender(activityManager);
        LOGGER = LoggerFactory.getLogger(SimpleRecommenderTestCase.class);
        
    }
    
    @Override
    protected void specificImplementationShutDown() {
    
        // Do nothing
        
    }
    
    @Override
    protected void specificImplementationMethodInitialize() {
    
        activityManager = new SimpleActivityManager(new InMemoryActivityRepository());
        
    }
    
}
