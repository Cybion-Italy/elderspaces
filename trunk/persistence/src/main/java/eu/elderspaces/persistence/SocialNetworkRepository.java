package eu.elderspaces.persistence;

import java.util.Date;
import java.util.Set;

import eu.elderspaces.model.Event.InvitationAnswer;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface SocialNetworkRepository {
    
    public void shutdown();
    
    // *******************************************************************************************
    // activity stream updates
    
    // void sendFriendRequest(String actorId, String objectId, Date eventTime);
    
    void addNewFriend(String actorId, String objectId, Date eventTime);
    
    void modifyProfileData(String actorId, Date eventTime);
    
    void deleteFriendConnection(String actorId, String objectId, Date eventTime);
    
    void deleteUser(String actorId, Date eventTime);
    
    void postActivity(String actorId, String objectId, Date eventTime);
    
    void deleteActivity(String actorId, String objectId, Date eventTime);
    
    void createClub(String actorId, String objectId, Date eventTime);
    
    // void modifyClub(String actorId, String objectId, Date eventTime);
    
    void deleteClub(String actorId, String objectId, Date eventTime);
    
    void joinClub(String actorId, String objectId, Date eventTime);
    
    void leaveClub(String actorId, String objectId, Date eventTime);
    
    void postClubActivity(String actorId, String objectId, String targetId, Date eventTime);
    
    void deleteClubActivity(String actorId, String objectId, String target, Date eventTime);
    
    void createEvent(String actorId, String objectId, Date eventTime);
    
    // void modifyEvent(String actorId, String objectId, Date eventTime);
    
    void deleteEvent(String actorId, String objectId, Date eventTime);
    
    void respondEvent(String actorId, String objectId, InvitationAnswer answer, Date eventTime);
    
    void postEventActivity(String actorId, String objectId, String targetId, Date eventTime);
    
    void deleteEventActivity(String actorId, String objectId, String targetId, Date eventTime);
    
    // *******************************************************************************************
    // query methods
    
    Set<String> getFriends(String id);
    
    Set<String> getClubs(String id);
    
    Set<String> getEvents(String id);
    
    Set<String> getActivities(String id);
    
    Set<String> getFriendsOfFriends(String id);
}
