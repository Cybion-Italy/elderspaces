package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Post extends Entity {
    
    private String body;
    private String title;
    
    public Post() {
    
    }
    
    public Post(final String body, final String title) {
    
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
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Post that = (Post) o;
        
        return Objects.equal(body, that.body) && Objects.equal(title, that.title);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(body, title);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(body).addValue(title).toString();
    }
    
}
