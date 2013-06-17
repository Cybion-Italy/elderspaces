package eu.elderspaces.recommendations.core;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.inject.internal.Lists;

import eu.elderspaces.activities.core.SimpleActivityManager;
import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.activities.persistence.InMemoryActivityRepository;
import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Verbs;

@Test
public class SimpleRecommenderTestCase extends AbstractRecommenderTestCase {
    
    private static final String USER_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String USER_DISPLAY_NAME = "Mr. Ederly Hans";
    
    private static final String USER2_ID = "User 2 Id";
    private static final String USER2_THUMBNAIL_URL = "User 2 Thumbnail Url";
    private static final String USER2_DISPLAY_NAME = "User 2 Display Name";
    
    private static final String FRIEND1_ID = "Friend 1 id";
    private static final String FRIEND1_DISPLAY_NAME = "Friend 1 Display Name";
    private static final String FRIEND1_THUMBNAIL_URL = "Friend 1 Thumbnail Url";
    
    private static final String FRIEND11_ID = "Friend 11 id";
    private static final String FRIEND11_DISPLAY_NAME = "Friend 11 Display Name";
    private static final String FRIEND11_THUMBNAIL_URL = "Friend 11 Thumbnail Url";
    
    private static final String FRIEND2_ID = "Friend 2 id";
    private static final String FRIEND2_DISPLAY_NAME = "Friend 2 Display Name";
    private static final String FRIEND2_THUMBNAIL_URL = "Friend 2 Thumbnail Url";
    
    private static final String FRIEND21_ID = "Friend 21 id";
    private static final String FRIEND21_DISPLAY_NAME = "Friend 21 Display Name";
    private static final String FRIEND21_THUMBNAIL_URL = "Friend 21 Thumbnail Url";
    
    private static final String EVENT1_ID = "Event 1 id";
    private static final String EVENT1_NAME = "Event 1 name";
    private static final String EVENT1_SHORT_DESCRIPTION = "Event 1 short description";
    
    private static final String EVENT2_ID = "Event 2 id";
    private static final String EVENT2_NAME = "Event 2 name";
    private static final String EVENT2_SHORT_DESCRIPTION = "Event 2 short description";
    
    private static final String CLUB1_ID = "Club 1 id";
    private static final String CLUB1_NAME = "Club 1 name";
    private static final String CLUB1_DESCRIPTION = "Club 1 description";
    private static final String CLUB1_SHORT_DESCRIPTION = "Club 1 short description";
    private static final String CLUB1_CATEGORY = "Club 1 category";
    
    private static final String CLUB2_ID = "Club 2 id";
    private static final String CLUB2_NAME = "Club 2 name";
    private static final String CLUB2_DESCRIPTION = "Club 2 description";
    private static final String CLUB2_SHORT_DESCRIPTION = "Club 2 short description";
    private static final String CLUB2_CATEGORY = "Club 2 category";
    
    private Person user;
    private Person user2;
    private Person friend1;
    private Person friend11;
    private Person friend2;
    private Person friend21;
    private Event event1;
    private Event event2;
    private Club club1;
    private Club club2;
    
