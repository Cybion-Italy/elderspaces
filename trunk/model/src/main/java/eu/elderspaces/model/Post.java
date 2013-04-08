package eu.elderspaces.model;

import com.google.common.base.Objects;

public class Post extends Entity {
    
    private String body;
    private String title;
    private Entity target;
    
    public Post() {
    
    }
    
    public Post(final String body, final String title, final Entity target) {
    
        this.body = body;
        this.title = title;
        this.setTarget(target);
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
    
    public Entity getTarget() {
    
        return target;
    }
    
    public void setTarget(final Entity target) {
    
        this.target = target;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Post that = (Post) o;
        
        return Objects.equal(body, that.body) && Objects.equal(title, that.title)
                && Objects.equal(target, that.target);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(body, title, target);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(body).addValue(title).addValue(target)
                .toString();
    }
    
}
