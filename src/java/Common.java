
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Harsh
 */
public class Common {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chirp";
    private static final String userName = "root";
    private static final String password = "harsh";

    public static ArrayList<NewsFeedEntity> GetMyChirps() {

        ArrayList<NewsFeedEntity> obj = new ArrayList<>();
        if (OnlineAccount.getUserId() != 0 && OnlineAccount.getUserId() != -1) 
        {
            String qry = "select t.UserId,t.tweettext,t.Isretweet,t.ReTweetUserId,DATE_FORMAT(t.Date,'On %b %d %Y at %h:%i %p') dte, "
                    + " u.FirstName , u.LastName "
                    + " from tweet t left join userdetails u"
                    + " on t.ReTweetUserId = u.UserId "
                    + "where t.UserId = " + OnlineAccount.getUserId()
                    +" order by dte desc";

            Connection conn = null;
            Statement stat = null;
            ResultSet rs = null;
            try {
                LoadDriver();
                conn = DriverManager.getConnection(DB_URL, userName, password);
                stat = conn.createStatement();
                rs = stat.executeQuery(qry);
                while (rs.next()) {
                    NewsFeedEntity nf = new NewsFeedEntity("", "", 0, rs.getBoolean(3), rs.getInt(4), rs.getString(2),
                            rs.getString(6), rs.getString(7), rs.getString(5),"");
                    obj.add(nf);
                }
            } catch (SQLException ex) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    if (stat != null) {
                        stat.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;

    }
    
    /**
     *
     * @return
     */
    public static LinkedHashMap GetTrendingHashTags()
    {
        LinkedHashMap obj = new LinkedHashMap();
        String sql = "select tag,tagCount from hashtag order by tagCount desc limit 10";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try 
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) 
            {
                obj.put("#"+ rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException ex) 
        {

        } finally 
        {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stat != null) {
                    stat.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
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
    
    public static ArrayList<SearchEntity> SearchChirp(String query,int type)
    {
        ArrayList<SearchEntity> res = new ArrayList<>();
        String sql = "";
        if(type == 1)
            sql = "Select id,tag from hashtag where tag like '%"+ query +"%'";
        else
            sql = "Select userid,username from userdetails where username like '%"+ query +"%'";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try 
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            if(type == 1)
            {
                while (rs.next()) 
                {
                    res.add(new SearchEntity("#" + rs.getString(2),rs.getInt(1), type));
                }
            }
            else
            {
                while (rs.next()) 
                {
                    res.add(new SearchEntity(rs.getString(2), rs.getInt(1), type));
                }
            }
            
        } catch (SQLException ex) 
        {

        } finally 
        {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stat != null) {
                    stat.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
        }
        return res;
    }
    
    public static ArrayList<NewsFeedEntity> GetFriendProfile(int userId) {

        ArrayList<NewsFeedEntity> obj = new ArrayList<>();
        if (OnlineAccount.getUserId() != 0 && OnlineAccount.getUserId() != -1) 
        {
            String qry = "select t.UserId,t.tweettext,t.Isretweet,t.ReTweetUserId,DATE_FORMAT(t.Date,'On %b %d %Y at %h:%i %p') dte, "
                    + " u.FirstName , u.LastName "
                    + " from tweet t left join userdetails u"
                    + " on t.ReTweetUserId = u.UserId "
                    + "where t.UserId = " + userId
                    +" order by dte desc";

            Connection conn = null;
            Statement stat = null;
            ResultSet rs = null;
            try {
                LoadDriver();
                conn = DriverManager.getConnection(DB_URL, userName, password);
                stat = conn.createStatement();
                rs = stat.executeQuery(qry);
                while (rs.next()) {
                    NewsFeedEntity nf = new NewsFeedEntity("", "", 0, rs.getBoolean(3), rs.getInt(4), rs.getString(2),
                            rs.getString(6), rs.getString(7), rs.getString(5),"");
                    obj.add(nf);
                }
            } catch (SQLException ex) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    if (stat != null) {
                        stat.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;

    }
    
    public static boolean FollowOperations(int userId,int type) // for type 1- follow,2-Unfollow
    {
        if(type == 1) // follow , update logged in user id row of following and and userId sent in parameter in followers 
        {
            if(DB_Operations.CheckIfRowExists("Select * from following where userId =" + OnlineAccount.getUserId()))
            {
                ArrayList<Integer> lstUserIds = new ArrayList<>();
                String sql = "select followingids from following where userId = " + OnlineAccount.getUserId();
                String userIdSet = DB_Operations.SelectQuery_String(sql);
                String[] arr= userIdSet.split(",");
                for(String s : arr)
                {
                    lstUserIds.add(Integer.parseInt(s));
                }
                if(!lstUserIds.contains(userId))
                {
                    sql = "Update following set followingids = Concat(followingids,',',"+ userId +") where userId = " + OnlineAccount.getUserId();
                    DB_Operations.InsertUpdateQuery(sql);
                }
            }
            else
            {
                String sql = "insert into following(userId,followingIds) values ("+ OnlineAccount.getUserId() +"," + userId + ")";
                DB_Operations.InsertUpdateQuery(sql);
            }
            
            // follower table operations
            
            String qry = "Select * from follower where userId = " + userId;
            if(DB_Operations.CheckIfRowExists(qry))
            {
                qry = "Update follower set followerIds = CONCAT(followerids,','," + OnlineAccount.getUserId() + ") where userid=" + userId;
                DB_Operations.InsertUpdateQuery(qry);
            }
            else
            {
                qry = "insert into follower(userId,followerIds) values ("+ userId +"," + OnlineAccount.getUserId() + ")";
                DB_Operations.InsertUpdateQuery(qry);
            }
        }
        else // unfollow
        {
            ArrayList<String> lstUserIds = new ArrayList<>();
            String sql = "select followingids from following where userId = " + OnlineAccount.getUserId();
            String userIdSet = DB_Operations.SelectQuery_String(sql);
            String[] arr= userIdSet.split(",");
            for(String s : arr)
            {
                lstUserIds.add(s);
            }
            lstUserIds.remove(userId + "");
            if(lstUserIds.size() > 0)
            {
                String finalStr = "";
                for(String st : lstUserIds)
                {
                    finalStr += ","+ st;
                }
                finalStr = finalStr.substring(1);
                sql = "Update following set followingIds = '" + finalStr + "' where userId = " + OnlineAccount.getUserId();
                DB_Operations.InsertUpdateQuery(sql);
            }
            else
            {
                sql = "delete from following where userId =" + OnlineAccount.getUserId();
                DB_Operations.InsertUpdateQuery(sql);
            }
            
            // follower table operation
            
            String qry = "select * from follower where userid=" + userId;
            if(DB_Operations.CheckIfRowExists(qry))
            {
                ArrayList<String> obj = new ArrayList<>();
                qry = "select followerids from follower where userid=" + userId;
                String followerIds = DB_Operations.SelectQuery_String(qry);
                String[] arr2 = followerIds.split(",");
                for(String s : arr2)
                {
                    obj.add(s + "");
                }
                obj.remove(OnlineAccount.getUserId() + "");
                
                if(obj.size() > 0)
                {
                    String fStr = "";
                    for(String st : obj)
                    {
                        fStr += ","+ st;
                    }
                    fStr = fStr.substring(1);
                    qry = "Update follower set followerids = '" + fStr + "' where userid=" + userId;
                    DB_Operations.InsertUpdateQuery(qry);
                }
                else
                {
                    qry = "delete from follower where userid=" + userId;
                    DB_Operations.InsertUpdateQuery(qry);
                }
            }
        }
        return true;
    }
    
    public static boolean IsLoggedUserFollowing(int userId)
    {
        String sql = "Select followingids from following where userId=" + OnlineAccount.getUserId();
        if(DB_Operations.CheckIfRowExists(sql))
        {
            ArrayList<Integer> obj = new ArrayList<>();
            String res = DB_Operations.SelectQuery_String(sql);
            String[] arr = res.split(",");
            for(String s : arr)
            {
                obj.add(Integer.parseInt(s));
            }
            if(obj.contains(userId))
                return true;
        }
        return false;
    }
    
    public static int GetCountFromString(String str)
    {
        int count = 0;
        if(str.length() > 0)
        {
            count = str.split(",").length;
        }
        return count;
    }
    
    public static int GetCommonFriendCount(String str1,String str2)
    {
        ArrayList<String> lst1 = GetArrayList(str1);
        ArrayList<String> lst2 = GetArrayList(str2);
        int count = 0;
        
        for(String s : lst1)
        {
            if(lst2.contains(s))
                count++;
        }
        return count;
    }
    
    public static ArrayList<String> GetArrayList(String str)
    {
        ArrayList<String> lst = new ArrayList<>();
        String[] arr = str.split(",");
        if(arr.length > 0)
        {
            for(String s: arr)
            {
                lst.add(s);
            }
        }
        return lst;
    }

}
