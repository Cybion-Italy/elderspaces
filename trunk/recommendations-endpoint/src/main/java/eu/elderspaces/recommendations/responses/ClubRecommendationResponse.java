package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.recommendations.PaginatedResult;

public class ClubRecommendationResponse extends ServiceResponse<PaginatedResult<Club>> {
    
    private PaginatedResult<Club> clubPaginatedResult;
    
    public ClubRecommendationResponse() {
        
    }
    
    public ClubRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<Club> clubPaginatedResult) {
        
        super(status, message);
        this.clubPaginatedResult = clubPaginatedResult;
    }
    
    @Override
    public PaginatedResult<Club> getObject() {
        
        return this.clubPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<Club> clubPaginatedResult) {
        
        this.clubPaginatedResult = clubPaginatedResult;
    }
    
}
