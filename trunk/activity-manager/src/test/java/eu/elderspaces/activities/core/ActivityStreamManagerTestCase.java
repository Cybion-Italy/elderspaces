package eu.elderspaces.activities.core;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.elderspaces.activities.core.exceptions.ActivityManagerException;
import eu.elderspaces.model.Activity;
import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Verbs;
import eu.elderspaces.persistence.EmbeddedElasticsearchServer;

public class ActivityStreamManagerTestCase {
    
    private static Logger LOGGER = Logger.getLogger(ActivityStreamManagerTestCase.class);
    
    private static final Date PUBLISHED = new Date(); // "2013-03-29T3:41:48+0100";
    private static final String VERB = "create";
    private static final String USER_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String USER_DISPLAY_NAME = "Mr. Ederly Hans";
    private static final String USER_ID = "13913365:elderspaces.iwiw.hu";
    
    private static final String FRIEND_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913366_1301469612927_tn1";
    private static final String FRIEND_DISPLAY_NAME = "Mr. Matt Eldy";
    private static final String FRIEND_ID = "13913366:elderspaces.iwiw.hu";
    
    private static final String POST_ID = "activityid:skdòakspora";
    private static final String POST_TITLE = "said :";
    private static final String POST_BODY = "Hello from Athens!";
    
    private static final String EVENT_ID = "Event id";
    private static final String EVENT_NAME = "Event name";
    private static final String EVENT_SHORT_DESCRIPTION = "Event short description";
    
    private static final String CLUB_ID = "Club id";
    private static final String CLUB_NAME = "Club name";
    private static final String CLUB_DESCRIPTION = "Club description";
    private static final String CLUB_SHORT_DESCRIPTION = "Club short description";
    private static final String CLUB_CATEGORY = "Club category";
    
    private static final String RSVP_RESPONSE = Verbs.MAYBE_RSVP_RESPONSE_TO_EVENT;
    
    private Person user;
    private Person friend;
    private Activity post;
    private Event event;
    private Club club;
    
    EmbeddedElasticsearchServer server;
    protected ActivityStreamManager manager;
    
    @BeforeClass
    public void initialize() {
    
        user = new Person(USER_ID, USER_DISPLAY_NAME, USER_THUMBNAIL_URL);
        friend = new Person(FRIEND_ID, FRIEND_DISPLAY_NAME, FRIEND_THUMBNAIL_URL);
        event = new Event(EVENT_ID, EVENT_NAME, EVENT_SHORT_DESCRIPTION);
        club = new Club(CLUB_ID, CLUB_NAME, CLUB_DESCRIPTION, CLUB_SHORT_DESCRIPTION, CLUB_CATEGORY);
        post = new Activity(POST_ID, POST_BODY, POST_TITLE);
        
        final Injector injector = Guice.createInjector(new ActivityStreamManagerTestModule());
        
        server = injector.getInstance(EmbeddedElasticsearchServer.class);
        manager = injector.getInstance(ActivityStreamManager.class);
    }
    
    @AfterClass
    public void shutDown() {
    
        server.shutdown();
        manager = null;
    }
    
    @Test
    public void store() throws ActivityManagerException {
    
        final Entity activityObject = new Activity(POST_ID, POST_BODY, POST_TITLE);
        final ActivityStream call = new ActivityStream(user, VERB, activityObject, null, PUBLISHED);
        
        final boolean stored = manager.playAndStoreActivity(call);
        LOGGER.info("Storing call: " + call);
        Assert.assertTrue(stored);
    }
    
    @Test
    public void addFriend() throws ActivityManagerException {
    
        LOGGER.info("Adding friend");
        final ActivityStream activity = new ActivityStream(user, Verbs.MAKE_FRIEND, friend, null,
                new Date());
        final boolean added = manager.playAndStoreActivity(activity);
        Assert.assertTrue(added);
    }
    
    @Test
    public void removeFriend() throws ActivityManagerException {
    
        LOGGER.info("Removing non-existent friend");
        final ActivityStream activity = new ActivityStream(user, Verbs.REMOVE_FRIEND, friend, null,
                new Date());
        final boolean removed = manager.playAndStoreActivity(activity);
        Assert.assertTrue(removed);
        
    }
    
    @Test
    public void updateUser() throws ActivityManagerException {
    
        LOGGER.info("Updating user");
        final Person updatedUser = user;
        updatedUser.setDisplayName("New display name");
        final ActivityStream activity = new ActivityStream(user, Verbs.UPDATE, updatedUser, null,
                new Date());
        final boolean updated = manager.playAndStoreActivity(activity);
        Assert.assertTrue(updated);
    }
    
