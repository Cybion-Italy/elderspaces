package eu.elderspaces.recommendations.responses;

import com.google.common.base.Objects;

public class RecommendationEntry {
    
    private String id;
    
    public RecommendationEntry() {
    
    }
    
    public RecommendationEntry(final String id) {
    
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
        
        final RecommendationEntry that = (RecommendationEntry) o;
        
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
