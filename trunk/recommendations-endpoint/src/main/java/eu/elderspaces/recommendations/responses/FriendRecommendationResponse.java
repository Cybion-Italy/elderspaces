package eu.elderspaces.recommendations.responses;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.ServiceResponse;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;

public class FriendRecommendationResponse extends
ServiceResponse<PaginatedResult<Person>> {
    
    private PaginatedResult<Person> friendPaginatedResult;
    
    public FriendRecommendationResponse() {
        
    }
    
    public FriendRecommendationResponse(final ResponseStatus status, final String message,
            final PaginatedResult<Person> friendPaginatedResult) {
        
        super(status, message);
        this.friendPaginatedResult = friendPaginatedResult;
    }
    
    @Override
    public PaginatedResult<Person> getObject() {
        
        return this.friendPaginatedResult;
    }
    
    @Override
    public void setObject(final PaginatedResult<Person> friendPaginatedResult) {
        
        this.friendPaginatedResult = friendPaginatedResult;
    }
    
}
