package eu.elderspaces.recommendations;

import it.cybion.commons.AbstractJerseyRESTTestCase;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import eu.elderspaces.recommendations.responses.RecommendationReport;
import eu.elderspaces.recommendations.responses.RecommendationResponse;

@Test
public class RecommendationServiceTestCase extends AbstractJerseyRESTTestCase {
    
    private static final String GET_FRIENDS = "";
    private static final String GET_EVENTS = "";
    private static final String GET_CLUBS = "";
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    private static final Logger logger = Logger.getLogger(RecommendationServiceTestCase.class);
    
    public RecommendationServiceTestCase() {
    
        super(PORT, TestServiceConfig.class.getName());
    }
    
    @Test
    public void getFriends() throws JsonParseException, JsonMappingException, CybionHttpException,
            IOException {
    
        final RecommendationReport recommendationReport = getRecommendationReport(GET_FRIENDS);
    }
    
    @Test
    public void getEvents() {
    
    }
    
    @Test
    public void getClubs() {
    
    }
    
    private RecommendationReport getRecommendationReport(final String serviceCall)
            throws CybionHttpException, JsonParseException, JsonMappingException, IOException {
    
        final ExternalStringResponse getReportResponse = CybionHttpClient.performGet(base_uri
                + serviceCall, null);
        
        final String responseObject = getReportResponse.getObject();
        
        final RecommendationResponse getReportResult = mapper.readValue(responseObject,
                RecommendationResponse.class);
        
        return getReportResult.getObject();
    }
}
