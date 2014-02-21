package eu.elderspaces.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.google.inject.Inject;

import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepository implements ActivityStreamRepository {
    
    private static final Logger LOGGER = Logger
            .getLogger(ElasticSearchActivityStreamRepository.class);
    
    private static final String ELDERSPACES_INDEX = "elderspaces";
    
    private static final String ACTIVITY_STREAM_TYPE = ActivityStream.class.getSimpleName();
    
    private final Client client;
    
    private final ObjectMapper mapper;
    
    @Inject
    public ElasticSearchActivityStreamRepository(final Client client, final ObjectMapper mapper) {
    
        this.client = client;
        this.mapper = mapper;
    }
    
    @Override
    public void shutDownRepository() {
    
        client.close();
        
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
        
        final IndexResponse indexResponse = client
                .prepareIndex(ELDERSPACES_INDEX, ACTIVITY_STREAM_TYPE).setOperationThreaded(false)
                .setSource(activityJson).execute().actionGet();
        LOGGER.debug("ActivityStream stored with index: " + indexResponse.getId());
        
        this.client.admin().indices().prepareRefresh().execute().actionGet();
        
        return indexResponse.getId();
    }
    
    @Override
    public ActivityStream getActivityStream(final String id)
            throws ActivityStreamRepositoryException {
    
        final GetResponse response = client.prepareGet(ELDERSPACES_INDEX, ACTIVITY_STREAM_TYPE, id)
                .setOperationThreaded(false).setRefresh(true).execute().actionGet();
        
        ActivityStream activityStream;
        try {
            final String json = response.getSourceAsString();
            activityStream = mapper.readValue(json, ActivityStream.class);
        } catch (final JsonParseException e) {
            throw new ActivityStreamRepositoryException(e.getMessage(), e);
        } catch (final JsonMappingException e) {
            throw new ActivityStreamRepositoryException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ActivityStreamRepositoryException(e.getMessage(), e);
        }
        
        return activityStream;
    }
    
    @Override
    public long getTotalActivityStreamSize() throws ActivityStreamRepositoryException {
    
        final CountResponse response = client.prepareCount(ELDERSPACES_INDEX)
                .setQuery(QueryBuilders.matchQuery("_type", ACTIVITY_STREAM_TYPE)).execute()
                .actionGet();
        
        return response.getCount();
    }
    
    @Override
    public List<String> getAllActivityStreams(int maxSize) throws ActivityStreamRepositoryException {
    
        SearchResponse response = client.prepareSearch(ELDERSPACES_INDEX)
                .setTypes(ACTIVITY_STREAM_TYPE).setSize(maxSize).execute().actionGet();
        
        List<String> streams = new ArrayList<String>();
        for (SearchHit hit : response.getHits().getHits())
            streams.add(hit.getSourceAsString());
        
        return streams;
    }
}
