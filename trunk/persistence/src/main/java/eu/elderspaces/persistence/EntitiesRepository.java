package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.Date;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface EntitiesRepository {
    
    void updateProfile(Person actor, Date eventTime);
    
    void deleteUser(Person actor, Date eventTime);
    
    void postActivity(Activity object, Date eventTime);
    
    void deleteActivity(Activity object, Date eventTime);
    
    void createClub(Club object, Date eventTime);
    
    void modifyClub(Club object, Date eventTime);
    
    void deleteClub(Club object, Date eventTime);
    
    void createEvent(Event object, Date eventTime);
    
    void modifyEvent(Event object, Date eventTime);
    
    // **********************************************************
    // getters
    Person getPerson(String id) throws RepositoryException;
    
    Club getClub(String id) throws RepositoryException;
    
    Event getEvent(String id) throws RepositoryException;
    
    Activity getActivity(String id) throws RepositoryException;
    
    // **********************************************************
    // queries
    
}