    @Test
    public void deleteUser() throws ActivityManagerException {
    
        LOGGER.info("Deleting user");
        final ActivityStream activity = new ActivityStream(user, Verbs.DELETE, user, null,
                new Date());
        final boolean deleted = manager.playAndStoreActivity(activity);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void addPost() throws ActivityManagerException {
    
        LOGGER.info("Creating post");
        final ActivityStream activity = new ActivityStream(user, Verbs.CREATE, post, null,
                new Date());
        final boolean added = manager.playAndStoreActivity(activity);
        Assert.assertTrue(added);
    }
    
    @Test
    public void deletePost() throws ActivityManagerException {
    
        LOGGER.info("Deleting non-existent post");
        final ActivityStream deleteActivity = new ActivityStream(user, Verbs.DELETE, post, null,
                new Date());
        boolean deleted;
        try {
            deleted = manager.playAndStoreActivity(deleteActivity);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        
        final ActivityStream postActivity = new ActivityStream(user, Verbs.CREATE, post, null,
                new Date());
        manager.playAndStoreActivity(postActivity);
        
        LOGGER.info("Deleting post");
        deleted = manager.playAndStoreActivity(deleteActivity);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void createEvent() throws ActivityManagerException {
    
        LOGGER.info("Creating event");
        final ActivityStream activity = new ActivityStream(user, Verbs.CREATE, event, null,
                new Date());
        final boolean added = manager.playAndStoreActivity(activity);
        Assert.assertTrue(added);
    }
    
    @Test
    public void modifyEvent() throws ActivityManagerException {
    
        LOGGER.info("Modifying event");
        final Event modifiedEvent = new Event(event.getId(), "New name", "New short description");
        final ActivityStream activity = new ActivityStream(user, Verbs.UPDATE, event, null,
                new Date());
        final boolean modified = manager.playAndStoreActivity(activity);
        Assert.assertTrue(modified);
    }
    
    @Test
    public void deleteEvent() throws ActivityManagerException {
    
        LOGGER.info("Deleting existing event");
        final ActivityStream activity = new ActivityStream(user, Verbs.DELETE, event, null,
                new Date());
        final boolean deleted = manager.playAndStoreActivity(activity);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void createClub() throws ActivityManagerException {
    
        LOGGER.info("Creating club");
        final ActivityStream activity = new ActivityStream(user, Verbs.CREATE, club, null,
                new Date());
        final boolean added = manager.playAndStoreActivity(activity);
        Assert.assertTrue(added);
    }
    
    @Test
    public void modifyClub() throws ActivityManagerException {
    
        LOGGER.info("Modifying club");
        final Club modifiedClub = new Club(club.getId(), "New name", CLUB_DESCRIPTION,
                "New short description", CLUB_CATEGORY);
        final ActivityStream activity = new ActivityStream(user, Verbs.UPDATE, modifiedClub, null,
                new Date());
        final boolean modified = manager.playAndStoreActivity(activity);
        Assert.assertTrue(modified);
    }
    
    @Test
    public void deleteClub() throws ActivityManagerException {
    
        LOGGER.info("Deleting existing club");
        final ActivityStream activity = new ActivityStream(user, Verbs.DELETE, club, null,
                new Date());
        final boolean deleted = manager.playAndStoreActivity(activity);
        Assert.assertTrue(deleted);
        
    }
    
    @Test
    public void joinClub() throws ActivityManagerException {
    
        LOGGER.info("Joining club");
        final ActivityStream activity = new ActivityStream(user, Verbs.JOIN, club, null, new Date());
        final boolean joined = manager.playAndStoreActivity(activity);
        Assert.assertTrue(joined);
    }
    
    @Test
    public void leaveClub() throws ActivityManagerException {
    
        LOGGER.info("Leaving existing club");
        final ActivityStream activity = new ActivityStream(user, Verbs.LEAVE, club, null,
                new Date());
        final boolean left = manager.playAndStoreActivity(activity);
        Assert.assertTrue(left);
        
    }
    
    @Test
    public void createRSVPResponseToEvent() throws ActivityManagerException {
    
        LOGGER.info("Creating RSVP response to an event");
        final ActivityStream activity = new ActivityStream(user, Verbs.NO_RSVP_RESPONSE_TO_EVENT,
                event, null, new Date());
        manager.playAndStoreActivity(activity);
        final boolean created = manager.playAndStoreActivity(activity);
        Assert.assertTrue(created);
    }
    
}
