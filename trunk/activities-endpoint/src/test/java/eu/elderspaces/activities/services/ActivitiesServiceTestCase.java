package eu.elderspaces.activities.services;

import com.google.common.collect.Maps;
import eu.elderspaces.activities.ActivitiesEndpoint;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.StringResponse;
import it.cybion.commons.web.services.exceptions.CybionHttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;

import static org.testng.Assert.assertTrue;

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
        String emptyRequestEntity = "";
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
        String requestEntity = "not-empty-entity";
        final ExternalStringResponse stringResponse = CybionHttpClient.performPost(
                url,
                requestHeaderMap,
                requestEntity);

        LOGGER.debug("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.OK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
}
