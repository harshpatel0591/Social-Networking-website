/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */
public class PersonDetailsEntity {
    
    private String firstName;
    private String lastName;
    private String userName;
    private int chirpCount;
    private int commonFriendCount;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getChirpCount() {
        return chirpCount;
    }

    public void setChirpCount(int chirpCount) {
        this.chirpCount = chirpCount;
    }

    public int getCommonFriendCount() {
        return commonFriendCount;
    }

    public void setCommonFriendCount(int commonFriendCount) {
        this.commonFriendCount = commonFriendCount;
    }
    
    
    public PersonDetailsEntity(String fname,String lname,String uName,int cFriend,int cCount)
    {
        firstName = fname;
        lastName = lname;
        chirpCount = cCount;
        userName = uName;
        commonFriendCount = cFriend;
    }
    
    
}
