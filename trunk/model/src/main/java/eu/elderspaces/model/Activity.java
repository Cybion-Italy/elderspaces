package eu.elderspaces.model;

public class Activity extends Entity {
    
    private String body = "";
    private String title = "";
    
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
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((body == null) ? 0 : body.hashCode());
        result = (prime * result) + ((title == null) ? 0 : title.hashCode());
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
        final Activity other = (Activity) obj;
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }
    
}