    @Override
    protected void specificImplementationClassInitialize() throws InvalidUserActivity,
            ActivityRepositoryException {
    
        final ActivityRepository activityRepository = new InMemoryActivityRepository();
        user = new Person(USER_ID, USER_DISPLAY_NAME, USER_THUMBNAIL_URL);
        user2 = new Person(USER2_ID, USER2_DISPLAY_NAME, USER2_THUMBNAIL_URL);
        friend1 = new Person(FRIEND1_ID, FRIEND1_DISPLAY_NAME, FRIEND1_THUMBNAIL_URL);
        friend11 = new Person(FRIEND11_ID, FRIEND11_DISPLAY_NAME, FRIEND11_THUMBNAIL_URL);
        friend2 = new Person(FRIEND2_ID, FRIEND2_DISPLAY_NAME, FRIEND2_THUMBNAIL_URL);
        friend21 = new Person(FRIEND21_ID, FRIEND21_DISPLAY_NAME, FRIEND21_THUMBNAIL_URL);
        event1 = new Event(EVENT1_ID, EVENT1_NAME, EVENT1_SHORT_DESCRIPTION);
        event2 = new Event(EVENT2_ID, EVENT2_NAME, EVENT2_SHORT_DESCRIPTION);
        club1 = new Club(CLUB1_ID, CLUB1_NAME, CLUB1_DESCRIPTION, CLUB1_SHORT_DESCRIPTION,
                CLUB1_CATEGORY);
        club2 = new Club(CLUB2_ID, CLUB2_NAME, CLUB2_DESCRIPTION, CLUB2_SHORT_DESCRIPTION,
                CLUB2_CATEGORY);
        
        activityRepository.addUser(user);
        activityRepository.addUser(friend1);
        activityRepository.addUser(friend11);
        activityRepository.addUser(friend2);
        activityRepository.addUser(friend21);
        
        activityManager = new SimpleActivityManager(activityRepository);
        
        final ActivityStream friendActivity1 = new ActivityStream(user, Verbs.MAKE_FRIEND, friend1,
                null, new Date());
        
        final ActivityStream friendActivity11 = new ActivityStream(friend1, Verbs.MAKE_FRIEND,
                friend11, null, new Date());
        
        final ActivityStream friendActivity2 = new ActivityStream(user, Verbs.MAKE_FRIEND, friend2,
                null, new Date());
        
        final ActivityStream friendActivity21 = new ActivityStream(friend2, Verbs.MAKE_FRIEND,
                friend21, null, new Date());
        final ActivityStream friendActivity211 = new ActivityStream(friend2, Verbs.MAKE_FRIEND,
                friend11, null, new Date());
        
        final ActivityStream createEvent1Activity = new ActivityStream(user2, Verbs.CREATE, event1,
                null, null);
        final ActivityStream partecipateEvent1Activity = new ActivityStream(friend1,
                Verbs.YES_RSVP_RESPONSE_TO_EVENT, event1, null, null);
        
        final ActivityStream createEvent2Activity = new ActivityStream(user2, Verbs.CREATE, event2,
                null, null);
        final ActivityStream partecipateEvent2Activity = new ActivityStream(friend2,
                Verbs.YES_RSVP_RESPONSE_TO_EVENT, event1, null, null);
        
        final ActivityStream partecipateEvent2Activity2 = new ActivityStream(friend2,
                Verbs.YES_RSVP_RESPONSE_TO_EVENT, event2, null, null);
        
        final ActivityStream createClub1Activity = new ActivityStream(user2, Verbs.CREATE, club1,
                null, null);
        
        final ActivityStream joinClub1Activity = new ActivityStream(friend1, Verbs.JOIN, club1,
                null, null);
        
        final ActivityStream createClub2Activity = new ActivityStream(friend2, Verbs.CREATE, club2,
                null, null);
        
        final ActivityStream joinClub1Activity2 = new ActivityStream(friend2, Verbs.JOIN, club1,
                null, null);
        
        final List<ActivityStream> activities = Lists.newArrayList();
        
        activities.add(friendActivity1);
        activities.add(friendActivity11);
        activities.add(friendActivity2);
        activities.add(friendActivity21);
        activities.add(friendActivity211);
        activities.add(createEvent1Activity);
        activities.add(partecipateEvent1Activity);
        activities.add(createEvent2Activity);
        activities.add(partecipateEvent2Activity);
        activities.add(partecipateEvent2Activity2);
        activities.add(createClub1Activity);
        activities.add(joinClub1Activity);
        activities.add(createClub2Activity);
        activities.add(joinClub1Activity2);
        
        for (final ActivityStream activity : activities) {
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
