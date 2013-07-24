package eu.elderspaces.model;

import java.util.Arrays;

public class Person extends Entity {
    
    private int friendsCount = 0;
    private String[] interests = {};
    private String[] movies = {};
    private String aboutMe = "";
    private String[] languagesSpoken = {};
    private String[] tvShows = {};
    private String thumbnailUrl = "";
    
    private String name = "";
    private String pets = "";
    private String gender = "";
    private String[] activities = {};
    private String[] music = {};
    private String[] books = {};
    private String displayName = "";
    
    public Person() {
    
    }
    
    public Person(final String id, final String thumbnailUrl, final String displayName) {
    
        super(id);
        this.thumbnailUrl = thumbnailUrl;
        this.displayName = displayName;
    }
    
    public int getFriendsCount() {
    
        return friendsCount;
    }
    
    public void setFriendsCount(final int friendsCount) {
    
        this.friendsCount = friendsCount;
    }
    
    public String[] getInterests() {
    
        return interests;
    }
    
    public String getInterestsString(final String separator) {
    
        String ret = "";
        for (final String item : interests) {
            ret += item + separator;
        }
        return ret;
    }
    
    public void setInterests(final String[] interests) {
    
        this.interests = interests;
    }
    
    public String[] getMovies() {
    
        return movies;
    }
    
    public String getMoviesString(final String separator) {
    
        return getStringFromArray(movies, separator);
    }
    
    public void setMovies(final String[] movies) {
    
        this.movies = movies;
    }
    
    public String getAboutMe() {
    
        return aboutMe;
    }
    
    public void setAboutMe(final String aboutMe) {
    
        this.aboutMe = aboutMe;
    }
    
    public String[] getLanguagesSpoken() {
    
        return languagesSpoken;
    }
    
    public String getLanguagesSpokenString(final String separator) {
    
        return getStringFromArray(languagesSpoken, separator);
    }
    
    public void setLanguagesSpoken(final String[] languagesSpoken) {
    
        this.languagesSpoken = languagesSpoken;
    }
    
    public String[] getTvShows() {
    
        return tvShows;
    }
    
    public String getTvShowsString(final String separator) {
    
        return getStringFromArray(tvShows, separator);
    }
    
    public void setTvShows(final String[] tvShows) {
    
        this.tvShows = tvShows;
    }
    
    public String getThumbnailUrl() {
    
        return thumbnailUrl;
    }
    
    public void setThumbnailUrl(final String thumbnailUrl) {
    
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(final String name) {
    
        this.name = name;
    }
    
    public String getPets() {
    
        return pets;
    }
    
    public void setPets(final String pets) {
    
        this.pets = pets;
    }
    
    public String getGender() {
    
        return gender;
    }
    
    public void setGender(final String gender) {
    
        this.gender = gender;
    }
    
    public String[] getActivities() {
    
        return activities;
    }
    
    public String getActivitiesString(final String separator) {
    
        return getStringFromArray(activities, separator);
    }
    
    public void setActivities(final String[] activities) {
    
        this.activities = activities;
    }
    
    public String[] getMusic() {
    
        return music;
    }
    
    public String getMusicString(final String separator) {
    
        return getStringFromArray(music, separator);
    }
    
    public void setMusic(final String[] music) {
    
        this.music = music;
    }
    
    public String[] getBooks() {
    
        return books;
    }
    
    public String getBooksString(final String separator) {
    
        return getStringFromArray(books, separator);
    }
    
    public void setBooks(final String[] books) {
    
        this.books = books;
    }
    
    public String getDisplayName() {
    
        return displayName;
    }
    
    public void setDisplayName(final String displayName) {
    
        this.displayName = displayName;
    }
    
    private String getStringFromArray(final String[] array, final String separator) {
    
        String ret = "";
        for (final String item : array) {
            ret += item + separator;
        }
        return ret;
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((aboutMe == null) ? 0 : aboutMe.hashCode());
        result = (prime * result) + Arrays.hashCode(activities);
        result = (prime * result) + Arrays.hashCode(books);
        result = (prime * result) + ((displayName == null) ? 0 : displayName.hashCode());
        result = (prime * result) + friendsCount;
        result = (prime * result) + ((gender == null) ? 0 : gender.hashCode());
        result = (prime * result) + Arrays.hashCode(interests);
        result = (prime * result) + Arrays.hashCode(languagesSpoken);
        result = (prime * result) + Arrays.hashCode(movies);
        result = (prime * result) + Arrays.hashCode(music);
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result = (prime * result) + ((pets == null) ? 0 : pets.hashCode());
        result = (prime * result) + ((thumbnailUrl == null) ? 0 : thumbnailUrl.hashCode());
        result = (prime * result) + Arrays.hashCode(tvShows);
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
        final Person other = (Person) obj;
        if (aboutMe == null) {
            if (other.aboutMe != null) {
                return false;
            }
        } else if (!aboutMe.equals(other.aboutMe)) {
            return false;
        }
        if (!Arrays.equals(activities, other.activities)) {
            return false;
        }
        if (!Arrays.equals(books, other.books)) {
            return false;
        }
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        } else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (friendsCount != other.friendsCount) {
            return false;
        }
        if (gender == null) {
            if (other.gender != null) {
                return false;
            }
        } else if (!gender.equals(other.gender)) {
            return false;
        }
        if (!Arrays.equals(interests, other.interests)) {
            return false;
        }
        if (!Arrays.equals(languagesSpoken, other.languagesSpoken)) {
            return false;
        }
        if (!Arrays.equals(movies, other.movies)) {
            return false;
        }
        if (!Arrays.equals(music, other.music)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (pets == null) {
            if (other.pets != null) {
                return false;
            }
        } else if (!pets.equals(other.pets)) {
            return false;
        }
        if (thumbnailUrl == null) {
            if (other.thumbnailUrl != null) {
                return false;
            }
        } else if (!thumbnailUrl.equals(other.thumbnailUrl)) {
            return false;
        }
        if (!Arrays.equals(tvShows, other.tvShows)) {
            return false;
        }
        return true;
    }
}
