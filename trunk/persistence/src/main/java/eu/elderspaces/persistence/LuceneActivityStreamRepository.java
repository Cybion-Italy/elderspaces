package eu.elderspaces.persistence;

import eu.elderspaces.model.ActivityStream;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class LuceneActivityStreamRepository implements ActivityStreamRepository {
    
    public LuceneActivityStreamRepository() {
    
    }
    
    @Override
    public void store(final String jsonActivityStream) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getJsonActivityStream(final int indexNumber) {
    
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ActivityStream getActivityStream(final int indexNumber) {
    
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getTotalActivityStreamSize() {
    
        // TODO Auto-generated method stub
        return 0;
    }
    
}
