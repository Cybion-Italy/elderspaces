package eu.elderspaces.activities.persistence;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;

public class ElasticSearchActivityRepository implements ActivityRepository {
    
    private static final Logger LOGGER = Logger.getLogger(ElasticSearchActivityRepository.class);
    private static final String CALL_INDEX = "user-";
    private static final String CALL_TYPE = "call";
    
    private final Node node;
    private final Client client;
    private final ObjectMapper mapper;
    
    public ElasticSearchActivityRepository() {
    
        node = NodeBuilder.nodeBuilder().node();
        client = node.client();
        mapper = new ObjectMapper();
        
        // prevent null values to be serialized in JSON
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }
    
    @Override
    public void shutDownRepository() {
    
        client.close();
        node.close();
    }
    
    @Override
    public boolean store(final Call call, final String userId) throws ActivityRepositoryException {
    
        String callJson;
        try {
            callJson = mapper.writeValueAsString(call);
        } catch (final Exception e) {
            throw new ActivityRepositoryException(e);
        }
        
        final IndexResponse indexResponse = client
                .prepareIndex(CALL_INDEX + userId, CALL_TYPE, call.getId()).setSource(callJson)
                .execute().actionGet();
        LOGGER.info("tweet stored with index: " + indexResponse.getId());
        
        node.client().admin().indices().prepareRefresh().execute().actionGet();
        
        return true;
    }
    
    @Override
    public boolean store(final String callString, final String userId) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
}
