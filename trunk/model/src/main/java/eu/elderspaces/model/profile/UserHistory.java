package eu.elderspaces.model.profile;

import java.util.List;

import com.google.common.base.Objects;

import eu.elderspaces.model.Person;
import eu.elderspaces.model.Post;

public class UserHistory {
    
    private Person user;
    private List<Post> posts;
    
    public UserHistory() {
    
    }
    
    public UserHistory(final Person user, final List<Post> posts) {
    
        this.user = user;
        this.posts = posts;
    }
    
    public Person getUser() {
    
        return user;
    }
    
    public void setUser(final Person user) {
    
        this.user = user;
    }
    
    public List<Post> getPosts() {
    
        return posts;
    }
    
    public void setPosts(final List<Post> posts) {
    
        this.posts = posts;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final UserHistory that = (UserHistory) o;
        
        return Objects.equal(user, that.user) && Objects.equal(posts, that.posts);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(user, posts);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(user).addValue(posts).toString();
    }
    
}
