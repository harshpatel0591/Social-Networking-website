/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;


/**
 *
 * @author Harsh
 */
@Named(value = "home")
@RequestScoped
public class Home {

//    private String firstName = OnlineAccount.getIsLoggedIn()? OnlineAccount.getFirstName() : "";
//    private String lastName = OnlineAccount.getIsLoggedIn() ? OnlineAccount.getLastName(): "";
//    private String profilePic = OnlineAccount.getIsLoggedIn() ? OnlineAccount.getProfilePicUrl(): "";
//    private int userId = OnlineAccount.getIsLoggedIn() ? OnlineAccount.getUserId():-1;
    
    private String firstName = "";
    private String lastName = "";
    private String profilePic = "";
    private int userId = 1;
    private String message;
    private int tweetCounts;
    private ArrayList<NewsFeedEntity> feeds;
    private ArrayList<String> hashTagsUsed;
    private String displayHashTags;
    private LinkedHashMap TrendingHashTags;
    private String tweetText;
    private String searchQuery;
    private ArrayList<SearchEntity> searchResult;
    private int following;
    private int followers;
    
    



    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
    

    public ArrayList<SearchEntity> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(ArrayList<SearchEntity> searchResult) {
        this.searchResult = searchResult;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public HashMap getTrendingHashTags() {
        return TrendingHashTags;
    }

    public void setTrendingHashTags(LinkedHashMap TrendingHashTags) {
        this.TrendingHashTags = TrendingHashTags;
    }

    public String getDisplayHashTags() {
        return displayHashTags;
    }

    public void setDisplayHashTags(String displayHashTags) {
        this.displayHashTags = displayHashTags;
    }
    
    public ArrayList<String> getHashTagsUsed() {
        return hashTagsUsed;
    }

    public void setHashTagsUsed(ArrayList<String> hashTagsUsed) {
        this.hashTagsUsed = hashTagsUsed;
    }

    
    
    public ArrayList<NewsFeedEntity> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<NewsFeedEntity> feeds) {
        this.feeds = feeds;
    }

    
    
    public int getTweetCounts() {
        return tweetCounts;
    }

    public void setTweetCounts(int tweetCounts) {
        this.tweetCounts = tweetCounts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }   

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    /**
     * Creates a new instance of Home
     */
    public Home() 
    {
        firstName = OnlineAccount.getFirstName();
        lastName = OnlineAccount.getLastName();
        userId = OnlineAccount.getUserId();
        CountDetails();
        feeds = DB_Operations.GetNewsFeeds();
        TrendingHashTags = Common.GetTrendingHashTags();
        
        following = Common.GetCountFromString(DB_Operations.SelectQuery_String("select followingids from following where userId=" + OnlineAccount.getUserId()));
        
        followers = Common.GetCountFromString(DB_Operations.SelectQuery_String("select followerids from follower where userId=" + OnlineAccount.getUserId()));
        
        profilePic = DB_Operations.SelectQuery_String("Select profilePicurl from userdetails where userId=" + OnlineAccount.getUserId());
        if(profilePic == null || profilePic == "")
            profilePic = "Images/gender.png"; 
    }
    
    public void CountDetails()
    {
        tweetCounts = DB_Operations.SelectQuerySingleRowColumn("select count(*) from tweet where userId=" + userId);
    }
    
    public String ReChirp(String text,int reChirpUserId)
    {
        String sql = "Insert into tweet(UserId,tweettext,IsRetweet,ReTweetUserId)"
                + " values (" + OnlineAccount.getUserId() + ",'" + text + "',1," + reChirpUserId + ")";
        DB_Operations.InsertUpdateQuery(sql);
        message = "Re-Chirped successfully";
        CountDetails();
        return "Home";
    }
    
    
    public String SubmitTweet()
    {
            if(tweetText != null && !tweetText.equals(""))
            {
                String sql = "Insert into tweet (userId,tweettext)"
                    + " values (" + userId + ",'" + tweetText + "')";
                DB_Operations.InsertUpdateQuery(sql);
                message = "Chirped successfully";
                CountDetails();
                InsertHashTags();
                tweetText = "";
                displayHashTags = "";
                TrendingHashTags = Common.GetTrendingHashTags();
            }
            else
                message = "Chirp cannot be empty.Please enter your Chirp";
            return "Home";
    }
    

    
    
    public void InsertHashTags()
    {
        CheckHashTags();
        for(String s : hashTagsUsed)
        {
            String sql = "Select 1 from hashtag where tag='" + s + "'";
            if(DB_Operations.CheckIfRowExists(sql))
                sql = "Update hashtag set tagCount = tagCount + 1 where tag='" + s + "'";
            else
                sql = "Insert Into hashtag (Tag,TagCount) values ('" + s + "',1)";
            
            DB_Operations.InsertUpdateQuery(sql);
        }
    }
    
    public void SearchChirpQuery()
    {
        if(searchQuery.length() > 0)
        {
            if(searchQuery.charAt(0) == '#' && searchQuery.length() > 1)
                searchResult = Common.SearchChirp(searchQuery.substring(1) , 1);
            else
                searchResult = Common.SearchChirp(searchQuery, 2);
        }
        
    }
    
    public void CheckHashTags()
    {
        hashTagsUsed = new ArrayList<>();
        if(tweetText.length() > 0)
        {
            String[] arrWords = tweetText.split(" ");
            for(String wrd:arrWords)
            {
                if(wrd.charAt(0) == '#' && wrd.length() > 1)
                {
                    if(!hashTagsUsed.contains(wrd.substring(1).toLowerCase()))
                        hashTagsUsed.add(wrd.substring(1).toLowerCase());
                }
            }
        }
        displayHashTags = String.join(",#", hashTagsUsed);
        if(displayHashTags.length() > 0)
            displayHashTags = "#" + displayHashTags;
    }
    
    public String RedirectToSearchResult()
    {
        if(searchQuery.length() > 0)
        {
            SearchRedirection.setSearchText(searchQuery);
            if(searchQuery.charAt(0) == '#')
                return "HashTagDetails.xhtml";
            else
                return "ViewProfile.xhtml";
        }
        return "Home";
    }
    
    
}
