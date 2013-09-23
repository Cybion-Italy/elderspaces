package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface EntitiesRepository {
    
    public void storeIfNewEntity(Entity entity, Date eventTime);
    
    public void updateProfile(Person actor, Date eventTime);
    
    public void deleteUser(Person actor, Date eventTime);
    
    public void postActivity(Activity object, Date eventTime);
    
    public void deleteActivity(Activity object, Date eventTime);
    
    public void createClub(Club object, Date eventTime);
    
    public void modifyClub(Club object, Date eventTime);
    
    public void deleteClub(Club object, Date eventTime);
    
    public void createEvent(Event object, Date eventTime);
    
    public void modifyEvent(Event object, Date eventTime);
    
    public void deleteEvent(Event event, Date eventTime);
    
    // **********************************************************
    // getters
    public Person getPerson(String id) throws RepositoryException;
    
    public Club getClub(String id) throws RepositoryException;
    
    public Event getEvent(String id) throws RepositoryException;
    
    public Activity getActivity(String id) throws RepositoryException;
    
    public Entity get(final String documentId) throws RepositoryException;
    
    public List<String> getAllKeys() throws RepositoryException;
    
    public void shutdown() throws RepositoryException;
}
