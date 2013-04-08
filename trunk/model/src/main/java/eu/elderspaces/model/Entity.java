package eu.elderspaces.model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({ @Type(value = Post.class, name = "activity"),
        @Type(value = Person.class, name = "person"), @Type(value = Event.class, name = "event"),
        @Type(value = Club.class, name = "club") })
public class Entity implements Comparable<Entity> {
    
    private String id;
    
    public Entity() {
    
    }
    
    public Entity(final String id) {
    
        this.id = id;
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
        
        final Entity that = (Entity) o;
        
        return Objects.equal(id, that.id);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(id);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(id).toString();
    }
    
    @Override
    public int compareTo(final Entity otherEntity) {
    
        return this.getId().compareTo(otherEntity.getId());
    }
}
