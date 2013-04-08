package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Person extends Entity implements Comparable<Person> {
    
    private String displayName;
    private String thumbnailUrl;
    
    public Person() {
    
    }
    
    public Person(final String id, final String displayName, final String thumbnailUrl) {
    
        super(id);
        this.displayName = displayName;
        this.thumbnailUrl = thumbnailUrl;
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
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Person that = (Person) o;
        
        return Objects.equal(displayName, that.displayName)
                && Objects.equal(thumbnailUrl, that.thumbnailUrl);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(displayName, thumbnailUrl);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(displayName).addValue(thumbnailUrl).toString();
    }
    
    @Override
    public int compareTo(final Person otherPerson) {
    
        return this.getId().compareTo(otherPerson.getId());
    }
    
}
