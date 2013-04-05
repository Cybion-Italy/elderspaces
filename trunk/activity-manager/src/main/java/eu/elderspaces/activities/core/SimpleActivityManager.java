package eu.elderspaces.activities.core;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserCall;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Person;

public class SimpleActivityManager implements ActivityManager {
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final ActivityRepository activityRepository;
    
    @Inject
    public SimpleActivityManager(final ActivityRepository activityRepository) {
    
        this.activityRepository = activityRepository;
    }
    
    @Override
    public boolean storeCall(final String callContent) throws InvalidUserCall,
            ActivityRepositoryException {
    
        final Call call;
        try {
            call = mapper.readValue(callContent, Call.class);
        } catch (final Exception e) {
            throw new InvalidUserCall(e);
        }
        
        final boolean callStored = storeCall(call);
        
        final Person user = call.getActor();
        final String verb = call.getVerb();
        final Entity entity = call.getObject();
        
        return callStored;
    }
    
    @Override
    public boolean storeCall(final Call call) throws InvalidUserCall, ActivityRepositoryException {
    
        final String userId = call.getActor().getId();
        return activityRepository.store(call, userId);
    }
}
