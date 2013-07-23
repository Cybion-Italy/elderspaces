package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.List;
import java.util.Map;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.persistence.exceptions.EnrichedEntitiesRepositoryException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface EnrichedEntitiesRepository {
    
    public void storeEnrichedPerson(Person person, List<Activity> activities, List<Club> clubs,
            List<Event> events) throws EnrichedEntitiesRepositoryException;
    
    public void storeEnrichedClub(Club club, List<Person> members)
            throws EnrichedEntitiesRepositoryException;
    
    public void storeEnrichedEvent(Event event, List<Person> members)
            throws EnrichedEntitiesRepositoryException;
    
    public Map<String, Double> getPersonRecommendations(String userId);
    
    public Map<String, Double> getClubRecommendations(String userId);
    
    public Map<String, Double> getEventRecommendations(String userId);
    
    public void buildEnrichedEntities(final EntitiesRepository entitiesRepository,
            final SocialNetworkRepository snRepository) throws EnrichedEntitiesRepositoryException;
    
    public void shutdown() throws RepositoryException;
}
