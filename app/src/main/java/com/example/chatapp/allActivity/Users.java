package com.example.chatapp.allActivity;

public class Users {
    String UserId;
    String UserName;
    String UserProfileUrl;

    public Users(String userId, String userName, String userProfileUrl) {
        UserId =userId;
        UserName = userName;
        UserProfileUrl = userProfileUrl;
    }
    public Users() {

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserProfileUrl() {
        return UserProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        UserProfileUrl = userProfileUrl;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserProfileUrl='" + UserProfileUrl + '\'' +
                '}';
    }
}
