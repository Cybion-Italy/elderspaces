package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Activity {
    
    private String id;
    private String verb;
    private Entity object;
    private Entity target;
    private Person actor;
    private String published;
    
    public Activity() {
    
    }
    
    public Activity(final Person actor, final String verb, final Entity object,
            final Entity target, final String published) {
    
        this.id = actor.getId() + "_" + verb + "_" + object.getId();
        this.actor = actor;
        this.verb = verb;
        this.object = object;
        this.setTarget(target);
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
    
    public String getPublished() {
    
        return published;
    }
    
    public void setPublished(final String published) {
    
        this.published = published;
    }
    
    public String getId() {
    
        return id;
    }
    
    public void setId(final String id) {
    
        this.id = id;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Activity that = (Activity) o;
        
        return Objects.equal(id, that.id) && Objects.equal(verb, that.verb)
                && Objects.equal(object, that.object) && Objects.equal(target, that.target)
                && Objects.equal(actor, that.actor) && Objects.equal(published, that.published);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(id, verb, object, target, actor, published);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(id).addValue(verb).addValue(object)
                .addValue(target).addValue(actor).addValue(published).toString();
    }
    
}
