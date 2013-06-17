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
    
}
