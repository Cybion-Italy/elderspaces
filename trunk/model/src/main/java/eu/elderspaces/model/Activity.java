package eu.elderspaces.model;

public class Activity {
    
    private String verb;
    private ActivityObject object;
    private Person actor;
    private String published;
    
    public Activity() {
    
    }
    
    public Activity(final String verb, final ActivityObject activityObject, final Person actor,
            final String published) {
    
        this.verb = verb;
        this.object = activityObject;
        this.actor = actor;
        this.published = published;
    }
    
    public String getVerb() {
    
        return verb;
    }
    
    public void setVerb(final String verb) {
    
        this.verb = verb;
    }
    
    public ActivityObject getObject() {
    
        return object;
    }
    
    public void setObject(final ActivityObject object) {
    
        this.object = object;
    }
    
    public Person getActor() {
    
        return actor;
    }
    
    public void setActor(final Person actor) {
    
        this.actor = actor;
    }
    
    public String getPublished() {
    
        return published;
    }
    
    public void setPublished(final String published) {
    
        this.published = published;
    }
}
