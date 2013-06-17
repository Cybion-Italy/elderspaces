package eu.elderspaces.model;

public class Activity extends Entity {
    
    private String body;
    private String title;
    
    public Activity() {
    
    }
    
    public Activity(final String id, final String body, final String title) {
    
        super(id);
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
