package eu.elderspaces.activities.persistence;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ElasticSearchActivityRepository implements ActivityRepository {
    
    private static final Logger logger = Logger.getLogger(ElasticSearchActivityRepository.class);
    
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
    
}
