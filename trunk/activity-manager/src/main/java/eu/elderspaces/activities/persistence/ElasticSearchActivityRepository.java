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
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

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
    
    @Override
    public boolean addFriend(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean removeFriend(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean updateUser(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteUser(final Person user, final Person personObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject, final Event target) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean addPost(final Person user, final Post postObject, final Club target) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject, final Event targetEvent) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deletePost(final Person user, final Post postObject, final Club targetClub) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean modifyEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createRSVPResponseToEvent(final Person user, final Event eventObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean createClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean modifyClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean deleteClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean joinClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean leaveClub(final Person user, final Club clubObject) {
    
        // TODO Auto-generated method stub
        return false;
    }
    
}
