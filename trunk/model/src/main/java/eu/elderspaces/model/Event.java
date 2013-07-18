package eu.elderspaces.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Event extends Entity {
    
    public static enum InvitationAnswer {
        YES, NO, MAYBE
    };
    
    private Date startDate; // 2013-06-13 17:15
    private String category;
    private String shortDescription;
    private String description;
    private String name;
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
    
}
