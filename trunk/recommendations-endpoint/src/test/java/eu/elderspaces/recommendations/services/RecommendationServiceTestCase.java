package eu.elderspaces.recommendations.services;

import it.cybion.commons.AbstractJerseyRESTTestCase;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.RecommendationsEndpoint;
import eu.elderspaces.recommendations.responses.EntityRecommendationResponse;

@Test
public class RecommendationServiceTestCase extends AbstractJerseyRESTTestCase {
    
    private static final int ENTRIES_SIZE = 3;
    private static final String GET_FRIENDS = RecommendationsEndpoint.FRIENDS + "/1";
    private static final String GET_EVENTS = RecommendationsEndpoint.EVENTS + "/1";
    private static final String GET_CLUBS = RecommendationsEndpoint.CLUBS + "/1";
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RecommendationServiceTestCase.class);
    
    public RecommendationServiceTestCase() {
    
        super(PORT, TestServiceConfig.class.getName());
    }
    
    @Test
    public void getFriends() throws JsonParseException, JsonMappingException, CybionHttpException,
            IOException {
    
        final PaginatedResult recommendationReport = getEntityRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_FRIENDS);
        LOGGER.info("Recommendations computed: " + recommendationReport);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    @Test
    public void getEvents() throws JsonParseException, JsonMappingException, CybionHttpException,
            IOException {
    
        final PaginatedResult recommendationReport = getEntityRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_EVENTS);
        LOGGER.info("Recommendations computed: " + recommendationReport);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    @Test
    public void getClubs() throws JsonParseException, JsonMappingException, CybionHttpException,
            IOException {
    
        final PaginatedResult recommendationReport = getEntityRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_CLUBS);
        LOGGER.info("Recommendations computed: " + recommendationReport);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    private PaginatedResult getEntityRecommendations(final String serviceCall)
            throws CybionHttpException, JsonParseException, JsonMappingException, IOException {
    
        final String url = base_uri + serviceCall;
        final ExternalStringResponse getReportResponse = CybionHttpClient.performGet(url, null);
        
        final String responseObject = getReportResponse.getObject();
        
        return mapper.readValue(responseObject, EntityRecommendationResponse.class).getObject();
    }
}
