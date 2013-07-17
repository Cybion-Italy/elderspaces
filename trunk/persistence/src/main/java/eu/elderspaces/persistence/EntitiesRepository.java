package eu.elderspaces.persistence;

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
    
    // void deleteUser(Person actor, Date eventTime);
    
    void postActivity(Activity object, Date eventTime);
    
    // void deleteActivity(Activity object, Date eventTime);
    
    void createClub(Club object, Date eventTime);
    
    void modifyClub(Club object, Date eventTime);
    
    // void deleteClub(Club object, Date eventTime);
    
    void createEvent(Event object, Date eventTime);
    
    void modifyEvent(Event object, Date eventTime);
    
    // **********************************************************
    // getters
    Person getPerson(String id);
    
    Club getClub(String id);
    
    Event getEvent(String id);
    
    Activity getActivity(String id);
    
    // **********************************************************
    // queries
    
}
