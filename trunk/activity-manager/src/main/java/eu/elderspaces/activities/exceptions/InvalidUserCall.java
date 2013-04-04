package eu.elderspaces.activities.exceptions;

public class InvalidUserCall extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public InvalidUserCall() {
    
        super();
    }
    
    public InvalidUserCall(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public InvalidUserCall(final String message) {
    
        super(message);
    }
    
    public InvalidUserCall(final Throwable cause) {
    
        super(cause);
    }
    
}
