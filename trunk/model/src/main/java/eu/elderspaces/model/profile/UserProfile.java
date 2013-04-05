package eu.elderspaces.model.profile;

import java.util.Set;

import com.google.common.base.Objects;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;

public class UserProfile {
    
    private Person user;
    private Set<Person> friends;
    private Set<Event> events;
    private Set<Club> clubs;
    
    public UserProfile() {
    
    }
    
    public UserProfile(final Person user, final Set<Person> friends, final Set<Event> events,
            final Set<Club> clubs) {
    
        this.user = user;
        this.friends = friends;
        this.events = events;
        this.clubs = clubs;
    }
    
    public Person getUser() {
    
        return user;
    }
    
    public void setUser(final Person user) {
    
        this.user = user;
    }
    
    public Set<Person> getFriends() {
    
        return friends;
    }
    
    public void setFriends(final Set<Person> friends) {
    
        this.friends = friends;
    }
    
    public Set<Event> getEvents() {
    
        return events;
    }
    
    public void setEvents(final Set<Event> events) {
    
        this.events = events;
    }
    
    public Set<Club> getClubs() {
    
        return clubs;
    }
    
    public void setClubs(final Set<Club> clubs) {
    
        this.clubs = clubs;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final UserProfile that = (UserProfile) o;
        
        return Objects.equal(user, that.user) && Objects.equal(friends, that.friends)
                && Objects.equal(events, that.events) && Objects.equal(clubs, that.clubs);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(user, friends, events, clubs);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(user).addValue(friends).addValue(events)
                .addValue(clubs).toString();
    }
}
