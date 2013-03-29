package eu.elderspaces.activities.services;

import static org.testng.Assert.assertTrue;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;

import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;

import eu.elderspaces.activities.ActivitiesEndpoint;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ActivitiesServiceTestCase extends BaseServiceTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesServiceTestCase.class);
    
    @Test
    public void givenOneEmptyActivityShouldGetNOKStatus() throws CybionHttpException {
        
        final String url = super.base_uri + ActivitiesEndpoint.ACTIVITY;
        
        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        requestHeaderMap.put(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN);
        final String emptyRequestEntity = "";
        final ExternalStringResponse stringResponse = CybionHttpClient.performPost(
                url,
                requestHeaderMap,
                emptyRequestEntity);
        
        LOGGER.debug("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.NOK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
    
    @Test
    public void givenOneActivityShouldGetOKStatus() throws CybionHttpException {
        
        final String url = super.base_uri + ActivitiesEndpoint.ACTIVITY;
        
        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        requestHeaderMap.put(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN);
        final String requestEntity = "not-empty-entity";
        final ExternalStringResponse stringResponse = CybionHttpClient.performPost(
                url,
                requestHeaderMap,
                requestEntity);
        
        LOGGER.debug("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.OK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
}
