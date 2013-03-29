package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;

public class RecommendationResponse extends ServiceResponse<RecommendationReport> {
    
    private RecommendationReport recommendationReport;
    
    public RecommendationResponse() {
    
    }
    
    public RecommendationResponse(final ResponseStatus status, final String message,
            final RecommendationReport recommendationReport) {
    
        super(status, message);
        this.recommendationReport = recommendationReport;
    }
    
    @Override
    public RecommendationReport getObject() {
    
        return this.recommendationReport;
    }
    
    @Override
    public void setObject(final RecommendationReport recommendationReport) {
    
        this.recommendationReport = recommendationReport;
    }
    
}
