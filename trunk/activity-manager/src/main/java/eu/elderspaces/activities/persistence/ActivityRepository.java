package eu.elderspaces.activities.persistence;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

public interface ActivityRepository {
    
    public void shutDownRepository();
    
    public boolean store(Call call, String userId) throws ActivityRepositoryException;
    
    public boolean store(String callString, String userId) throws ActivityRepositoryException;
    
    public boolean addFriend(Person user, Person personObject);
    
    public boolean removeFriend(Person user, Person personObject);
    
    public boolean updateUser(Person user, Person personObject);
    
    public boolean deleteUser(Person user);
    
    public boolean addPost(Person user, Post postObject);
    
    public boolean addPost(Person user, Post postObject, Event target);
    
    public boolean addPost(Person user, Post postObject, Club target);
    
    public boolean deletePost(Person user, Post postObject);
    
    public boolean deletePost(Person user, Post postObject, Event targetEvent);
    
    public boolean deletePost(Person user, Post postObject, Club targetClub);
    
    public boolean createEvent(Person user, Event eventObject);
    
    public boolean modifyEvent(Person user, Event eventObject);
    
    public boolean deleteEvent(Person user, Event eventObject);
    
    public boolean createRSVPResponseToEvent(Person user, Event eventObject);
    
    public boolean createClub(Person user, Club clubObject);
    
    public boolean modifyClub(Person user, Club clubObject);
    
    public boolean deleteClub(Person user, Club clubObject);
    
    public boolean joinClub(Person user, Club clubObject);
    
    public boolean leaveClub(Person user, Club clubObject);
    
}
