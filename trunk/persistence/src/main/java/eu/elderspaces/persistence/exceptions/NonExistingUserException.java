package eu.elderspaces.persistence.exceptions;

public class NonExistingUserException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public NonExistingUserException() {
    
        super();
    }
    
    public NonExistingUserException(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public NonExistingUserException(final String message) {
    
        super(message);
    }
    
    public NonExistingUserException(final Throwable cause) {
    
        super(cause);
    }
    
}
