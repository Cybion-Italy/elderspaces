package eu.elderspaces.recommendations.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import eu.elderspaces.recommendations.RecommendationsEndpoint;

/**
 * 
 * @author micheleminno
 * 
 */

@Path(RecommendationsEndpoint.RECOMMENDATIONS)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
    
    @Inject
    public RecommendationService() {
    
    }
    
    @GET
    @Path(RecommendationsEndpoint.FRIENDS + "/{userId}")
    public Response getFriends(@PathParam("userId") final String userId) {
    
        return null;
    }
    
    @GET
    @Path(RecommendationsEndpoint.EVENTS + "/{userId}")
    public Response getEvents(@PathParam("userId") final String userId) {
    
        return null;
    }
    
    @GET
    @Path(RecommendationsEndpoint.CLUBS + "/{userId}")
    public Response getClubs(@PathParam("userId") final String userId) {
    
        return null;
    }
    
}
