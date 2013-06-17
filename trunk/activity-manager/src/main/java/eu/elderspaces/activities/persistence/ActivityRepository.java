package eu.elderspaces.activities.persistence;

import java.util.Set;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Activity;

public interface ActivityRepository {
    
    public void shutDownRepository();
    
    public boolean store(ActivityStream call, String userId) throws ActivityRepositoryException;
    
    public boolean store(String callString, String userId) throws ActivityRepositoryException;
    
    public boolean addFriend(Person user, Person personObject);
    
    public boolean removeFriend(Person user, Person personObject);
    
    public boolean updateUser(Person user, Person personObject);
    
    public boolean deleteUser(Person user);
    
    public boolean addPost(Person user, Activity postObject);
    
    public boolean deletePost(Person user, Activity postObject);
    
    public boolean createEvent(Person user, Event eventObject);
    
    public boolean modifyEvent(Person user, Event eventObject);
    
    public boolean deleteEvent(Person user, Event eventObject);
    
    public boolean createRSVPResponseToEvent(Person user, String verb, Event eventObject);
    
    public boolean createClub(Person user, Club clubObject);
    
    public boolean modifyClub(Person user, Club clubObject);
    
    public boolean deleteClub(Person user, Club clubObject);
    
    public boolean joinClub(Person user, Club clubObject);
    
    public boolean leaveClub(Person user, Club clubObject);
    
    public void addUser(Person user);
    
    public boolean userExists(String userId);
    
    public Set<Person> getFriends(String userId);
    
    public Set<Event> getEvents(String userId);
    
    public Set<Club> getClubs(String userId);
    
}
