package eu.elderspaces.recommendations.responses;

import com.google.common.base.Objects;

public class RecommendationEntry {
    
    private String id;
    private String displayName;
    private String thumbnailUrl;
    private String shortDescription;
    private String name;
    private String category;
    
    public String getId() {
    
        return id;
    }
    
    public void setId(final String id) {
    
        this.id = id;
    }
    
    public String getDisplayName() {
    
        return displayName;
    }
    
    public void setDisplayName(final String displayName) {
    
        this.displayName = displayName;
    }
    
    public String getThumbnailUrl() {
    
        return thumbnailUrl;
    }
    
    public void setThumbnailUrl(final String thumbnailUrl) {
    
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public String getShortDescription() {
    
        return shortDescription;
    }
    
    public void setShortDescription(final String shortDescription) {
    
        this.shortDescription = shortDescription;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(final String name) {
    
        this.name = name;
    }
    
    public String getCategory() {
    
        return category;
    }
    
    public void setCategory(final String category) {
    
        this.category = category;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final RecommendationEntry that = (RecommendationEntry) o;
        
        return Objects.equal(id, that.id) && Objects.equal(displayName, that.displayName)
                && Objects.equal(thumbnailUrl, that.thumbnailUrl)
                && Objects.equal(shortDescription, that.shortDescription)
                && Objects.equal(name, that.name) && Objects.equal(category, that.category);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(id, displayName, thumbnailUrl, shortDescription, name, category);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(id).addValue(displayName)
                .addValue(thumbnailUrl).addValue(shortDescription).addValue(name)
                .addValue(category).toString();
    }
    
}
