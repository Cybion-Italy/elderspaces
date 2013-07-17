package eu.elderspaces.persistence;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;

import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchActivityStreamRepository implements ActivityStreamRepository {

    private static final Logger LOGGER = Logger.getLogger(
            ElasticSearchActivityStreamRepository.class);

    private static final String ELDERSPACES_DB = "es";

    private static final String ACTIVITIES = "activities";

    private final Client client;

    private ObjectMapper mapper;

    private final String host;

    @Inject
    public ElasticSearchActivityStreamRepository(
            @Named("eu.elderspaces.activitystream.repository.host") final String esHost,
            @Named("eu.elderspaces.activitystream.repository.port") final int port,
            final ObjectMapper mapper) {

        this.host = esHost;

        this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(
                this.host, port));
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

        final IndexResponse indexResponse = client.prepareIndex(ELDERSPACES_DB, ACTIVITIES)
                .setSource(activityJson).execute().actionGet();
        LOGGER.debug("ActivityStream stored with index: " + indexResponse.getId());

        this.client.admin().indices().prepareRefresh().execute().actionGet();

        return indexResponse.getId();
    }

    @Override
    public ActivityStream getActivityStream(final String id)
            throws ActivityStreamRepositoryException {

        return null; // To change body of implemented methods use File |
        // Settings | File Templates.
    }

    @Override
    public boolean remove(final String id) throws ActivityStreamRepositoryException {

        return false; // To change body of implemented methods use File |
        // Settings | File Templates.
    }

    @Override
    public long getTotalActivityStreamSize() throws ActivityStreamRepositoryException {

        return 0; // To change body of implemented methods use File | Settings |
        // File Templates.
    }
}
