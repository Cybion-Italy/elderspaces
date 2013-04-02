package eu.elderspaces.recommendations;

import eu.elderspaces.recommendations.exceptions.RecommenderException;
import eu.elderspaces.recommendations.responses.ClubEntry;
import eu.elderspaces.recommendations.responses.EventEntry;
import eu.elderspaces.recommendations.responses.FriendEntry;
import eu.elderspaces.recommendations.responses.PaginatedResult;

public interface Recommender {
    
    public PaginatedResult<FriendEntry> getFriends(String userId) throws RecommenderException;
    
    public PaginatedResult<EventEntry> getEvents(String userId) throws RecommenderException;
    
    public PaginatedResult<ClubEntry> getClubs(String userId) throws RecommenderException;
    
}
