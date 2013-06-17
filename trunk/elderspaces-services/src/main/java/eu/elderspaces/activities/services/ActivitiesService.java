package eu.elderspaces.activities.services;

import it.cybion.commons.web.services.base.JsonService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import eu.elderspaces.activities.ActivitiesEndpoint;
import eu.elderspaces.activities.core.ActivityManager;
import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Path(ActivitiesEndpoint.REST_RADIX + ActivitiesEndpoint.ACTIVITIES)
@Produces(MediaType.APPLICATION_JSON)
public class ActivitiesService extends JsonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesService.class);
    
    private final ActivityManager activityManager;
    
    @Inject
    public ActivitiesService(final ActivityManager activityManager) {
    
        this.activityManager = activityManager;
    }
    
    @POST
    @Path(ActivitiesEndpoint.STORE)
    public Response storeActivity(final String activityContent) {
    
        LOGGER.debug("Store activity service called on:");
        LOGGER.debug(activityContent);
        
        if ((activityContent == null) || (activityContent.length() == 0)) {
            return error("empty parameter");
        }
        
        boolean stored;
        try {
            stored = activityManager.storeActivity(activityContent);
        } catch (final InvalidUserActivity e) {
            return error(Response.Status.NOT_ACCEPTABLE, e.getMessage());
        } catch (final ActivityRepositoryException e) {
            return error(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
        if (stored) {
            return success("Activity stored");
        } else {
            return error("Error while storing activity");
        }
    }
}
