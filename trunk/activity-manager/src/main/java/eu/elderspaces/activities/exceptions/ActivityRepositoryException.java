package eu.elderspaces.activities.exceptions;

public class ActivityRepositoryException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public ActivityRepositoryException() {
    
        super();
    }
    
    public ActivityRepositoryException(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public ActivityRepositoryException(final String message) {
    
        super(message);
    }
    
    public ActivityRepositoryException(final Throwable cause) {
    
        super(cause);
    }
    
}
