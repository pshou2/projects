package learn.tier.models;

public class DisplayProfile {
    private int displayProfileId;
    private String picture;
    private String bio;
    private String twitter;
    private String instagram;
    private String tiktok;

    private String username;
    private int appUserId;

    //constructor
    public DisplayProfile(int displayProfileId, String picture, String bio, String twitter, String instagram, String tiktok, String username, int appUserId) {
        this.displayProfileId = displayProfileId;
        this.picture = picture;
        this.bio = bio;
        this.twitter = twitter;
        this.instagram = instagram;
        this.tiktok = tiktok;
        this.username = username;
        this.appUserId = appUserId;
    }

    public DisplayProfile() {
    }

    //getters and setters

    public int getDisplayProfileId() {
        return displayProfileId;
    }

    public void setDisplayProfileId(int displayProfileId) {
        this.displayProfileId = displayProfileId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTiktok() {
        return tiktok;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
