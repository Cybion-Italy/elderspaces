package eu.elderspaces.recommendations.model;

import com.google.common.base.Objects;

public class ClubEntry extends RecommendationEntry {
    
    private String name;
    private String description;
    private String shortDescription;
    private String category;
    
    public ClubEntry() {
    
    }
    
    public ClubEntry(final String id, final String name, final String description,
            final String shortDescription, final String category) {
    
        super(id);
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.category = category;
    }
    
    public String getDescription() {
    
        return description;
    }
    
    public void setDescription(final String description) {
    
        this.description = description;
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
        
        final ClubEntry that = (ClubEntry) o;
        
        return Objects.equal(description, that.description)
                && Objects.equal(shortDescription, that.shortDescription)
                && Objects.equal(name, that.name) && Objects.equal(category, that.category);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(description, shortDescription, name, category);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(description).addValue(shortDescription)
                .addValue(name).addValue(category).toString();
    }
    
}
