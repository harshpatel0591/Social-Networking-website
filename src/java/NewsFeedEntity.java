/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */
public class NewsFeedEntity 
{
    private String userFirstName;
    private String userLastName;
    private int userId;
    private boolean isRetweet;
    private int reTweetUserId;
    private String tweetText;
    private String retweetFirstName;
    private String retweetLastName;
    private String message;
    private String date;
    private String userName;
    
    public NewsFeedEntity(String fn,String ln,int uid,boolean rt,int ruid,String txt,String rtFn,String rtLn,String dte,String uname)
    {
        userFirstName = fn;
        userLastName = ln;
        userId = uid;
        isRetweet = rt;
        reTweetUserId = ruid;
        tweetText = txt;
        retweetFirstName = rtFn;
        retweetLastName = rtLn;
        date = dte;
        userName = uname;
        message = isRetweet ? "Re-Chirped from " + retweetFirstName + " " + retweetLastName:"Chirped";
        message += " " + date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRetweetFirstName() {
        return retweetFirstName;
    }

    public void setRetweetFirstName(String retweetFirstName) {
        this.retweetFirstName = retweetFirstName;
    }

    public String getRetweetLastName() {
        return retweetLastName;
    }

    public void setRetweetLastName(String retweetLastName) {
        this.retweetLastName = retweetLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isIsRetweet() {
        return isRetweet;
    }

    public void setIsRetweet(boolean isRetweet) {
        this.isRetweet = isRetweet;
    }

    public int getReTweetUserId() {
        return reTweetUserId;
    }

    public void setReTweetUserId(int reTweetUserId) {
        this.reTweetUserId = reTweetUserId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
    
    
    
    
    
}
