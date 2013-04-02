package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;

public class FriendRecommendationResponse extends
        ServiceResponse<PaginatedResult<FriendEntry>> {
    
    private PaginatedResult<FriendEntry> friendRecommendationReport;
    
    public FriendRecommendationResponse() {
    
    }
    
    public FriendRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<FriendEntry> friendRecommendationReport) {
    
        super(status, message);
        this.friendRecommendationReport = friendRecommendationReport;
    }
    
    @Override
    public PaginatedResult<FriendEntry> getObject() {
    
        return this.friendRecommendationReport;
    }
    
    @Override
    public void setObject(final PaginatedResult<FriendEntry> friendRecommendationReport) {
    
        this.friendRecommendationReport = friendRecommendationReport;
    }
    
}
