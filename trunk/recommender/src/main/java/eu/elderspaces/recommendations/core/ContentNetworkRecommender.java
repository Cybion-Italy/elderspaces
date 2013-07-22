package eu.elderspaces.recommendations.core;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.log4testng.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.persistence.EnrichedEntitiesRepository;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;
import eu.elderspaces.recommendations.core.helpers.MapSorter;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class ContentNetworkRecommender implements Recommender {
    
    public static final Logger LOGGER = Logger.getLogger(ContentNetworkRecommender.class);
    
    private double SN_WEIGHT = 0.5;
    private double CONTENT_WEIGHT = 0.5;
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    private final EnrichedEntitiesRepository enrichedEntitiesRepository;
    private final EntitiesRepository entityRepository;
    private final SocialNetworkRepository socialNetworkRepository;
    
    @Inject
    public ContentNetworkRecommender(
            @Named("eu.elderspaces.recommender.sn-weight") final double snWeight,
            @Named("eu.elderspaces.recommender.content-weight") final double contentWeight,
            final EntitiesRepository entityRepository,
            final EnrichedEntitiesRepository enrichedEntitiesRepository,
            final SocialNetworkRepository socialNetworkRepository) {
    
        this.SN_WEIGHT = snWeight;
        this.CONTENT_WEIGHT = contentWeight;
        this.entityRepository = entityRepository;
        this.enrichedEntitiesRepository = enrichedEntitiesRepository;
        this.socialNetworkRepository = socialNetworkRepository;
    }
    
    @Override
    public PaginatedResult getRecommendedEntities(final String userId,
            final Class<? extends Entity> type) throws RecommenderException {
    
        final List<Entity> result = getRecommendations(userId, type);
        
        return new PaginatedResult(START_INDEX, TOTAL_RESULTS, result);
    }
    
    private List<Entity> getRecommendations(final String userId, final Class<?> targetClass) {
    
        if (targetClass == Person.class) {
            
            return getPersonRecommendation(userId);
            
        } else if (targetClass == Event.class) {
            
            return getEventRecommendation(userId);
            
        } else if (targetClass == Club.class) {
            
            return getClubRecommendation(userId);
        } else {
            LOGGER.error("Unknown targetClass: " + targetClass.getSimpleName());
            return new ArrayList<Entity>();
        }
        
    }
    
    private List<Entity> getPersonRecommendation(final String userId) {
    
        final List<Entity> result = new ArrayList<Entity>();
        
        final Map<String, Double> contentIds = enrichedEntitiesRepository
                .getPersonRecommendations(userId);
        final Map<String, Double> networkIds = socialNetworkRepository.getFriendsOfFriends(userId);
        
        // remove friends from content list that the user already knows
        // it can't happend on the network recommendations thus we don't
        // check
        final Set<String> existingIds = socialNetworkRepository.getFriends(userId);
        for (final String id : existingIds) {
            contentIds.remove(id);
        }
        
        final Map<String, Double> finalRecommendations = processScores(contentIds, networkIds);
        
        for (final String id : finalRecommendations.keySet()) {
            Person person;
            try {
                person = entityRepository.getPerson(id);
            } catch (final RepositoryException e) {
                LOGGER.error(e.getMessage());
                continue;
            }
            result.add(person);
        }
        
        return result;
    }
    
    private List<Entity> getEventRecommendation(final String userId) {
    
        final List<Entity> result = new ArrayList<Entity>();
        
        final Map<String, Double> contentIds = enrichedEntitiesRepository
                .getEventRecommendations(userId);
        final Map<String, Double> networkIds = socialNetworkRepository.getEventsOfFriends(userId);
        
        // remove events from content list that the user already joined
        // it can't happend on the network recommendations thus we don't
        // check
        final Set<String> existingIds = socialNetworkRepository.getEvents(userId);
        for (final String id : existingIds) {
            contentIds.remove(id);
        }
        
        final Map<String, Double> finalRecommendations = processScores(contentIds, networkIds);
        
        for (final String id : finalRecommendations.keySet()) {
            Event event;
            try {
                event = entityRepository.getEvent(id);
            } catch (final RepositoryException e) {
                LOGGER.error(e.getMessage());
                continue;
            }
            result.add(event);
        }
        
        return result;
        
    }
    
    private List<Entity> getClubRecommendation(final String userId) {
    
        final List<Entity> result = new ArrayList<Entity>();
        
        final Map<String, Double> contentIds = enrichedEntitiesRepository
                .getClubRecommendations(userId);
        final Map<String, Double> networkIds = socialNetworkRepository.getClubsOfFriends(userId);
        
        // remove clubs from content list that the user already joined
        // it can't happend on the network recommendations thus we don't
        // check
        final Set<String> existingIds = socialNetworkRepository.getClubs(userId);
        for (final String id : existingIds) {
            contentIds.remove(id);
        }
        
        final Map<String, Double> finalRecommendations = processScores(contentIds, networkIds);
        
        for (final String id : finalRecommendations.keySet()) {
            Club club;
            try {
                club = entityRepository.getClub(id);
            } catch (final RepositoryException e) {
                LOGGER.error(e.getMessage());
                continue;
            }
            result.add(club);
        }
        
        return result;
    }
    
    private Map<String, Double> processScores(final Map<String, Double> contentIds,
            final Map<String, Double> networkIds) {
    
        // get max scores
        double maxContentScore = 0;
        double maxNetworkScore = 0;
        
        for (final String id : contentIds.keySet()) {
            if (contentIds.get(id) > maxContentScore) {
                maxContentScore = contentIds.get(id);
            }
        }
        for (final String id : networkIds.keySet()) {
            if (networkIds.get(id) > maxNetworkScore) {
                maxNetworkScore = networkIds.get(id);
            }
        }
        
        // weight scores
        for (final String id : contentIds.keySet()) {
            contentIds.put(id, (contentIds.get(id) * CONTENT_WEIGHT) / maxContentScore);
        }
        for (final String id : networkIds.keySet()) {
            networkIds.put(id, (networkIds.get(id) * SN_WEIGHT) / maxNetworkScore);
        }
        
        // pour network recommentations in the content-based one summing up
        // weights of same entities
        for (final String id : networkIds.keySet()) {
            if (contentIds.containsKey(id)) {
                contentIds.put(id, contentIds.get(id) + networkIds.get(id));
            } else {
                contentIds.put(id, networkIds.get(id));
            }
        }
        
        // sort
        final Map<String, Double> orderedMap = MapSorter.sortByValues(contentIds);
        
        return orderedMap;
    }
    
}
