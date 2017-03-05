
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */
public class DB_Operations {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chirp";
    private static final String userName = "root";
    private static final String password = "harsh";
    
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
    public static boolean InsertUpdateQuery(String query)
    {
        // For Insert , Update and delete operations
        
        boolean response = false;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            stat.executeUpdate(query);
            response = true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            response = false;
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
        return response;
    }
    
    public static int SelectQuerySingleRowColumn(String query)
    {
        // For a Query Returning a Single Integer Value
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        int resp = -1;
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = rs.getInt(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
        return resp;
    }
    
    public static String SelectQuery_String(String query)
    {
        // for query returning single String value
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String resp = "";
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = rs.getString(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
        return resp;
    }
    
    public static boolean CheckIfRowExists(String query)
    {
        
        // To check if a Query returns a row
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean resp = false;
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
        return resp;
    }
    
    
    
    public static ArrayList<Integer> SelectQuery_IntArrayList(String query)
    {
        // For a query that returns an ArrayList<Integer>
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<Integer> resp = new ArrayList<>();
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while(rs.next())
            {
                resp.add(rs.getInt(1));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
        return resp;
    }
    
    
    
    public static double SelectQuery_Double(String query)
    {
        // For a Query that returns a Double Single Value
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        double resp = -1;
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = rs.getInt(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
        return resp;
    }
    
    
    public static ArrayList<String> SelectQuery_StringArrayList(String query)
    {
        // For a Query that Returns An ArrayList<String>
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<String> ary = new ArrayList<>();
        try
        {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while(rs.next())
            {
                ary.add(rs.getString(1));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return ary;
    }
    
    public static ArrayList<NewsFeedEntity> GetNewsFeeds()
    {
        ArrayList<NewsFeedEntity> obj = new ArrayList<>();
        if(OnlineAccount.getUserId() != 0 && OnlineAccount.getUserId() != -1)
        {
            String qry = "select followingIds from following where userId=" + OnlineAccount.getUserId();
            
            String sql= "Select tbl.fn,tbl.ln,tbl.uid,tbl.rt,tbl.rtid,tbl.tt, u2.FirstName rtfn,u2.LastName rtln , DATE_FORMAT(tbl.dte,'On %b %d %Y at %h:%i %p')"
                    + ", tbl.uname"
                    + " from (select u.username uname, u.firstName fn,u.lastName ln,u.userId uid,t.isRetweet rt,t.retweetUserId rtid,t.tweetText tt,t.Date dte "
                    + " from tweet t,userdetails u where t.userID = u.userId) "
                    + " tbl left join userdetails u2 on tbl.rtid = u2.UserId "
                    + " where tbl.uid in (" + DB_Operations.SelectQuery_String(qry) + ") order by tbl.dte DESC";
            
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
        }
        return obj;
    }
    
}
