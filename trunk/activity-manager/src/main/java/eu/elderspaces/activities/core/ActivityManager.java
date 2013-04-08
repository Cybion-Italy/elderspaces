package eu.elderspaces.activities.core;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.model.Activity;

public interface ActivityManager {
    
    boolean storeActivity(String callContent) throws InvalidUserActivity, ActivityRepositoryException;
    
    boolean storeActivity(Activity call) throws InvalidUserActivity, ActivityRepositoryException;
    
}
