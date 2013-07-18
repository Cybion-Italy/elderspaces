package eu.elderspaces.model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({ @Type(value = Activity.class, name = "activity"),
        @Type(value = Person.class, name = "person"), @Type(value = Event.class, name = "event"),
        @Type(value = Club.class, name = "club") })
public class Entity implements Comparable<Entity> {
    
    String id;
    
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
    public int compareTo(final Entity otherEntity) {
    
        return this.getId().compareTo(otherEntity.getId());
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
        final Entity other = (Entity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
