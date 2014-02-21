package eu.elderspaces.activities.core;

import java.util.List;

import eu.elderspaces.activities.core.exceptions.ActivityManagerException;
import eu.elderspaces.model.ActivityStream;

public interface ActivityStreamManager {
    
    boolean storeActivity(String activityContent, boolean store) throws ActivityManagerException;
    
    List<String> getAllActivities(int size) throws ActivityManagerException;
    
    boolean playAndStoreActivity(ActivityStream activity) throws ActivityManagerException;
    
    boolean playActivity(ActivityStream activity) throws ActivityManagerException;
    
}
