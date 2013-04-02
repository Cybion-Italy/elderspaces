package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.recommendations.model.EventEntry;
import eu.elderspaces.recommendations.model.PaginatedResult;

public class EventRecommendationResponse extends ServiceResponse<PaginatedResult<EventEntry>> {
    
    private PaginatedResult<EventEntry> eventPaginatedResult;
    
    public EventRecommendationResponse() {
        
    }
    
    public EventRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<EventEntry> eventPaginatedResult) {
        
        super(status, message);
        this.eventPaginatedResult = eventPaginatedResult;
    }
    
    @Override
    public PaginatedResult<EventEntry> getObject() {
        
        return this.eventPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<EventEntry> eventPaginatedResult) {
        
        this.eventPaginatedResult = eventPaginatedResult;
    }
    
}
