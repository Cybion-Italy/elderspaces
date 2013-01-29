package eu.elderspaces.activities.services;

import com.google.inject.Inject;
import eu.elderspaces.activities.ActivitiesEndpoint;
import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.StringResponse;
import it.cybion.commons.web.services.base.JsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Path(ActivitiesEndpoint.ACTIVITY)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ActivitiesService extends JsonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

    @Inject
    public ActivitiesService() {
    }

    @POST
    public Response createActivity(final String activityContent) {

        if (activityContent == null || activityContent.length() == 0) {
            return error("empty parameter");
        } else {
            Response.ResponseBuilder rb = Response.ok().type(MediaType.APPLICATION_JSON);
            rb.entity(new StringResponse(ResponseStatus.OK,
                    "received activity content: '" + activityContent + "'"));
            return rb.build();
        }
    }

}
