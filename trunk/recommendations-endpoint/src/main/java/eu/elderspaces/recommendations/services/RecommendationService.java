package eu.elderspaces.recommendations.services;

import it.cybion.commons.web.responses.ResponseStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import eu.elderspaces.recommendations.RecommendationsEndpoint;
import eu.elderspaces.recommendations.Recommender;
import eu.elderspaces.recommendations.exceptions.RecommenderException;
import eu.elderspaces.recommendations.responses.ClubEntry;
import eu.elderspaces.recommendations.responses.ClubRecommendationResponse;
import eu.elderspaces.recommendations.responses.EventEntry;
import eu.elderspaces.recommendations.responses.EventRecommendationResponse;
import eu.elderspaces.recommendations.responses.FriendEntry;
import eu.elderspaces.recommendations.responses.FriendRecommendationResponse;
import eu.elderspaces.recommendations.responses.PaginatedResult;

/**
 * 
 * @author micheleminno
 * 
 */

@Path(RecommendationsEndpoint.REST_RADIX + RecommendationsEndpoint.RECOMMENDATIONS)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
    
    private final Recommender recommender;
    
    @Inject
    public RecommendationService(final Recommender recommender) {
    
        this.recommender = recommender;
    }
    
    @GET
    @Path(RecommendationsEndpoint.FRIENDS + "/{userId}")
    public Response getFriends(@PathParam("userId") final String userId) {
    
        LOGGER.info("Friends recommendation service called with userId: " + userId);
        ResponseBuilder rb = null;
        
        final PaginatedResult<FriendEntry> recommendationReport;
        
        try {
            recommendationReport = recommender.getFriends(userId);
        } catch (final RecommenderException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        rb = Response.ok();
        
        rb.entity(new FriendRecommendationResponse(ResponseStatus.OK, "Friends recommendations",
                recommendationReport));
        
        LOGGER.info("Friend recommendations retrieved");
        
        return rb.build();
    }
    
    @GET
    @Path(RecommendationsEndpoint.EVENTS + "/{userId}")
    public Response getEvents(@PathParam("userId") final String userId) {
    
        LOGGER.info("Events recommendation service called with userId: " + userId);
        ResponseBuilder rb = null;
        
        final PaginatedResult<EventEntry> recommendationReport;
        
        try {
            recommendationReport = recommender.getEvents(userId);
        } catch (final RecommenderException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        rb = Response.ok();
        
        rb.entity(new EventRecommendationResponse(ResponseStatus.OK, "Events recommendations",
                recommendationReport));
        
        LOGGER.info("Event recommendations retrieved");
        
        return rb.build();
    }
    
    @GET
    @Path(RecommendationsEndpoint.CLUBS + "/{userId}")
    public Response getClubs(@PathParam("userId") final String userId) {
    
        LOGGER.info("Clubs recommendation service called with userId: " + userId);
        ResponseBuilder rb = null;
        
        final PaginatedResult<ClubEntry> recommendationReport;
        
        try {
            recommendationReport = recommender.getClubs(userId);
        } catch (final RecommenderException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        rb = Response.ok();
        
        rb.entity(new ClubRecommendationResponse(ResponseStatus.OK, "Clubs recommendations",
                recommendationReport));
        
        LOGGER.info("Club recommendations retrieved");
        
        return rb.build();
    }
    
}
