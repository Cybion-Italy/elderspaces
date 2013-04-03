package eu.elderspaces.activities.core;

import org.codehaus.jackson.map.ObjectMapper;

import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.model.Activity;

public class SimpleActivityManager implements ActivityManager {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public boolean storeActivity(final String activityContent) throws InvalidUserActivity {
    
        try {
            final Activity activity = mapper.readValue(activityContent, Activity.class);
        } catch (final Exception e) {
            throw new InvalidUserActivity(e);
        }
        
        return false;
    }
}
