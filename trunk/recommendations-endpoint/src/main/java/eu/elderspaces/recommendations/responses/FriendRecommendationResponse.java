package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.recommendations.model.FriendEntry;
import eu.elderspaces.recommendations.model.PaginatedResult;

public class FriendRecommendationResponse extends
ServiceResponse<PaginatedResult<FriendEntry>> {
    
    private PaginatedResult<FriendEntry> friendPaginatedResult;
    
    public FriendRecommendationResponse() {
        
    }
    
    public FriendRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<FriendEntry> friendPaginatedResult) {
        
        super(status, message);
        this.friendPaginatedResult = friendPaginatedResult;
    }
    
    @Override
    public PaginatedResult<FriendEntry> getObject() {
        
        return this.friendPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<FriendEntry> friendPaginatedResult) {
        
        this.friendPaginatedResult = friendPaginatedResult;
    }
    
}
