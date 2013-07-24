package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Club extends Entity {
    
    private String name = "";
    private String description = "";
    private String shortDescription = "";
    private String category = "";
    
    public Club() {
    
    }
    
    public Club(final String id, final String name, final String description,
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
    public boolean equals(final Object obj) {
    
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Club other = (Club) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (shortDescription == null) {
            if (other.shortDescription != null) {
                return false;
            }
        } else if (!shortDescription.equals(other.shortDescription)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((category == null) ? 0 : category.hashCode());
        result = (prime * result) + ((description == null) ? 0 : description.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result = (prime * result) + ((shortDescription == null) ? 0 : shortDescription.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(description).addValue(shortDescription)
                .addValue(name).addValue(category).toString();
    }
}
