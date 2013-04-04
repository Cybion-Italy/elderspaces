package eu.elderspaces.activities.core;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserCall;
import eu.elderspaces.model.Call;

public interface ActivityManager {
    
    boolean storeCall(String callContent) throws InvalidUserCall, ActivityRepositoryException;
    
    boolean storeCall(Call call) throws InvalidUserCall, ActivityRepositoryException;
    
}
