package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.recommendations.model.ClubEntry;
import eu.elderspaces.recommendations.model.PaginatedResult;

public class ClubRecommendationResponse extends ServiceResponse<PaginatedResult<ClubEntry>> {
    
    private PaginatedResult<ClubEntry> clubPaginatedResult;
    
    public ClubRecommendationResponse() {
    
    }
    
    public ClubRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<ClubEntry> clubPaginatedResult) {
    
        super(status, message);
        this.clubPaginatedResult = clubPaginatedResult;
    }
    
    @Override
    public PaginatedResult<ClubEntry> getObject() {
    
        return this.clubPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<ClubEntry> clubPaginatedResult) {
    
        this.clubPaginatedResult = clubPaginatedResult;
    }
    
}
