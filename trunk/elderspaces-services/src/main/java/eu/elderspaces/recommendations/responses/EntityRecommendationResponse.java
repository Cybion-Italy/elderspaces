package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.model.recommendations.PaginatedResult;

public class EntityRecommendationResponse extends ServiceResponse<PaginatedResult> {
    
    private PaginatedResult entityPaginatedResult;
    
    public EntityRecommendationResponse() {
    
    }
    
    public EntityRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult entityPaginatedResult) {
    
        super(status, message);
        this.entityPaginatedResult = entityPaginatedResult;
    }
    
    @Override
    public PaginatedResult getObject() {
    
        return this.entityPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult entityPaginatedResult) {
    
        this.entityPaginatedResult = entityPaginatedResult;
    }
    
}
