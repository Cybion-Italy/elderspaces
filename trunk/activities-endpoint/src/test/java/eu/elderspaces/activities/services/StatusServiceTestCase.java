package eu.elderspaces.activities.services;

import com.google.common.collect.Maps;
import eu.elderspaces.activities.ActivitiesEndpoint;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.services.exceptions.CybionHttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class StatusServiceTestCase extends BaseServiceTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusServiceTestCase.class);

    @Test
    public void servicesShouldBeUpAndRunning() throws CybionHttpException {

        final String url = super.base_uri + ActivitiesEndpoint.STATUS_RADIX + ActivitiesEndpoint.NOW;

        ExternalStringResponse stringResponse = null;

        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);

        LOGGER.debug("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.OK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
}
