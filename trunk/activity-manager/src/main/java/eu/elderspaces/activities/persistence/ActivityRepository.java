package eu.elderspaces.activities.persistence;

import eu.elderspaces.model.Call;

public interface ActivityRepository {
    
    public void shutDownRepository();
    
    public boolean store(Call call);
}
