package eu.elderspaces.model;

import java.util.Date;

public class ActivityStream {
    
    private String verb;
    private Entity object;
    private Entity target;
    private Person actor;
    private Date published;
    
    public ActivityStream() {
    
    }
    
    public ActivityStream(final Person actor, final String verb, final Entity object,
            final Entity target, final Date published) {
    
        this.actor = actor;
        this.verb = verb;
        this.object = object;
        this.target = target;
        this.published = published;
    }
    
    public Person getActor() {
    
        return actor;
    }
    
    public void setActor(final Person actor) {
    
        this.actor = actor;
    }
    
    public String getVerb() {
    
        return verb;
    }
    
    public void setVerb(final String verb) {
    
        this.verb = verb;
    }
    
    public Entity getObject() {
    
        return object;
    }
    
    public void setObject(final Entity object) {
    
        this.object = object;
    }
    
    public Entity getTarget() {
    
        return target;
    }
    
    public void setTarget(final Entity target) {
    
        this.target = target;
    }
    
    public Date getPublished() {
    
        return published;
    }
    
    public void setPublished(final Date published) {
    
        this.published = published;
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((actor == null) ? 0 : actor.hashCode());
        result = (prime * result) + ((object == null) ? 0 : object.hashCode());
        result = (prime * result) + ((published == null) ? 0 : published.hashCode());
        result = (prime * result) + ((target == null) ? 0 : target.hashCode());
        result = (prime * result) + ((verb == null) ? 0 : verb.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActivityStream other = (ActivityStream) obj;
        if (actor == null) {
            if (other.actor != null) {
                return false;
            }
        } else if (!actor.equals(other.actor)) {
            return false;
        }
        if (object == null) {
            if (other.object != null) {
                return false;
            }
        } else if (!object.equals(other.object)) {
            return false;
        }
        if (published == null) {
            if (other.published != null) {
                return false;
            }
        } else if (!published.equals(other.published)) {
            return false;
        }
        if (target == null) {
            if (other.target != null) {
                return false;
            }
        } else if (!target.equals(other.target)) {
            return false;
        }
        if (verb == null) {
            if (other.verb != null) {
                return false;
            }
        } else if (!verb.equals(other.verb)) {
            return false;
        }
        return true;
    }
    
}
