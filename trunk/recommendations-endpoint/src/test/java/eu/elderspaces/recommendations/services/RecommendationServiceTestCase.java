package eu.elderspaces.recommendations.services;

import it.cybion.commons.AbstractJerseyRESTTestCase;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import eu.elderspaces.recommendations.RecommendationsEndpoint;
import eu.elderspaces.recommendations.model.ClubEntry;
import eu.elderspaces.recommendations.model.EventEntry;
import eu.elderspaces.recommendations.model.FriendEntry;
import eu.elderspaces.recommendations.model.PaginatedResult;
import eu.elderspaces.recommendations.responses.ClubRecommendationResponse;
import eu.elderspaces.recommendations.responses.EventRecommendationResponse;
import eu.elderspaces.recommendations.responses.FriendRecommendationResponse;

@Test
public class RecommendationServiceTestCase extends AbstractJerseyRESTTestCase {
    
    private static final int ENTRIES_SIZE = 3;
    private static final String GET_FRIENDS = RecommendationsEndpoint.FRIENDS + "/1";
    private static final String GET_EVENTS = RecommendationsEndpoint.EVENTS + "/1";
    private static final String GET_CLUBS = RecommendationsEndpoint.CLUBS + "/1";
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    private static final Logger LOGGER = Logger.getLogger(RecommendationServiceTestCase.class);
    
    public RecommendationServiceTestCase() {
        
        super(PORT, TestServiceConfig.class.getName());
    }
    
    @Test
    public void getFriends() throws JsonParseException, JsonMappingException, CybionHttpException,
    IOException {
        
        final PaginatedResult<FriendEntry> recommendationReport = getFriendRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_FRIENDS);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    @Test
    public void getEvents() throws JsonParseException, JsonMappingException, CybionHttpException,
    IOException {
        
        final PaginatedResult<EventEntry> recommendationReport = getEventRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_EVENTS);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    @Test
    public void getClubs() throws JsonParseException, JsonMappingException, CybionHttpException,
    IOException {
        
        final PaginatedResult<ClubEntry> recommendationReport = getClubRecommendations(RecommendationsEndpoint.REST_RADIX
                + RecommendationsEndpoint.RECOMMENDATIONS + GET_CLUBS);
        Assert.assertEquals(ENTRIES_SIZE, recommendationReport.getEntries().size());
        
    }
    
    private PaginatedResult<FriendEntry> getFriendRecommendations(final String serviceCall)
            throws CybionHttpException, JsonParseException, JsonMappingException, IOException {
        
        final ExternalStringResponse getReportResponse = CybionHttpClient.performGet(base_uri
                + serviceCall, null);
        
        final String responseObject = getReportResponse.getObject();
        
        final FriendRecommendationResponse getReportResult = mapper.readValue(responseObject,
                FriendRecommendationResponse.class);
        
        return getReportResult.getObject();
    }
    
    private PaginatedResult<EventEntry> getEventRecommendations(final String serviceCall)
            throws CybionHttpException, JsonParseException, JsonMappingException, IOException {
        
        final ExternalStringResponse getReportResponse = CybionHttpClient.performGet(base_uri
                + serviceCall, null);
        
        final String responseObject = getReportResponse.getObject();
        
        final EventRecommendationResponse getReportResult = mapper.readValue(responseObject,
                EventRecommendationResponse.class);
        
        return getReportResult.getObject();
    }
    
    private PaginatedResult<ClubEntry> getClubRecommendations(final String serviceCall)
            throws CybionHttpException, JsonParseException, JsonMappingException, IOException {
        
        final ExternalStringResponse getReportResponse = CybionHttpClient.performGet(base_uri
                + serviceCall, null);
        
        final String responseObject = getReportResponse.getObject();
        
        final ClubRecommendationResponse getReportResult = mapper.readValue(responseObject,
                ClubRecommendationResponse.class);
        
        return getReportResult.getObject();
    }
}
