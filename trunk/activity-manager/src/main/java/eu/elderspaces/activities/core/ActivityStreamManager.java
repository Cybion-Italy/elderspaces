package eu.elderspaces.activities.core;

import eu.elderspaces.activities.core.exceptions.ActivityManagerException;
import eu.elderspaces.model.ActivityStream;

public interface ActivityStreamManager {
    
    boolean storeActivity(String activityContent) throws ActivityManagerException;
    
    boolean storeActivity(ActivityStream activity) throws ActivityManagerException;
    
}
