package eu.elderspaces.recommendations.services;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.services.base.JsonService;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import eu.elderspaces.ProductionCacheModule;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.persistence.EnrichedEntitiesRepository;
import eu.elderspaces.recommendations.RecommendationsEndpoint;
import eu.elderspaces.recommendations.core.ContentNetworkRecommender;
import eu.elderspaces.recommendations.core.Recommender;
import eu.elderspaces.recommendations.exceptions.RecommenderException;
import eu.elderspaces.recommendations.responses.EntityRecommendationResponse;

/**
 * 
 * @author micheleminno
 * 
 */

@Path(RecommendationsEndpoint.REST_RADIX + RecommendationsEndpoint.RECOMMENDATIONS)
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationService extends JsonService {
    
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
        return getRecommendationResponse(userId);
    }
    
    @GET
    @Path(RecommendationsEndpoint.EVENTS + "/{userId}")
    public Response getEvents(@PathParam("userId") final String userId) {
    
        LOGGER.info("Events recommendation service called with userId: " + userId);
        return getRecommendationResponse(userId);
    }
    
    @GET
    @Path(RecommendationsEndpoint.CLUBS + "/{userId}")
    public Response getClubs(@PathParam("userId") final String userId) {
    
        LOGGER.info("Clubs recommendation service called with userId: " + userId);
        return getRecommendationResponse(userId);
    }
    
    private Response getRecommendationResponse(final String userId) {
    
        final PaginatedResult recommendationReport;
        
        try {
            recommendationReport = recommender.getRecommendedEntities(userId, Event.class);
        } catch (final RecommenderException e) {
            return error(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
        LOGGER.info("Event recommendations retrieved");
        
        return success(new EntityRecommendationResponse(ResponseStatus.OK,
                "Events recommendations", recommendationReport));
    }
    
    /*
     * 1. shutdown existing enriched repository 2. get an injector with a custom
     * module containing only properties and enrichedrepository provider 3.
     * clean directory 4. get new instance 5. notify recommender to update
     * enriched repository by providing new instance
     */
    @GET
    @Path(RecommendationsEndpoint.UPDATE_CACHE + "/{password}")
    public Response updateCache(@PathParam("password") final String password) {
    
        if (!password.equals("BigBang1255")) {
            return error("Oooops got ya!");
        }
        
        ((ContentNetworkRecommender) recommender).shutdownEnrichedRepository();
        
        final Injector injector = Guice.createInjector(new ProductionCacheModule());
        
        // clean cache directory (alias enrichedEntitiesRepository)
        
        final String cacheDirPath = injector.getInstance(Key.get(String.class,
                Names.named("eu.elderspaces.repository.enriched-entities")));
        try {
            FileUtils.cleanDirectory(new File(cacheDirPath));
        } catch (final IOException e) {
            return error(e, "Could not clean cache directory located at: " + cacheDirPath);
        }
        
        // get new instance from injector
        final EnrichedEntitiesRepository enrichedEntitiesRepository = injector
                .getInstance(EnrichedEntitiesRepository.class);
        
        // rebuild cache
        try {
            ((ContentNetworkRecommender) recommender)
                    .updateEnrichedEntitiesRepository(enrichedEntitiesRepository);
        } catch (final RecommenderException e) {
            return error(e, e.getMessage());
        }
        
        return success("Updated Cache");
        
    }
}
