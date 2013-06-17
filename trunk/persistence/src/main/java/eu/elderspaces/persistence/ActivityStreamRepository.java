package eu.elderspaces.persistence;

import eu.elderspaces.model.ActivityStream;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface ActivityStreamRepository {
    
    public void store(String jsonActivityStream);
    
    public String getJsonActivityStream(int indexNumber);
    
    public ActivityStream getActivityStream(int indexNumber);
    
    public int getTotalActivityStreamSize();
    
}
