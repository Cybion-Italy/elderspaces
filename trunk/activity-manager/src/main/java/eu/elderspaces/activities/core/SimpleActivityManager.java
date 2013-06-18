package eu.elderspaces.activities.core;

import eu.elderspaces.activities.core.exceptions.ActivityManagerException;
import eu.elderspaces.persistence.ActivityStreamRepository;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;
import eu.elderspaces.persistence.exceptions.ActivityStreamRepositoryException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.ActivityStream;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.Verbs;

public class SimpleActivityManager implements ActivityManager {

    private final ObjectMapper mapper;

    private final ActivityStreamRepository activityRepository;

    private final EntitiesRepository entitiesRepository;

    private final SocialNetworkRepository socialNetworkRepository;

    @Inject
    public SimpleActivityManager(final ActivityStreamRepository activityRepository,
                                 final EntitiesRepository entitiesRepository,
                                 final SocialNetworkRepository socialNetworkRepository,
                                 final ObjectMapper objectMapper) {

        this.activityRepository = activityRepository;
        this.entitiesRepository = entitiesRepository;
        this.socialNetworkRepository = socialNetworkRepository;
        this.mapper = objectMapper;
    }

    @Override
    public boolean storeActivity(final String activityContent) throws ActivityManagerException {

        final ActivityStream activity;
        try {
            activity = mapper.readValue(activityContent, ActivityStream.class);
        } catch (final Exception e) {
            throw new ActivityManagerException(
                    "can't deserialise activity: '" + activityContent + "' to json", e);
        }

        return storeActivity(activity);
    }

    @Override
    public boolean storeActivity(final ActivityStream activity) throws ActivityManagerException {

        final String userId = activity.getActor().getId();
        String activityId = "";

        try {
            activityId = activityRepository.store(activity);
        } catch (ActivityStreamRepositoryException e) {
            throw new ActivityManagerException("can't store activity '" + activity + "'", e);
        }
        boolean activityStored = !activityId.equals("");

        final Person user = activity.getActor();
        final String verb = activity.getVerb();
        final Entity target = activity.getTarget();

        final Entity object = activity.getObject();

        boolean profileUpdated = false;

        if (object.getClass() == Person.class) {

            final Person personObject = (Person) object;
            profileUpdated = handlePersonObject(user, verb, personObject);

        } else if (object.getClass() == Activity.class) {

            final Activity postObject = (Activity) object;
            profileUpdated = handlePostObject(user, verb, postObject, target);

        } else if (object.getClass() == Event.class) {

            final Event eventObject = (Event) object;
            profileUpdated = handleEventObject(user, verb, eventObject);

        } else if (object.getClass() == Club.class) {

            final Club clubObject = (Club) object;
            profileUpdated = handleClubObject(user, verb, clubObject);

        } else {
            throw new ActivityManagerException(
                    "Not managed class type: '" + object.getClass().getName() + "'");
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

    private boolean handlePostObject(final Person user, final String verb,
            final Activity postObject, final Entity target) throws InvalidUserActivity {

        boolean postObjectHandled = false;

        if (verb.equals(Verbs.CREATE)) {

            if (target == null) {

                // postObject.setPostedOn(user);

            } else if ((target.getClass() == Event.class) || (target.getClass() == Club.class)) {

                // postObject.setPostedOn(target);

            } else {

                throw new InvalidUserActivity("Invalid Target type");
            }

            postObjectHandled = activityRepository.addPost(user, postObject);

        } else if (verb.equals(Verbs.DELETE)) {

            if (target == null) {

                // postObject.setPostedOn(user);

            } else if ((target.getClass() == Event.class) || (target.getClass() == Club.class)) {

                // postObject.setPostedOn(target);

            } else {

                throw new InvalidUserActivity("Invalid Target type");
            }

            postObjectHandled = activityRepository.deletePost(user, postObject);

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
}
