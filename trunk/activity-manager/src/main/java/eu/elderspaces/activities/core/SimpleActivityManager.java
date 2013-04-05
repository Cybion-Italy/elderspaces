package eu.elderspaces.activities.core;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserCall;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.model.Call;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;
import eu.elderspaces.model.Verbs;

public class SimpleActivityManager implements ActivityManager {
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final ActivityRepository activityRepository;
    
    @Inject
    public SimpleActivityManager(final ActivityRepository activityRepository) {
    
        this.activityRepository = activityRepository;
    }
    
    @Override
    public boolean storeCall(final String callContent) throws InvalidUserCall,
            ActivityRepositoryException {
    
        final Call call;
        try {
            call = mapper.readValue(callContent, Call.class);
        } catch (final Exception e) {
            throw new InvalidUserCall(e);
        }
        
        final boolean callStored = storeCall(call);
        
        final Person user = call.getActor();
        final String verb = call.getVerb();
        final Entity object = call.getObject();
        final Entity target = call.getTarget();
        
        boolean profileUpdated = false;
        
        if (object.getClass() == Person.class) {
            
            final Person personObject = (Person) object;
            profileUpdated = handlePersonObject(user, verb, personObject);
            
        } else if (object.getClass() == Post.class) {
            
            final Post postObject = (Post) object;
            profileUpdated = handlePostObject(user, verb, postObject, target);
            
        } else if (object.getClass() == Event.class) {
            
            final Event eventObject = (Event) object;
            profileUpdated = handleEventObject(user, verb, eventObject);
            
        } else if (object.getClass() == Club.class) {
            
            final Club clubObject = (Club) object;
            profileUpdated = handleClubObject(user, verb, clubObject);
            
        } else {
            throw new InvalidUserCall("Invalid Object type");
        }
        
        return profileUpdated && callStored;
    }
    
    private boolean handlePersonObject(final Person user, final String verb,
            final Person personObject) throws InvalidUserCall {
    
        boolean personObjectHandled = false;
        
        if (verb.equals(Verbs.REQUEST_FRIEND)) {
            
            // Do nothing
            
        } else if (verb.equals(Verbs.MAKE_FRIEND)) {
            
            personObjectHandled = activityRepository.addFriend(user, personObject);
            
        } else if (verb.equals(Verbs.REMOVE_FRIEND)) {
            
            personObjectHandled = activityRepository.removeFriend(user, personObject);
            
        } else if (verb.equals(Verbs.UPDATE)) {
            
            personObjectHandled = activityRepository.updateUser(user, personObject);
            
        } else if (verb.equals(Verbs.DELETE)) {
            
            personObjectHandled = activityRepository.deleteUser(user);
            
        } else {
            throw new InvalidUserCall("Invalid verb");
        }
        
        return personObjectHandled;
    }
    
    private boolean handlePostObject(final Person user, final String verb, final Post postObject,
            final Entity target) throws InvalidUserCall {
    
        boolean postObjectHandled = false;
        
        if (verb.equals(Verbs.CREATE)) {
            
            if (target == null) {
                
                postObjectHandled = activityRepository.addPost(user, postObject);
                
            } else if (target.getClass() == Event.class) {
                
                final Event targetEvent = (Event) target;
                postObjectHandled = activityRepository.addPost(user, postObject, targetEvent);
                
            } else if (target.getClass() == Club.class) {
                
                final Club targetClub = (Club) target;
                postObjectHandled = activityRepository.addPost(user, postObject, targetClub);
                
            } else {
                
                throw new InvalidUserCall("Invalid Target type");
            }
            
        } else if (verb.equals(Verbs.DELETE)) {
            
            if (target == null) {
                
                postObjectHandled = activityRepository.deletePost(user, postObject);
                
            } else if (target.getClass() == Event.class) {
                
                final Event targetEvent = (Event) target;
                postObjectHandled = activityRepository.deletePost(user, postObject, targetEvent);
                
            } else if (target.getClass() == Club.class) {
                
                final Club targetClub = (Club) target;
                postObjectHandled = activityRepository.deletePost(user, postObject, targetClub);
                
            } else {
                
                throw new InvalidUserCall("Invalid Target type");
            }
            
        } else {
            throw new InvalidUserCall("Invalid verb");
        }
        
        return postObjectHandled;
    }
    
    private boolean handleEventObject(final Person user, final String verb, final Event eventObject)
            throws InvalidUserCall {
    
        boolean eventObjectHandled = false;
        
        if (verb.equals(Verbs.CREATE)) {
            
            eventObjectHandled = activityRepository.createEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.UPDATE)) {
            
            eventObjectHandled = activityRepository.modifyEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.DELETE)) {
            
            eventObjectHandled = activityRepository.deleteEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.RSVP_RESPONSE_TO_EVENT)) {
            
            eventObjectHandled = activityRepository.createRSVPResponseToEvent(user, eventObject);
            
        } else {
            throw new InvalidUserCall("Invalid verb");
        }
        
        return eventObjectHandled;
    }
    
    private boolean handleClubObject(final Person user, final String verb, final Club clubObject)
            throws InvalidUserCall {
    
        boolean clubObjectHandled = false;
        
        if (verb.equals(Verbs.CREATE)) {
            
            clubObjectHandled = activityRepository.createClub(user, clubObject);
            
        } else if (verb.equals(Verbs.UPDATE)) {
            
            clubObjectHandled = activityRepository.modifyClub(user, clubObject);
            
        } else if (verb.equals(Verbs.DELETE)) {
            
            clubObjectHandled = activityRepository.deleteClub(user, clubObject);
            
        } else if (verb.equals(Verbs.JOIN)) {
            
            clubObjectHandled = activityRepository.joinClub(user, clubObject);
            
        } else if (verb.equals(Verbs.LEAVE)) {
            
            clubObjectHandled = activityRepository.leaveClub(user, clubObject);
            
        } else {
            throw new InvalidUserCall("Invalid verb");
        }
        
        return clubObjectHandled;
    }
    
    @Override
    public boolean storeCall(final Call call) throws InvalidUserCall, ActivityRepositoryException {
    
        final String userId = call.getActor().getId();
        return activityRepository.store(call, userId);
    }
}
