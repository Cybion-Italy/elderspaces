package eu.elderspaces.activities.persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.internal.Maps;
import com.google.inject.internal.Sets;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;
import eu.elderspaces.model.profile.UserHistory;
import eu.elderspaces.model.profile.UserProfile;

public class InMemoryActivityRepository implements ActivityRepository {
    
    private final Map<String, List<Activity>> activities = Maps.newHashMap();
    private final Map<String, UserHistory> userHistories = Maps.newHashMap();
    private final Map<String, UserProfile> profiles = Maps.newHashMap();
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void shutDownRepository() {
    
        // Do nothing
    }
    
    @Override
    public boolean store(final Activity activity, final String userId) {
    
        List<Activity> userActivities = activities.get(userId);
        
        if (userActivities == null) {
            
            userActivities = Lists.newArrayList(activity);
            
        } else {
            userActivities.add(activity);
        }
        activities.put(userId, userActivities);
        
        return true;
    }
    
    @Override
    public boolean store(final String activityString, final String userId)
            throws ActivityRepositoryException {
    
        Activity activity = null;
        try {
            activity = mapper.readValue(activityString, Activity.class);
        } catch (final Exception e) {
            throw new ActivityRepositoryException(e);
        }
        
        return store(activity, userId);
    }
    
    @Override
    public boolean addFriend(final Person user, final Person personObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean friendAdded = userProfile.getFriends().add(personObject);
        profiles.put(userId, userProfile);
        
        return friendAdded;
    }
    
    @Override
    public boolean removeFriend(final Person user, final Person personObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean friendRemoved = userProfile.getFriends().remove(personObject);
        profiles.put(user.getId(), userProfile);
        
        return friendRemoved;
    }
    
    @Override
    public void addUser(final Person user) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        profiles.put(userId, userProfile);
        
        final UserHistory userHistory = getUserHistory(userId, user);
        userHistories.put(userId, userHistory);
    }
    
    @Override
    public boolean updateUser(final Person user, final Person personObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, personObject, true);
        profiles.put(user.getId(), userProfile);
        return true;
    }
    
    @Override
    public boolean deleteUser(final Person user) {
    
        final String userId = user.getId();
        profiles.remove(userId);
        userHistories.remove(userId);
        
        return true;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject) {
    
        final String userId = user.getId();
        final UserHistory userHistory = getUserHistory(userId, user);
        final boolean added = userHistory.getPosts().add(postObject);
        userHistories.put(userId, userHistory);
        
        return added;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject) {
    
        final String userId = user.getId();
        final UserHistory userHistory = getUserHistory(userId, user);
        final boolean deleted = userHistory.getPosts().remove(postObject);
        userHistories.put(userId, userHistory);
        
        return deleted;
    }
    
    @Override
    public boolean createEvent(final Person user, final Event eventObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, true);
        final boolean created = userProfile.getEvents().add(eventObject);
        profiles.put(userId, userProfile);
        
        return created;
    }
    
    @Override
    public boolean modifyEvent(final Person user, final Event eventObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, true);
        final String eventId = eventObject.getId();
        
        final Optional<Event> event = Iterables.tryFind(userProfile.getEvents(),
                new Predicate<Event>() {
                    
                    @Override
                    public boolean apply(final Event event) {
                    
                        return event.getId().equals(eventId);
                    }
                });
        
        boolean removed = false;
        boolean added = false;
        
        if (event.isPresent()) {
            removed = userProfile.getEvents().remove(event.get());
            added = userProfile.getEvents().add(eventObject);
        }
        
        return removed && added;
    }
    
    @Override
    public boolean deleteEvent(final Person user, final Event eventObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean eventRemoved = userProfile.getEvents().remove(eventObject);
        profiles.put(user.getId(), userProfile);
        
        return eventRemoved;
    }
    
    @Override
    public boolean createRSVPResponseToEvent(final Person user, final String verb,
            final Event eventObject) {
    
        final String userId = user.getId();
        final UserHistory userHistory = getUserHistory(userId, user);
        userHistory.getEventResponses().put(eventObject, verb);
        
        return true;
    }
    
    @Override
    public boolean createClub(final Person user, final Club clubObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, true);
        final boolean created = userProfile.getClubs().add(clubObject);
        profiles.put(userId, userProfile);
        
        return created;
    }
    
    @Override
    public boolean modifyClub(final Person user, final Club clubObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, true);
        final String clubId = clubObject.getId();
        
        final Optional<Club> club = Iterables.tryFind(userProfile.getClubs(),
                new Predicate<Club>() {
                    
                    @Override
                    public boolean apply(final Club club) {
                    
                        return club.getId().equals(clubId);
                    }
                });
        
        boolean removed = false;
        boolean added = false;
        
        if (club.isPresent()) {
            removed = userProfile.getClubs().remove(club.get());
            added = userProfile.getClubs().add(clubObject);
        }
        
        return removed && added;
    }
    
    @Override
    public boolean deleteClub(final Person user, final Club clubObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean clubRemoved = userProfile.getClubs().remove(clubObject);
        profiles.put(user.getId(), userProfile);
        
        return clubRemoved;
    }
    
    @Override
    public boolean joinClub(final Person user, final Club clubObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean clubJoined = userProfile.getClubs().add(clubObject);
        profiles.put(userId, userProfile);
        
        return clubJoined;
    }
    
    @Override
    public boolean leaveClub(final Person user, final Club clubObject) {
    
        final String userId = user.getId();
        final UserProfile userProfile = getUserProfile(userId, user, false);
        final boolean clubLeft = userProfile.getClubs().remove(clubObject);
        profiles.put(user.getId(), userProfile);
        
        return clubLeft;
    }
    
    private UserProfile getUserProfile(final String userId, final Person user, final boolean update) {
    
        UserProfile userProfile = profiles.get(userId);
        
        if (userProfile == null) {
            final Set<Person> friends = Sets.newHashSet();
            final Set<Event> events = Sets.newHashSet();
            final Set<Club> clubs = Sets.newHashSet();
            userProfile = new UserProfile(user, friends, events, clubs);
        } else if (update) {
            userProfile.setUser(user);
        }
        
        return userProfile;
    }
    
    private UserHistory getUserHistory(final String userId, final Person user) {
    
        UserHistory userHistory = userHistories.get(userId);
        if (userHistory == null) {
            final List<Post> posts = Lists.newArrayList();
            userHistory = new UserHistory(user, posts);
        }
        
        return userHistory;
    }
    
    @Override
    public boolean userExists(final String userId) {
    
        final UserProfile userProfile = profiles.get(userId);
        if (userProfile != null) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Set<Person> getFriends(final String userId) {
    
        if (!userExists(userId)) {
            return null;
        } else {
            return profiles.get(userId).getFriends();
        }
    }
    
}
