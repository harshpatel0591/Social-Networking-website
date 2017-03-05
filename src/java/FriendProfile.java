/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class FriendProfile {

    private int UserId;
    private int id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String profilePicURL;
    private String gender;
    private String buttonValue;
    private int followers;
    private int following;
    private ArrayList<FriendDetails> friendDetails=new ArrayList<>();
    private String message;
    private ArrayList<Conversation>conversation=new ArrayList<>();
    private int friendId;

    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
           
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    
    public int getFriendId() {
        return friendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
   
    public ArrayList<Conversation> getConversation() {
        return conversation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<FriendDetails> getFriendDetails() {
        return friendDetails;
    }

    public void setFriendDetails(ArrayList<FriendDetails> friendDetails) {
        this.friendDetails = friendDetails;
    }
    
    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
     
    public String getButtonValue() {
        return buttonValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
     
    /**
     * Creates a new instance of FriendProfile
     */  
    
    public FriendProfile() 
    {
        this.UserId=1;
    
    }
        
    public String ViewFriendProfile()
    {
        
        HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        friendId = 3;
        //friendId=Integer.parseInt(request.getParameter("friendId"));
        
        final String DB_URL="jdbc:mysql://localhost:3306/chirp";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"root","harsh");
         stat=conn.createStatement();
         
         
         rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
         if(rs.next())
         {
             //fetching the user details
            
             userName=rs.getString("UserName");
            
         }
         
         rs=stat.executeQuery("select * from following where UserId='"+friendId+"' ");
         if(rs.next())
         {
             String followingIds=rs.getString("FollowingIds");
             List<String> followingList = Arrays.asList(followingIds.split(","));
             following=followingList.size();
         }
         
         rs=stat.executeQuery("select * from follower where UserId='"+friendId+"'");
         if(rs.next())
         {
             String followerIds=rs.getString("FollowerIds");
             List<String> followerList = Arrays.asList(followerIds.split(","));
             followers=followerList.size();
         }
         
         //finding if he already follows the friend or not
         rs=stat.executeQuery("select * from following where UserId='"+OnlineAccount.getUserId()+"' and LOCATE('"+friendId+"',followingIds)");
         if(rs.next())
         {
              buttonValue="UnFollow";
             
         }
         
         else
         {
              buttonValue="Follow";
         }
         
         return "FriendProfile";
       }
        
       catch(SQLException e)
       {
           e.printStackTrace();
           return e.getMessage();
           
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               return e.getMessage();
           }
       }
         
    }
    
    public void Follow()
    {
        
        HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        friendId=Integer.parseInt(request.getParameter("friendId"));
       
       final String DB_URL="jdbc:mysql://localhost:3306/chirp";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
       
       try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"root","harsh");
         stat=conn.createStatement();
         
        rs=stat.executeQuery("select * from following where UserId='"+OnlineAccount.getUserId()+"' and LOCATE('"+friendId+"',followingIds)");
         if(rs.next())
         {
             //unfollowing the existing friend
              String followingIds="";
             String followerIds="";
             //selecting followinguserids from following table
             rs=stat.executeQuery("select * from following where UserId='"+OnlineAccount.getUserId()+"'");
             if(rs.next())
             {
                 followingIds=rs.getString("FollowingIds");
                 List<String> followingList = new LinkedList<String>(Arrays.asList(followingIds.split(",")));
                 String fID = friendId + "";
                 int index = followingList.indexOf(fID);
                 followingList.remove(index);
                 followingIds = "";
                 if(followingList.size()>0){
                    for(int i=0;i<followingList.size();i++)
                    {
                        if(i==0)
                        {
                           followingIds=followingIds+followingList.get(i); 
                        }
                        else
                        {
                            followingIds=followingIds.concat(","+followingList.get(i));
                        }

                    }
                 }else{
                     //followingIds = "";
                     int k = stat.executeUpdate("Delete from following where UserId='"+OnlineAccount.getUserId()+"'");
                 }
                 
             }
             
             String sql3 = "update following set FollowingIds=? where UserId='"+OnlineAccount.getUserId()+"' ";
             PreparedStatement preparedStatement3 = conn.prepareStatement(sql3);
             preparedStatement3.setString(1,followingIds);
             preparedStatement3.executeUpdate();
             
             //selecting followerid from follower table using friend id
             rs=stat.executeQuery("select * from follower where UserId='"+friendId+"'");
             if(rs.next())
             {
                 followerIds=rs.getString("FollowerIds");
                 List<String> followerList = new LinkedList<String>(Arrays.asList(followerIds.split(",")));
                 int index = followerList.indexOf(OnlineAccount.getUserId());
                 followerList.remove(index);
                 followerIds = "";
                 if(followerList.size()>0){
                    for(int i=0;i<followerList.size();i++)
                    {
                        if(i==0)
                        {
                           followerIds=followerIds+followerList.get(i); 
                        }
                        else
                        {
                            followerIds=followerIds.concat(","+followerList.get(i));
                        }

                    }
                 }else{
                     //followerIds = "";
                     int k = stat.executeUpdate("Delete from follower where UserId='"+friendId+"'");
                 }
                 
             }
             
             String sql4 = "update follower set FollowerIds=? where UserId='"+friendId+"' ";
             PreparedStatement preparedStatement4= conn.prepareStatement(sql4);
             preparedStatement4.setString(1,followerIds);
             preparedStatement4.executeUpdate();
             //Making buttonvalue follow
              buttonValue="Follow"; 
             
         }
         
         else
         {
          //checking if userId exists in following table
         rs=stat.executeQuery("select * from following where UserId='"+OnlineAccount.getUserId()+"'");
         
         if(rs.next())
         {
             //if user ID already exists then we update the following table 
             
             
             String followingIds=rs.getString("FollowingIds").concat(","+friendId);
             String sql1 = "update following set FollowingIds=? where UserId='"+OnlineAccount.getUserId()+"' ";
             PreparedStatement preparedStatement = conn.prepareStatement(sql1);
             preparedStatement.setString(1,followingIds);
             preparedStatement.executeUpdate();
             
         }
         
         
         else
         {
             //we insert a new row in to following table if  userId doesnot exist in the table 
        String sql1 = "INSERT INTO following (UserId,FollowingIds)" +"VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql1);
        preparedStatement.setString(1,OnlineAccount.getUserId() + "");
        preparedStatement.setString(2, friendId+"");
        preparedStatement.executeUpdate();
        
         
        }
         
         
         //checking if friendId exists in follower table
         rs=stat.executeQuery("select * from follower where UserId='"+friendId+"'");
         
         if(rs.next())
         {
            
             //updating follower table
             
             String followerIds=rs.getString("FollowerIds").concat(","+OnlineAccount.getUserId());
             String sql2 = "update follower set FollowerIds=? where UserId='"+OnlineAccount.getUserId()+"'  ";
             PreparedStatement preparedStatement1 = conn.prepareStatement(sql2);
             preparedStatement1.setString(1,followerIds);
             preparedStatement1.executeUpdate();
        }
        
         else
         {
             
        //we insert a new row in to followers table if  friendId doesnot exist in the table 
        String sql2 = "INSERT INTO follower (UserId,FollowerIds)" +"VALUES (?, ?)";
        PreparedStatement preparedStatement1 = conn.prepareStatement(sql2);
        preparedStatement1.setInt(1,friendId);
        preparedStatement1.setString(2,OnlineAccount.getUserId() + "");
        preparedStatement1.executeUpdate();
        
        }
         //Making buttonvalue unfollow
        buttonValue="UnFollow"; 
        
         }
         
         rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
         if(rs.next())
         {
             //fetching the user details
            
             userName=rs.getString("UserName");
            
         }
         
         rs=stat.executeQuery("select * from following where UserId='"+friendId+"' ");
         if(rs.next())
         {
             String followingIds=rs.getString("FollowingIds");
             List<String> followingList = Arrays.asList(followingIds.split(","));
             following=followingList.size();
         }
         
         rs=stat.executeQuery("select * from follower where UserId='"+friendId+"'");
         if(rs.next())
         {
             String followerIds=rs.getString("FollowerIds");
             List<String> followerList = Arrays.asList(followerIds.split(","));
             followers=followerList.size();
         }
        
      }
       
       catch(SQLException e)
       {
           e.printStackTrace();
           
           
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               e.printStackTrace();
              
           }
       }
       
        
    }
    
    //view Message Page
    public String ViewMessage()
    {
      HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
      friendId=Integer.parseInt(request.getParameter("friendId"));
     
      final String DB_URL="jdbc:mysql://localhost:3306/chirp";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
       
       try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
       
        try
       {
           
             conn = DriverManager.getConnection(DB_URL,"root","harsh");
             stat = conn.createStatement();
             rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
            if(rs.next())
                {
                //fetching the user details
            
                 userName=rs.getString("UserName");
            
                }
             rs=stat.executeQuery("SELECT u2.NumberOfMessages,u1.username,u2.message FROM userdetails u1 natural join message u2 WHERE (u2.userid = '"+OnlineAccount.getUserId()+"' and u2.friendid = '"+friendId+"') or (u2.userid = '"+friendId+"' and u2.friendid = '"+OnlineAccount.getUserId()+"') order by messagetime ASC");
             while(rs.next())
             {
                 conversation.add(new Conversation(rs.getInt("NumberOfMessages"),rs.getString("UserName"),rs.getString("Message")));
             }
             
             return "Message.xhtml";
       }
        
       catch(SQLException e)
       {
           e.printStackTrace();
           
           return e.getMessage();
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               e.printStackTrace();
               
               return e.getMessage();
                       //"Message.xhtml";
                       //e.getMessage();
           }
       }
    }
    //Messaging
  public void Message()
  {
      
      HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
      friendId=Integer.parseInt(request.getParameter("friendId"));
      
      
      final String DB_URL="jdbc:mysql://localhost:3306/chirp";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
       
       try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"root","harsh");
         stat=conn.createStatement();
         
            rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
            if(rs.next())
                {
                //fetching the user details
            
                 userName=rs.getString("UserName");
                }
         
             String sql1 = "Insert into message (UserId,FriendID,Message)"+"Values(?,?,?)";
             PreparedStatement preparedStatement = conn.prepareStatement(sql1);
             preparedStatement.setInt(1,OnlineAccount.getUserId());
             preparedStatement.setInt(2,friendId);
             preparedStatement.setString(3,message);
             preparedStatement.executeUpdate();
             
           rs=stat.executeQuery("SELECT u2.NumberOfMessages,u1.username,u2.message FROM userdetails u1 natural join message u2 WHERE (u2.userid = '"+OnlineAccount.getUserId()+"' and u2.friendid = '"+friendId+"') or (u2.userid = '"+friendId+"' and u2.friendid = '"+OnlineAccount.getUserId()+"') order by messagetime ASC");
             while(rs.next())
             {
                 conversation.add(new Conversation(rs.getInt("NumberOfMessages"),rs.getString("UserName"),rs.getString("Message")));
             }
             message="";
       }
        
       catch(SQLException e)
       {
           e.printStackTrace();
           
           
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               e.printStackTrace();
               
           }
       }
      
  }
}