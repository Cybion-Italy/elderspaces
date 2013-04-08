package eu.elderspaces.activities.persistence;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

public abstract class AbstractActivityRepositoryTestCase {
    
    private static final String PUBLISHED = "2013-03-29T3:41:48+0100";
    private static final String VERB = "create";
    private static final String PERSON_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913365_1301469612927_tn1";
    private static final String USER_DISPLAY_NAME = "Mr. Ederly Hans";
    private static final String USER_ID = "13913365:elderspaces.iwiw.hu";
    
    private static final String FRIEND_THUMBNAIL_URL = "http://thn1.elderspaces.iwiw.hu/0101//user/01/39/13/36/5/user_13913366_1301469612927_tn1";
    private static final String FRIEND_DISPLAY_NAME = "Mr. Matt Eldy";
    private static final String FRIEND_ID = "13913366:elderspaces.iwiw.hu";
    
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
    
    private Person user;
    private Person friend;
    private Post post;
    private Event event;
    private Club club;
    
    protected static Logger LOGGER;
    
    protected ActivityRepository activityRepository;
    
    @BeforeMethod
    public void initializeDataStructures() {
    
        specificImplementationMethodInitialize();
    }
    
    @BeforeClass
    public void initialize() {
    
        specificImplementationClassInitialize();
        
        user = new Person(USER_ID, USER_DISPLAY_NAME, PERSON_THUMBNAIL_URL);
        activityRepository.addUser(user);
        friend = new Person(FRIEND_ID, FRIEND_DISPLAY_NAME, FRIEND_THUMBNAIL_URL);
        event = new Event(EVENT_ID, EVENT_NAME, EVENT_SHORT_DESCRIPTION);
        club = new Club(CLUB_ID, CLUB_NAME, CLUB_DESCRIPTION, CLUB_SHORT_DESCRIPTION, CLUB_CATEGORY);
        post = new Post(POST_BODY, POST_TITLE, user);
    }
    
    protected abstract void specificImplementationClassInitialize();
    
    protected abstract void specificImplementationMethodInitialize();
    
    @AfterClass
    public void shutDown() {
    
        specificImplementationShutDown();
    }
    
    protected abstract void specificImplementationShutDown();
    
    @Test
    public void store() throws ActivityRepositoryException {
    
        final Entity activityObject = new Post(POST_BODY, POST_TITLE, user);
        final Activity call = new Activity(VERB, activityObject, null, user, PUBLISHED);
        
        final boolean stored = activityRepository.store(call, USER_ID);
        LOGGER.info("Storing call: " + call);
        Assert.assertTrue(stored);
    }
    
    @Test
    public void addFriend() {
    
        LOGGER.info("Adding friend");
        final boolean added = activityRepository.addFriend(user, friend);
        Assert.assertTrue(added);
    }
    
    @Test
    public void removeFriend() {
    
        LOGGER.info("Removing non-existent friend");
        boolean removed = activityRepository.removeFriend(user, friend);
        Assert.assertFalse(removed);
        
        activityRepository.addFriend(user, friend);
        LOGGER.info("Removing existing friend");
        removed = activityRepository.removeFriend(user, friend);
        Assert.assertTrue(removed);
    }
    
    @Test
    public void updateUser() {
    
        LOGGER.info("Updating user");
        final Person updatedUser = user;
        updatedUser.setDisplayName("New display name");
        final boolean updated = activityRepository.updateUser(user, updatedUser);
        Assert.assertTrue(updated);
    }
    
    @Test
    public void deleteUser() {
    
        LOGGER.info("Deleting user");
        final boolean deleted = activityRepository.deleteUser(user);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void addPost() {
    
        LOGGER.info("Creating post");
        final boolean added = activityRepository.addPost(user, post);
        Assert.assertTrue(added);
    }
    
    @Test
    public void deletePost() {
    
        LOGGER.info("Deleting non-existent post");
        boolean deleted = activityRepository.deletePost(user, post);
        Assert.assertFalse(deleted);
        
        activityRepository.addPost(user, post);
        
        LOGGER.info("Deleting post");
        deleted = activityRepository.deletePost(user, post);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void createEvent() {
    
        LOGGER.info("Creating event");
        final boolean added = activityRepository.createEvent(user, event);
        Assert.assertTrue(added);
    }
    
    @Test
    public void modifyEvent() {
    
        activityRepository.createEvent(user, event);
        
        LOGGER.info("Modifying event");
        final Event modifiedEvent = new Event(event.getId(), "New name", "New short description");
        final boolean modified = activityRepository.modifyEvent(user, modifiedEvent);
        Assert.assertTrue(modified);
    }
    
    @Test
    public void deleteEvent() {
    
        LOGGER.info("Deleting non-existent event");
        boolean deleted = activityRepository.deleteEvent(user, event);
        Assert.assertFalse(deleted);
        
        activityRepository.createEvent(user, event);
        LOGGER.info("Deleting existing event");
        deleted = activityRepository.deleteEvent(user, event);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void createCLub() {
    
        LOGGER.info("Creating club");
        final boolean added = activityRepository.createClub(user, club);
        Assert.assertTrue(added);
    }
    
    @Test
    public void modifyClub() {
    
        activityRepository.createClub(user, club);
        
        LOGGER.info("Modifying club");
        final Club modifiedClub = new Club(club.getId(), "New name", CLUB_DESCRIPTION,
                "New short description", CLUB_CATEGORY);
        final boolean modified = activityRepository.modifyClub(user, modifiedClub);
        Assert.assertTrue(modified);
    }
    
    @Test
    public void deleteClub() {
    
        LOGGER.info("Deleting non-existent club");
        boolean deleted = activityRepository.deleteClub(user, club);
        Assert.assertFalse(deleted);
        
        activityRepository.createClub(user, club);
        LOGGER.info("Deleting existing club");
        deleted = activityRepository.deleteClub(user, club);
        Assert.assertTrue(deleted);
    }
    
    @Test
    public void joinClub() {
    
        LOGGER.info("Joining club");
        final boolean joined = activityRepository.joinClub(user, club);
        Assert.assertTrue(joined);
    }
    
    @Test
    public void leaveClub() {
    
        LOGGER.info("Leaving non-existent club");
        boolean left = activityRepository.leaveClub(user, club);
        Assert.assertFalse(left);
        
        activityRepository.joinClub(user, club);
        LOGGER.info("Leaving existing club");
        left = activityRepository.leaveClub(user, club);
        Assert.assertTrue(left);
    }
    
}
