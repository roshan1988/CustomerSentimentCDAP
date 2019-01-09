package com.roshan;

public final class UserSentiment {

    UserProfile userProfile;

    FacebookPost facebookPost;

    Purchase purchase;

    String sentiment;

    public UserSentiment(UserProfile userProfile, FacebookPost facebookPost, Purchase purchase, String sentiment) {
        this.userProfile = userProfile;
        this.facebookPost = facebookPost;
        this.purchase = purchase;
        this.sentiment = sentiment;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public FacebookPost getFacebookPost() {
        return facebookPost;
    }

    public void setFacebookPost(FacebookPost facebookPost) {
        this.facebookPost = facebookPost;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

}
