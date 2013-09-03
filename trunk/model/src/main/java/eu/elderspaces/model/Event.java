package eu.elderspaces.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Event extends Entity {
    
    public static enum InvitationAnswer {
        YES, NO, MAYBE
    };
    
    private Date startDate; // 2013-06-13 17:15
    private String category = "";
    private String shortDescription = "";
    private String description = "";
    private String name = "";
    private Date endDate; // 2013-06-13 19:15
    
    public Event() {
    
    }
    
    public Event(final String id, final String description, final String name) {
    
        super(id);
        this.description = description;
        this.name = name;
    }
    
    public Date getStartDate() {
    
        return startDate;
    }
    
    public void setStartDate(final Date startDate) {
    
        this.startDate = startDate;
    }
    
    public String getCategory() {
    
        return category;
    }
    
    public void setCategory(final String category) {
    
        this.category = category;
    }
    
    public String getShortDescription() {
    
        return shortDescription;
    }
    
    public void setShortDescription(final String shortDescription) {
    
        this.shortDescription = shortDescription;
    }
    
    public String getDescription() {
    
        return description;
    }
    
    public void setDescription(final String description) {
    
        this.description = description;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(final String name) {
    
        this.name = name;
    }
    
    public Date getEndDate() {
    
        return endDate;
    }
    
    public void setEndDate(final Date endDate) {
    
        this.endDate = endDate;
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((category == null) ? 0 : category.hashCode());
        result = (prime * result) + ((description == null) ? 0 : description.hashCode());
        result = (prime * result) + ((endDate == null) ? 0 : endDate.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result = (prime * result) + ((shortDescription == null) ? 0 : shortDescription.hashCode());
        result = (prime * result) + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
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
        final Event other = (Event) obj;
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
        if (endDate == null) {
            if (other.endDate != null) {
                return false;
            }
        } else if (!endDate.equals(other.endDate)) {
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
        if (startDate == null) {
            if (other.startDate != null) {
                return false;
            }
        } else if (!startDate.equals(other.startDate)) {
            return false;
        }
        return true;
    }
    
}
