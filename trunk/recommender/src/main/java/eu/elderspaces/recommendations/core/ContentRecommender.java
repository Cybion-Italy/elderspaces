package eu.elderspaces.recommendations.core;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.log4testng.Logger;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

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

public class ContentRecommender implements Recommender {
    
    public static final Logger LOGGER = Logger.getLogger(ContentRecommender.class);
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    private final EnrichedEntitiesRepository enrichedEntitiesRepository;
    private final EntitiesRepository entityRepository;
    private final SocialNetworkRepository socialNetworkRepository;
    
    @Inject
    public ContentRecommender(final EntitiesRepository entityRepository,
            final EnrichedEntitiesRepository enrichedEntitiesRepository,
            final SocialNetworkRepository socialNetworkRepository) {
    
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
    
        final List<Entity> result = Lists.newArrayList();
        
        if (targetClass == Person.class) {
            Map<String, Double> recommendedIds = enrichedEntitiesRepository
                    .getPersonRecommendations(userId);
            final Set<String> existingIds = socialNetworkRepository.getFriends(userId);
            
            // remove persons that are friend already
            for (final String id : existingIds) {
                recommendedIds.remove(id);
            }
            
            recommendedIds = MapSorter.sortByValues(recommendedIds);
            
            for (final String id : recommendedIds.keySet()) {
                Person person;
                try {
                    person = entityRepository.getPerson(id);
                } catch (final RepositoryException e) {
                    LOGGER.error(e.getMessage());
                    continue;
                }
                result.add(person);
            }
            
        } else if (targetClass == Event.class) {
            
            Map<String, Double> recommendedIds = enrichedEntitiesRepository
                    .getEventRecommendations(userId);
            final Set<String> existingIds = socialNetworkRepository.getEvents(userId);
            
            // remove events the user participates already
            for (final String id : existingIds) {
                recommendedIds.remove(id);
            }
            
            recommendedIds = MapSorter.sortByValues(recommendedIds);
            
            for (final String id : recommendedIds.keySet()) {
                Event event;
                try {
                    event = entityRepository.getEvent(id);
                } catch (final RepositoryException e) {
                    LOGGER.error(e.getMessage());
                    continue;
                }
                result.add(event);
            }
        } else if (targetClass == Club.class) {
            
            Map<String, Double> recommendedIds = enrichedEntitiesRepository
                    .getClubRecommendations(userId);
            final Set<String> existingIds = socialNetworkRepository.getClubs(userId);
            
            // remove clubs the user participates already
            for (final String id : existingIds) {
                recommendedIds.remove(id);
            }
            
            recommendedIds = MapSorter.sortByValues(recommendedIds);
            
            for (final String id : recommendedIds.keySet()) {
                Club club;
                try {
                    club = entityRepository.getClub(id);
                } catch (final RepositoryException e) {
                    LOGGER.error(e.getMessage());
                    continue;
                }
                result.add(club);
            }
        }
        
        return result;
    }
    
}
