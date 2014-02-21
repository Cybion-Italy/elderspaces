package eu.elderspaces.activities.services;

import it.cybion.commons.web.services.base.JsonService;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import eu.elderspaces.GuiceFactory;
import eu.elderspaces.activities.ActivitiesEndpoint;
import eu.elderspaces.activities.core.ActivityStreamManager;
import eu.elderspaces.activities.core.exceptions.ActivityManagerException;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;
import eu.elderspaces.recommendations.services.RecommendationService;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Path(ActivitiesEndpoint.REST_RADIX + ActivitiesEndpoint.ACTIVITIES)
@Produces(MediaType.APPLICATION_JSON)
public class ActivitiesService extends JsonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesService.class);
    
    private final ActivityStreamManager activityManager;
    
    private String entitiesDirectoryPath;
    private String socialNetworkRepository;
    
    @Inject
    public ActivitiesService(final ActivityStreamManager activityManager,
            @Named("eu.elderspaces.repository.entities") final String entitiesDirectoryPath,
            @Named("eu.elderspaces.repository.social-network") final String socialNetworkRepository) {
    
        this.activityManager = activityManager;
        this.entitiesDirectoryPath = entitiesDirectoryPath;
        this.socialNetworkRepository = socialNetworkRepository;
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
            stored = activityManager.storeActivity(activityContent, true);
        } catch (final ActivityManagerException e) {
            LOGGER.error(e.getMessage());
            return error(Response.Status.NOT_ACCEPTABLE, e.getMessage());
        }
        
        if (stored) {
            return success("Activity stored");
        } else {
            return error("Error while storing activity");
        }
    }
    
    @GET
    @Path(ActivitiesEndpoint.REPLAY + "/{password}")
    public Response replayActivities(@PathParam("password") final String password) {
    
        if (!password.equals("BigBang1255")) {
            return error("Oooops got ya!");
        }
        
        LOGGER.info("Replaying activity stream...");
        
        List<String> streams = new ArrayList<String>();
        try {
            streams = activityManager.getAllActivities(10000);
            LOGGER.info("Loaded " + streams.size() + " activities");
            cleanAll();
        } catch (ActivityManagerException e) {
            LOGGER.error(e.getMessage());
            return error(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
        for (int i = 0; i < streams.size(); i++) {
            
            LOGGER.info("Replaying activity " + i + " / " + streams.size());
            try {
                boolean stored = activityManager.storeActivity(streams.get(i), false);
                if (!stored)
                    LOGGER.error("Couldn't replay activity " + i);
            } catch (Exception e) {
                LOGGER.error("Couldn't replay activity " + i + " : " + e.getMessage());
                e.printStackTrace();
                continue;
            }
            
        }
        
        // rebuild cache
        GuiceFactory.getInjector().getInstance(RecommendationService.class).updateCache(password);
        
        return success("Replay completed!");
        
    }
    
    private void cleanAll() throws ActivityManagerException {
    
        try {
            
            GuiceFactory.getInjector().getInstance(EntitiesRepository.class).reset();;
            
            GuiceFactory.getInjector().getInstance(SocialNetworkRepository.class).reset();
            
        } catch (Exception e) {
            throw new ActivityManagerException("Couldn't clean repositories: " + e.getMessage());
        }
        
    }
    
}
