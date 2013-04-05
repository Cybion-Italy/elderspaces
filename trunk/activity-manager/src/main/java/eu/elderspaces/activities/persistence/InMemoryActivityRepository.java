package eu.elderspaces.activities.persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.collect.Lists;
import com.google.inject.internal.Maps;
import com.google.inject.internal.Sets;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;
import eu.elderspaces.model.profile.UserHistory;
import eu.elderspaces.model.profile.UserProfile;

public class InMemoryActivityRepository implements ActivityRepository {
    
    private final Map<String, List<Call>> calls = Maps.newHashMap();
    private final Map<String, UserHistory> histories = Maps.newHashMap();
    private final Map<String, UserProfile> profiles = Maps.newHashMap();
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void shutDownRepository() {
    
        // Do nothing
    }
    
    @Override
    public boolean store(final Call call, final String userId) {
    
        List<Call> userCalls = calls.get(userId);
        
        if (userCalls == null) {
            
            userCalls = Lists.newArrayList(call);
            
        } else {
            userCalls.add(call);
        }
        calls.put(userId, userCalls);
        
        return true;
    }
    
    @Override
    public boolean store(final String callString, final String userId)
            throws ActivityRepositoryException {
    
        Call call = null;
        try {
            call = mapper.readValue(callString, Call.class);
        } catch (final Exception e) {
            throw new ActivityRepositoryException(e);
        }
        
        return store(call, userId);
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
        histories.put(userId, userHistory);
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
        histories.remove(userId);
        
        return true;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject) {
    
        final String userId = user.getId();
        final UserHistory userHistory = getUserHistory(userId, user);
        final boolean added = userHistory.getPosts().add(postObject);
        histories.put(userId, userHistory);
        
        return added;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject, final Event target) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject, final Club target) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject) {
    
        final String userId = user.getId();
        final UserHistory userHistory = getUserHistory(userId, user);
        final boolean deleted = userHistory.getPosts().remove(postObject);
        histories.put(userId, userHistory);
        
        return deleted;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject, final Event targetEvent) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject, final Club targetClub) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean modifyEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createRSVPResponseToEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean modifyClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean joinClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean leaveClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
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
    
        UserHistory userHistory = histories.get(userId);
        if (userHistory == null) {
            final List<Post> posts = Lists.newArrayList();
            userHistory = new UserHistory(user, posts);
        }
        
        return userHistory;
    }
    
}
