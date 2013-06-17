package eu.elderspaces.persistence;

import eu.elderspaces.model.ActivityStream;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface ActivitiesStreamRepository {
    
    public void dispatchActivyStream(ActivityStream activityStream);
}
