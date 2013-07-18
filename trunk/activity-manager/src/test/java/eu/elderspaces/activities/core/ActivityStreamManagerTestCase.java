package eu.elderspaces.activities.core;

import java.util.Date;

import org.slf4j.Logger;
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

public class ActivityStreamManagerTestCase {
    
    private static final Date PUBLISHED = new Date(); // "2013-03-29T3:41:48+0100";
    private static final String VERB = "create";
    private static final String USER_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String USER_DISPLAY_NAME = "Mr. Ederly Hans";
    private static final String USER_ID = "13913365:elderspaces.iwiw.hu";
    
    private static final String FRIEND_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913366_1301469612927_tn1";
    private static final String FRIEND_DISPLAY_NAME = "Mr. Matt Eldy";
    private static final String FRIEND_ID = "13913366:elderspaces.iwiw.hu";
    
    private static final String POST_ID = null;
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
    
    protected static Logger LOGGER;
    
    protected ActivityStreamManager manager;
    
    @BeforeClass
    public void initialize() {
    
        user = new Person(USER_ID, USER_DISPLAY_NAME, USER_THUMBNAIL_URL);
        friend = new Person(FRIEND_ID, FRIEND_DISPLAY_NAME, FRIEND_THUMBNAIL_URL);
        event = new Event(EVENT_ID, EVENT_NAME, EVENT_SHORT_DESCRIPTION);
        club = new Club(CLUB_ID, CLUB_NAME, CLUB_DESCRIPTION, CLUB_SHORT_DESCRIPTION, CLUB_CATEGORY);
        post = new Activity(POST_ID, POST_BODY, POST_TITLE);
        
        final Injector injector = Guice.createInjector(new ActivityStreamManagerTestModule());
        
        manager = injector.getInstance(ActivityStreamManager.class);
    }
    
    @AfterClass
    public void shutDown() {
    
        manager = null;
    }
    
    @Test
    public void store() throws ActivityManagerException {
    
        final Entity activityObject = new Activity(POST_ID, POST_BODY, POST_TITLE);
        final ActivityStream call = new ActivityStream(user, VERB, activityObject, null, PUBLISHED);
        
        final boolean stored = manager.storeActivity(call);
        LOGGER.info("Storing call: " + call);
        Assert.assertTrue(stored);
    }
    
    @Test
    public void addFriend() throws ActivityManagerException {
    
        LOGGER.info("Adding friend");
        final ActivityStream activity = new ActivityStream(user, "create", friend, null, new Date());
        final boolean added = manager.storeActivity(activity);
        Assert.assertTrue(added);
    }
    
    @Test
    public void removeFriend() throws ActivityManagerException {
    
        LOGGER.info("Removing non-existent friend");
        final ActivityStream activity = new ActivityStream(user, "delete", friend, null, new Date());
        final boolean removed = manager.storeActivity(activity);
        Assert.assertFalse(removed);
        
    }
    
    @Test
    public void updateUser() throws ActivityManagerException {
    
        LOGGER.info("Updating user");
        final Person updatedUser = user;
        updatedUser.setDisplayName("New display name");
        final ActivityStream activity = new ActivityStream(user, "update", updatedUser, null,
                new Date());
        final boolean updated = manager.storeActivity(activity);
        Assert.assertTrue(updated);
    }
    
    @Test
    public void deleteUser() throws ActivityManagerException {
    
        LOGGER.info("Deleting user");
        final ActivityStream activity = new ActivityStream(user, "delete", user, null, new Date());
        final boolean deleted = manager.storeActivity(activity);
        Assert.assertTrue(deleted);
    }
    /*
     * @Test public void addPost() {
     * 
     * LOGGER.info("Creating post"); final boolean added = manager.addPost(user,
     * post); Assert.assertTrue(added); }
     * 
     * @Test public void deletePost() {
     * 
     * LOGGER.info("Deleting non-existent post"); boolean deleted =
     * manager.deletePost(user, post); Assert.assertFalse(deleted);
     * 
     * manager.addPost(user, post);
     * 
     * LOGGER.info("Deleting post"); deleted = manager.deletePost(user, post);
     * Assert.assertTrue(deleted); }
     * 
     * @Test public void createEvent() {
     * 
     * LOGGER.info("Creating event"); final boolean added =
     * manager.createEvent(user, event); Assert.assertTrue(added); }
     * 
     * @Test public void modifyEvent() {
     * 
     * manager.createEvent(user, event);
     * 
     * LOGGER.info("Modifying event"); final Event modifiedEvent = new
     * Event(event.getId(), "New name", "New short description"); final boolean
     * modified = manager.modifyEvent(user, modifiedEvent);
     * Assert.assertTrue(modified); }
     * 
     * @Test public void deleteEvent() {
     * 
     * LOGGER.info("Deleting non-existent event"); boolean deleted =
     * manager.deleteEvent(user, event); Assert.assertFalse(deleted);
     * 
     * manager.createEvent(user, event); LOGGER.info("Deleting existing event");
     * deleted = manager.deleteEvent(user, event); Assert.assertTrue(deleted); }
     * 
     * @Test public void createClub() {
     * 
     * LOGGER.info("Creating club"); final boolean added =
     * manager.createClub(user, club); Assert.assertTrue(added); }
     * 
     * @Test public void modifyClub() {
     * 
     * manager.createClub(user, club);
     * 
     * LOGGER.info("Modifying club"); final Club modifiedClub = new
     * Club(club.getId(), "New name", CLUB_DESCRIPTION, "New short description",
     * CLUB_CATEGORY); final boolean modified = manager.modifyClub(user,
     * modifiedClub); Assert.assertTrue(modified); }
     * 
     * @Test public void deleteClub() {
     * 
     * LOGGER.info("Deleting non-existent club"); boolean deleted =
     * manager.deleteClub(user, club); Assert.assertFalse(deleted);
     * 
     * manager.createClub(user, club); LOGGER.info("Deleting existing club");
     * deleted = manager.deleteClub(user, club); Assert.assertTrue(deleted); }
     * 
     * @Test public void joinClub() {
     * 
     * LOGGER.info("Joining club"); final boolean joined =
     * manager.joinClub(user, club); Assert.assertTrue(joined); }
     * 
     * @Test public void leaveClub() {
     * 
     * LOGGER.info("Leaving non-existent club"); boolean left =
     * manager.leaveClub(user, club); Assert.assertFalse(left);
     * 
     * manager.joinClub(user, club); LOGGER.info("Leaving existing club"); left
     * = manager.leaveClub(user, club); Assert.assertTrue(left); }
     * 
     * @Test public void createRSVPResponseToEvent() {
     * 
     * LOGGER.info("Creating RSVP response to an event");
     * manager.createEvent(user, event); final boolean created =
     * manager.createRSVPResponseToEvent(user, RSVP_RESPONSE, event);
     * Assert.assertTrue(created); }
     */
}
