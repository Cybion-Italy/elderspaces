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
}
