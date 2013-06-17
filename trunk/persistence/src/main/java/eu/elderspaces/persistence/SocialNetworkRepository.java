package eu.elderspaces.persistence;

import java.util.Date;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Event.InvitationAnswer;
import eu.elderspaces.model.Person;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface SocialNetworkRepository {
    
    // void sendFriendRequest(Person actor, Person object, Date eventTime);
    
    void addNewFriend(Person actor, Person object, Date eventTime);

    void modifyProfileData(Person actor, Date eventTime);

    void deleteFriendConnection(Person actor, Person object, Date eventTime);

    void deleteUser(Person actor, Date eventTime);
    
    void postActivity(Person actor, Activity object, Date eventTime);
    
    // void deleteActivity(Person actor, Activity object, Date eventTime);
    
    void createClub(Person actor, Club object, Date eventTime);
    
    void modifyClub(Person actor, Club object, Date eventTime);
    
    void deleteClub(Person actor, Club object, Date eventTime);
    
    void joinClub(Person actor, Club object, Date eventTime);
    
    // void leaveClub(Person actor, Club object, Date eventTime);
    
    void postClubActivity(Person actor, Activity object, Club target, Date eventTime);
    
    // void deleteClubActivity(Person actor, Activity object, Club target, Date
    // eventTime);
    
    void createEvent(Person actor, Event object, Date eventTime);
    
    void modifyEvent(Person actor, Event object, Date eventTime);
    
    void deleteEvent(Person actor, Event object, Date eventTime);
    
    void respondEvent(Person actor, Event object, InvitationAnswer answer, Date eventTime);
    
    void postEventActivity(Person actor, Activity object, Event target, Date eventTime);
    
    // void deleteEventActivity(Person actor, Activity object, Event target,
    // Date eventTime);
    
}
