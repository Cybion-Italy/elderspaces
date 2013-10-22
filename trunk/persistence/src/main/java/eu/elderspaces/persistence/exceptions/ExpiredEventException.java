package eu.elderspaces.persistence.exceptions;

import java.util.Date;

public class ExpiredEventException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public String eventId;
    public Date expireDate;
    
    public ExpiredEventException(String id, Date expireDate) {
    
        super();
        this.eventId = id;
        this.expireDate = expireDate;
    }
    
    public ExpiredEventException(String message, Throwable cause) {
    
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    
    public ExpiredEventException(String message) {
    
        super(message);
        // TODO Auto-generated constructor stub
    }
    
    public ExpiredEventException(Throwable cause) {
    
        super(cause);
        // TODO Auto-generated constructor stub
    }
    
}
