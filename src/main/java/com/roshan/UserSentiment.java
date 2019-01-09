package com.roshan;

public final class UserSentiment {

    UserProfile userProfile;

    FacebookPost facebookPost;

    Purchase[] purchases;

    String sentiment;

    public UserSentiment(UserProfile userProfile, FacebookPost facebookPost, Purchase[] purchases, String sentiment) {
        this.userProfile = userProfile;
        this.facebookPost = facebookPost;
        this.purchases = purchases;
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

    public Purchase[] getPurchases() {
        return purchases;
    }

    public void setPurchases(Purchase[] purchases) {
        this.purchases = purchases;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

}
