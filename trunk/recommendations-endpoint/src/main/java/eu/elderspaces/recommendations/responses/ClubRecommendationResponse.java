package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;

public class ClubRecommendationResponse extends ServiceResponse<PaginatedResult<ClubEntry>> {
    
    private PaginatedResult<ClubEntry> clubRecommendationReport;
    
    public ClubRecommendationResponse() {
    
    }
    
    public ClubRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<ClubEntry> clubRecommendationReport) {
    
        super(status, message);
        this.clubRecommendationReport = clubRecommendationReport;
    }
    
    @Override
    public PaginatedResult<ClubEntry> getObject() {
    
        return this.clubRecommendationReport;
    }
    
    @Override
    public void setObject(final PaginatedResult<ClubEntry> clubRecommendationReport) {
    
        this.clubRecommendationReport = clubRecommendationReport;
    }
    
}
