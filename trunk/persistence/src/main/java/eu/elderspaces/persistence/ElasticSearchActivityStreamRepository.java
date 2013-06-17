package eu.elderspaces.persistence;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.utils.ActivityStreamObjectMapper;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepository implements ActivityStreamRepository {
    
    private static final Logger LOGGER = Logger
            .getLogger(ElasticSearchActivityStreamRepository.class);
    private static final String ACTIVITY_INDEX = "user-";
    private static final String ACTIVITY_TYPE = "activity";
    
    private final Node node;
    private final Client client;
    private final ObjectMapper mapper;
    
    public ElasticSearchActivityStreamRepository() {
    
        node = NodeBuilder.nodeBuilder().node();
        client = node.client();
        mapper = ActivityStreamObjectMapper.getDefaultMapper();
    }
    
    @Override
    public void shutDownRepository() {
    
        client.close();
        node.close();
    }
    
    @Override
    public String store(final ActivityStream activityStream)
            throws ActivityStreamRepositoryException {
    
        String activityJson;
        try {
            activityJson = mapper.writeValueAsString(activityStream);
        } catch (final Exception e) {
            throw new ActivityStreamRepositoryException(e);
        }
        
        final IndexResponse indexResponse = client.prepareIndex(ACTIVITY_INDEX, ACTIVITY_TYPE)
                .setSource(activityJson).execute().actionGet();
        LOGGER.debug("ActivityStream stored with index: " + indexResponse.getId());
        
        node.client().admin().indices().prepareRefresh().execute().actionGet();
        
        return indexResponse.getId();
    }
    
    @Override
    public String store(final String activityStreamJSON) {
    
        final IndexResponse indexResponse = client.prepareIndex(ACTIVITY_INDEX, ACTIVITY_TYPE)
                .setSource(activityStreamJSON).execute().actionGet();
        LOGGER.debug("ActivityStream stored with index: " + indexResponse.getId());
        
        node.client().admin().indices().prepareRefresh().execute().actionGet();
        
        return indexResponse.getId();
    }
    
    @Override
    public ActivityStream getActivityStream(final String id) {
    
        return null; // To change body of implemented methods use File |
                     // Settings | File Templates.
    }
    
    @Override
    public boolean remove(final String id) {
    
        return false; // To change body of implemented methods use File |
                      // Settings | File Templates.
    }
    
    @Override
    public long getTotalActivityStreamSize() {
    
        return 0; // To change body of implemented methods use File | Settings |
                  // File Templates.
    }
}
