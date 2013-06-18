package eu.elderspaces.persistence.exceptions;

public class NonValidUserActivity extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public NonValidUserActivity() {
    
        super();
    }
    
    public NonValidUserActivity(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public NonValidUserActivity(final String message) {
    
        super(message);
    }
    
    public NonValidUserActivity(final Throwable cause) {
    
        super(cause);
    }
    
}
