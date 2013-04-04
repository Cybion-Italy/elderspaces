package eu.elderspaces.activities.core;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

import eu.elderspaces.activities.exceptions.InvalidUserCall;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.model.Call;

public class SimpleActivityManager implements ActivityManager {
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final ActivityRepository activityRepository;
    
    @Inject
    public SimpleActivityManager(final ActivityRepository activityRepository) {
    
        this.activityRepository = activityRepository;
    }
    
    @Override
    public boolean storeCall(final String callContent) throws InvalidUserCall {
    
        final Call call;
        try {
            call = mapper.readValue(callContent, Call.class);
        } catch (final Exception e) {
            throw new InvalidUserCall(e);
        }
        
        return storeCall(call);
    }
    
    @Override
    public boolean storeCall(final Call call) throws InvalidUserCall {
    
        return activityRepository.store(call);
    }
}
