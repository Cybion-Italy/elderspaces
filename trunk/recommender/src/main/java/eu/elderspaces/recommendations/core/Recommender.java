package eu.elderspaces.recommendations.core;

import eu.elderspaces.model.Entity;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public interface Recommender {
    
    PaginatedResult getRecommendedEntities(String userId, Class<? extends Entity> type)
            throws RecommenderException;
    
}
