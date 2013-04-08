package eu.elderspaces.recommendations.core;

import eu.elderspaces.activities.exceptions.NonExistentUser;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public interface Recommender {
    
    public PaginatedResult<Person> getFriends(String userId) throws RecommenderException,
            NonExistentUser;
    
    public PaginatedResult<Event> getEvents(String userId) throws RecommenderException;
    
    public PaginatedResult<Club> getClubs(String userId) throws RecommenderException;
    
}
