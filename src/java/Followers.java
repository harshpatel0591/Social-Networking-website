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
@Named(value = "followers")
@RequestScoped
public class Followers {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chirp";
    private static final String userName = "root";
    private static final String password = "harsh";

    private ArrayList<PersonDetailsEntity> details = new ArrayList<>();

    public ArrayList<PersonDetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<PersonDetailsEntity> details) {
        this.details = details;
    }
    
    
    
    
    /**
     * Creates a new instance of Followers
     */
    public Followers() 
    {
        String sql = "select firstName,lastName,userName,userId from userdetails where userId in (" + DB_Operations.SelectQuery_String("select followerIds from follower where userId=" 
                + OnlineAccount.getUserId()) + ")";
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            LoadDriver();
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                // int userId = rs.getInt(4);
                int chirpCount = DB_Operations.SelectQuerySingleRowColumn("select count(*) from tweet where userId= " + rs.getInt(4));
                String str1 = DB_Operations.SelectQuery_String("select followerids from follower where userId=" + OnlineAccount.getUserId());
                String str2 = DB_Operations.SelectQuery_String("select followerids from follower where userId=" + rs.getInt(4) );
                int commonCount = Common.GetCommonFriendCount(str1, str2);
                PersonDetailsEntity pd = new PersonDetailsEntity(rs.getString(1), rs.getString(2), rs.getString(3),commonCount,chirpCount);
                details.add(pd);
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
    
    
    
}
