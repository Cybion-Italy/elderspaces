package eu.elderspaces.activities.persistence;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;

public interface ActivityRepository {
    
    public void shutDownRepository();
    
    public boolean store(Call call, String userId) throws ActivityRepositoryException;
    
    public boolean store(String callString, String userId) throws ActivityRepositoryException;
}
