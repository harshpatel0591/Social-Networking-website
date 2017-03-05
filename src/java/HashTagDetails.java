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
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Harsh
 */
@Named(value = "hashTagDetails")
@RequestScoped
public class HashTagDetails {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chirp";
    private static final String userName = "root";
    private static final String password = "harsh";
    
   private String hashTagText;
   private ArrayList<NewsFeedEntity> feeds;

    public ArrayList<NewsFeedEntity> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<NewsFeedEntity> feeds) {
        this.feeds = feeds;
    }

    public String getHashTagText() {
        return hashTagText;
    }

    public void setHashTagText(String hashTagText) {
        this.hashTagText = hashTagText;
    }
    
    public ArrayList<NewsFeedEntity> GetTweets()
    {
        ArrayList<NewsFeedEntity> obj = new ArrayList<>();
        String sql = "Select tbl.fn,tbl.ln,tbl.uid,tbl.rt,tbl.rtid,tbl.tt, u2.FirstName rtfn,"
                + "u2.LastName rtln , DATE_FORMAT(tbl.dte,'On %b %d %Y at %h:%i %p'),tbl.uname "
                + "from (select u.username uname, u.firstName fn,u.lastName ln,u.userId uid,t.isRetweet rt,t.retweetUserId rtid,t.tweetText tt,t.Date dte "
                + "from tweet t,userdetails u where t.userID = u.userId) tbl "
                + "left join userdetails u2 on tbl.rtid = u2.UserId "
                + "where tbl.tt LIKE '%"+ hashTagText +"%' "
                + "order by tbl.dte DESC";
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while(rs.next())
            {
                NewsFeedEntity nf = new NewsFeedEntity(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getBoolean(4),rs.getInt(5)
                        , rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
                obj.add(nf);
            }
        }
        catch(SQLException ex)
        {
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return obj;
    }
    
    public static void LoadDriver()
    {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
        }
    }
   
   

    /**
     * Creates a new instance of HashTagDetails
     */
    public HashTagDetails() {
        hashTagText = SearchRedirection.getSearchText();
        feeds = GetTweets();
        
    }
    
}
