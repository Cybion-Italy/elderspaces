package eu.elderspaces.persistence.exceptions;

public class ActivityStreamRepositoryException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public ActivityStreamRepositoryException() {
    
        super();
    }
    
    public ActivityStreamRepositoryException(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public ActivityStreamRepositoryException(final String message) {
    
        super(message);
    }
    
    public ActivityStreamRepositoryException(final Throwable cause) {
    
        super(cause);
    }
    
}
