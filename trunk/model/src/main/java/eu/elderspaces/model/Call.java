package eu.elderspaces.model;

public class Call {
    
    private String verb;
    private Entity object;
    private Person actor;
    private String published;
    
    public Call() {
    
    }
    
    public Call(final String verb, final Entity callObject, final Person actor,
            final String published) {
    
        this.verb = verb;
        this.object = callObject;
        this.actor = actor;
        this.published = published;
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
