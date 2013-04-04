package eu.elderspaces.activities.persistence;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.internal.Maps;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;

public class InMemoryActivityRepository implements ActivityRepository {
    
    private final Map<String, Call> userCalls = Maps.newHashMap();
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void shutDownRepository() {
    
        // Do nothing
    }
    
    @Override
    public boolean store(final Call call, final String userId) {
    
        return userCalls.put(userId, call) != null;
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
    
}
