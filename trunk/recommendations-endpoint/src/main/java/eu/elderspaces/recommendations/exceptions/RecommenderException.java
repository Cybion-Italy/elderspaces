package eu.elderspaces.recommendations.exceptions;

public class RecommenderException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public RecommenderException(final Exception e) {
    
        super(e);
    }
    
    public RecommenderException(final String message) {
    
        super(message);
    }
    
    public RecommenderException(final String message, final Exception e) {
    
        super(message, e);
    }
    
}
