package eu.elderspaces.persistence;

import eu.elderspaces.model.ActivityStream;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepository implements ActivityStreamRepository {

    @Override
    public String store(ActivityStream activityStream) {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityStream getActivityStream(String id) {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(String id) {

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getTotalActivityStreamSize() {

        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
