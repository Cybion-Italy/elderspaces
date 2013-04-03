package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Entity {
    
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
}
