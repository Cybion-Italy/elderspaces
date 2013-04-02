package eu.elderspaces.recommendations.core;

import eu.elderspaces.recommendations.exceptions.RecommenderException;
import eu.elderspaces.recommendations.model.ClubEntry;
import eu.elderspaces.recommendations.model.EventEntry;
import eu.elderspaces.recommendations.model.FriendEntry;
import eu.elderspaces.recommendations.model.PaginatedResult;

public interface Recommender {
    
    public PaginatedResult<FriendEntry> getFriends(String userId) throws RecommenderException;
    
    public PaginatedResult<EventEntry> getEvents(String userId) throws RecommenderException;
    
    public PaginatedResult<ClubEntry> getClubs(String userId) throws RecommenderException;
    
}
