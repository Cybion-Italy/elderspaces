package eu.elderspaces.activities.core.exceptions;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ActivityManagerException extends Exception {

    public ActivityManagerException(String emsg, Exception e) {
        super(emsg, e);
    }

    public ActivityManagerException(String emsg) {
        super(emsg);
    }

}
