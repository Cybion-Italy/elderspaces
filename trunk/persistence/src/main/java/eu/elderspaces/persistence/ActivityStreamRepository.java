package eu.elderspaces.persistence;

import eu.elderspaces.model.ActivityStream;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface ActivityStreamRepository {

    String store(ActivityStream activityStream);

    ActivityStream getActivityStream(String id);

    boolean remove(String id);

    long getTotalActivityStreamSize();

}
