package eu.elderspaces.persistence;

import java.util.List;

import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface ActivityStreamRepository {
    
    String store(ActivityStream activityStream) throws ActivityStreamRepositoryException;
    
    ActivityStream getActivityStream(String id) throws ActivityStreamRepositoryException;
    
    long getTotalActivityStreamSize() throws ActivityStreamRepositoryException;
    
    void shutDownRepository() throws ActivityStreamRepositoryException;
    
    List<String> getAllActivityStreams(int maxSize) throws ActivityStreamRepositoryException;
    
}
