package eu.elderspaces.recommendations;

import eu.elderspaces.recommendations.exceptions.RecommenderException;
import eu.elderspaces.recommendations.responses.RecommendationReport;

public interface Recommender {
    
    public RecommendationReport getFriends(String userId) throws RecommenderException;
    
    public RecommendationReport getEvents(String userId) throws RecommenderException;
    
    public RecommendationReport getClubs(String userId) throws RecommenderException;
    
}
