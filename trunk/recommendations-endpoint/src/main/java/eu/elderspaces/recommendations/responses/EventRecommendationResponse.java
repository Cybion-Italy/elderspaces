package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.recommendations.PaginatedResult;

public class EventRecommendationResponse extends ServiceResponse<PaginatedResult<Event>> {
    
    private PaginatedResult<Event> eventPaginatedResult;
    
    public EventRecommendationResponse() {
    
    }
    
    public EventRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<Event> eventPaginatedResult) {
    
        super(status, message);
        this.eventPaginatedResult = eventPaginatedResult;
    }
    
    @Override
    public PaginatedResult<Event> getObject() {
    
        return this.eventPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<Event> eventPaginatedResult) {
    
        this.eventPaginatedResult = eventPaginatedResult;
    }
    
}
