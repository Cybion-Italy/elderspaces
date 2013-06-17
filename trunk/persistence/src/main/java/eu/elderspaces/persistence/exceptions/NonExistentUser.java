package eu.elderspaces.persistence.exceptions;

public class NonExistentUser extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public NonExistentUser() {
    
        super();
    }
    
    public NonExistentUser(final String message, final Throwable cause) {
    
        super(message, cause);
    }
    
    public NonExistentUser(final String message) {
    
        super(message);
    }
    
    public NonExistentUser(final Throwable cause) {
    
        super(cause);
    }
    
}
