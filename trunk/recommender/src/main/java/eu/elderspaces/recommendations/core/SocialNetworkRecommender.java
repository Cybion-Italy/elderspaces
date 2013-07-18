package eu.elderspaces.recommendations.core;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.List;
import java.util.Set;

import org.testng.log4testng.Logger;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class SocialNetworkRecommender implements Recommender {
    
    public static final Logger LOGGER = Logger.getLogger(SocialNetworkRecommender.class);
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    private final SocialNetworkRepository socialNetworkRepository;
    private final EntitiesRepository entityRepository;
    
    @Inject
    public SocialNetworkRecommender(final SocialNetworkRepository socialNetworkRepository,
            final EntitiesRepository entityRepository) {
    
        this.socialNetworkRepository = socialNetworkRepository;
        this.entityRepository = entityRepository;
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
            final Set<String> ids = socialNetworkRepository.getFriendsOfFriends(userId);
            for (final String id : ids) {
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
            final Set<String> ids = socialNetworkRepository.getEventsOfFriends(userId);
            for (final String id : ids) {
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
            final Set<String> ids = socialNetworkRepository.getClubsOfFriends(userId);
            for (final String id : ids) {
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
