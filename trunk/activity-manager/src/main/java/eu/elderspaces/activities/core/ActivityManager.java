package eu.elderspaces.activities.core;

import java.util.Set;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.activities.exceptions.NonExistentUser;
import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Person;

public interface ActivityManager {
    
    boolean storeActivity(String activityContent) throws InvalidUserActivity,
            ActivityRepositoryException;
    
    boolean storeActivity(Activity activity) throws InvalidUserActivity,
            ActivityRepositoryException;
    
    Set<Person> getFriends(String userId) throws NonExistentUser;
    
    Set<? extends Entity> getEvents(String userId) throws NonExistentUser;
    
    Set<? extends Entity> getClubs(String userId) throws NonExistentUser;
    
}
