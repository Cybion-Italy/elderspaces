package eu.elderspaces.activities.core;

import eu.elderspaces.activities.exceptions.InvalidUserActivity;

public interface ActivityManager {
    
    boolean storeActivity(String activityContent) throws InvalidUserActivity;
    
}
