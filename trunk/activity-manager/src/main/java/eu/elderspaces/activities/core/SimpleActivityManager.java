package eu.elderspaces.activities.core;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

import eu.elderspaces.activities.exceptions.ActivityRepositoryException;
import eu.elderspaces.activities.exceptions.InvalidUserActivity;
import eu.elderspaces.activities.persistence.ActivityRepository;
import eu.elderspaces.model.Activity;
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
    public boolean storeActivity(final String activityContent) throws InvalidUserActivity,
            ActivityRepositoryException {
    
        final Activity activity;
        try {
            activity = mapper.readValue(activityContent, Activity.class);
        } catch (final Exception e) {
            throw new InvalidUserActivity(e);
        }
        
        final boolean activityStored = storeActivity(activity);
        
        final Person user = activity.getActor();
        final String verb = activity.getVerb();
        final Entity object = activity.getObject();
        final Entity target = activity.getTarget();
        
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
            throw new InvalidUserActivity("Invalid Object type");
        }
        
        return profileUpdated && activityStored;
    }
    
    private boolean handlePersonObject(final Person user, final String verb,
            final Person personObject) throws InvalidUserActivity {
    
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
            throw new InvalidUserActivity("Invalid verb");
        }
        
        return personObjectHandled;
    }
    
    private boolean handlePostObject(final Person user, final String verb, final Post postObject,
            final Entity target) throws InvalidUserActivity {
    
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
                
                throw new InvalidUserActivity("Invalid Target type");
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
                
                throw new InvalidUserActivity("Invalid Target type");
            }
            
        } else {
            throw new InvalidUserActivity("Invalid verb");
        }
        
        return postObjectHandled;
    }
    
    private boolean handleEventObject(final Person user, final String verb, final Event eventObject)
            throws InvalidUserActivity {
    
        boolean eventObjectHandled = false;
        
        if (verb.equals(Verbs.CREATE)) {
            
            eventObjectHandled = activityRepository.createEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.UPDATE)) {
            
            eventObjectHandled = activityRepository.modifyEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.DELETE)) {
            
            eventObjectHandled = activityRepository.deleteEvent(user, eventObject);
            
        } else if (verb.equals(Verbs.YES_RSVP_RESPONSE_TO_EVENT)
                || verb.equals(Verbs.NO_RSVP_RESPONSE_TO_EVENT)
                || verb.equals(Verbs.MAYBE_RSVP_RESPONSE_TO_EVENT)) {
            
            eventObjectHandled = activityRepository.createRSVPResponseToEvent(user, verb,
                    eventObject);
            
        } else {
            throw new InvalidUserActivity("Invalid verb");
        }
        
        return eventObjectHandled;
    }
    
    private boolean handleClubObject(final Person user, final String verb, final Club clubObject)
            throws InvalidUserActivity {
    
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
            throw new InvalidUserActivity("Invalid verb");
        }
        
        return clubObjectHandled;
    }
    
    @Override
    public boolean storeActivity(final Activity activity) throws InvalidUserActivity,
            ActivityRepositoryException {
    
        final String userId = activity.getActor().getId();
        return activityRepository.store(activity, userId);
    }
}
