package eu.elderspaces.activities.persistence;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.internal.Maps;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

public class InMemoryActivityRepository implements ActivityRepository {
    
    private final Map<String, Call> userCalls = Maps.newHashMap();
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void shutDownRepository() {
    
        // Do nothing
    }
    
    @Override
    public boolean store(final Call call, final String userId) {
    
        userCalls.put(userId, call);
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
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean removeFriend(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean updateUser(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteUser(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject) {
    
        // TODO Auto-generated method stub
        return false;
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
    
        // TODO Auto-generated method stub
        return false;
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
    
}
