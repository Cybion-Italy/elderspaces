package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Call {
    
    private String id;
    private String verb;
    private Entity object;
    private Person actor;
    private String published;
    
    public Call() {
    
    }
    
    public Call(final String verb, final Entity callObject, final Person actor,
            final String published) {
    
        this.id = actor.getId() + "_" + verb + "_" + callObject.getId();
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
        
        final Call that = (Call) o;
        
        return Objects.equal(id, that.id) && Objects.equal(verb, that.verb)
                && Objects.equal(object, that.object) && Objects.equal(actor, that.actor)
                && Objects.equal(published, that.published);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(id, verb, object, actor, published);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(id).addValue(verb).addValue(object)
                .addValue(actor).addValue(published).toString();
    }
    
}
