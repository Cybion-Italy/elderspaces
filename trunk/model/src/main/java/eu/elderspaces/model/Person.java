package eu.elderspaces.model;

public class Person extends Entity {
    
    private int friendsCount;
    private String[] interests;
    private String[] movies;
    private String aboutMe;
    private String[] languagesSpoken;
    private String[] tvShows;
    private String thumbnailUrl;
    
    private String name;
    private String pets;
    private String gender;
    private String[] activities;
    private String[] music;
    private String[] books;
    private String displayName;
    
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
    
    public void setInterests(final String[] interests) {
    
        this.interests = interests;
    }
    
    public String[] getMovies() {
    
        return movies;
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
    
    public void setLanguagesSpoken(final String[] languagesSpoken) {
    
        this.languagesSpoken = languagesSpoken;
    }
    
    public String[] getTvShows() {
    
        return tvShows;
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
    
    public void setActivities(final String[] activities) {
    
        this.activities = activities;
    }
    
    public String[] getMusic() {
    
        return music;
    }
    
    public void setMusic(final String[] music) {
    
        this.music = music;
    }
    
    public String[] getBooks() {
    
        return books;
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
    
}
