package eu.elderspaces.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class ActivityObject {
    
    private String body;
    private String title;
    
    public ActivityObject() {
    
    }
    
    public ActivityObject(final String body, final String title) {
    
        this.body = body;
        this.title = title;
    }
    
    public String getBody() {
    
        return body;
    }
    
    public void setBody(final String body) {
    
        this.body = body;
    }
    
    public String getTitle() {
    
        return title;
    }
    
    public void setTitle(final String title) {
    
        this.title = title;
    }
    
}
