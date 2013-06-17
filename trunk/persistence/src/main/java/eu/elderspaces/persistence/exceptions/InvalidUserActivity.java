package eu.elderspaces.persistence.exceptions;

public class InvalidUserActivity extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public InvalidUserActivity() {
    
        super();
    }
    
    public InvalidUserActivity(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public InvalidUserActivity(final String message) {
    
        super(message);
    }
    
    public InvalidUserActivity(final Throwable cause) {
    
        super(cause);
    }
    
}
