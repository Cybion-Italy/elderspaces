package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;

public class EventRecommendationResponse extends ServiceResponse<PaginatedResult<EventEntry>> {
    
    private PaginatedResult<EventEntry> eventRecommendationReport;
    
    public EventRecommendationResponse() {
    
    }
    
    public EventRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<EventEntry> eventRecommendationReport) {
    
        super(status, message);
        this.eventRecommendationReport = eventRecommendationReport;
    }
    
    @Override
    public PaginatedResult<EventEntry> getObject() {
    
        return this.eventRecommendationReport;
    }
    
    @Override
    public void setObject(final PaginatedResult<EventEntry> eventRecommendationReport) {
    
        this.eventRecommendationReport = eventRecommendationReport;
    }
    
}
