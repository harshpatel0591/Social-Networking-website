/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Harsh
 */
@Named(value = "login")
@RequestScoped
public class Login {

    private String email;
    private String password;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String LoginUser()
    {
        boolean isError = false;
        if(email == null || email.equals(""))
        {
            message = "Please enter Email";
            isError = true;
        }
        else if(!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"))
        {
            message = "Please enter a valid email";
            isError = true;
        }
        else if(password.equals("") || password == null || password.length() < 4)
        {
            message = "Please enter valid password";
            isError = true;
        }
        else if(!DB_Operations.CheckIfRowExists("select 1 from userdetails where email='" + email + "' and password ='" + password + "'"))
        {
            message = "Invalid Credentials";
            isError = true;
        }
        
        if(!isError)
        {
            OnlineAccount.setFirstName(DB_Operations.SelectQuery_String("select firstName from userdetails where email='" + email + "' and password ='" + password + "'"));
            OnlineAccount.setLastName(DB_Operations.SelectQuery_String("select lastname from userdetails where email='" + email + "' and password ='" + password + "'"));
            OnlineAccount.setProfilePicUrl(DB_Operations.SelectQuery_String("select profilepicUrl from userdetails where email='" + email + "' and password ='" + password + "'"));
            OnlineAccount.setUserId(DB_Operations.SelectQuerySingleRowColumn("select userid from userdetails where email='" + email + "' and password ='" + password + "'"));
            OnlineAccount.setUserName(DB_Operations.SelectQuery_String("select userName from userdetails where email='" + email + "' and password ='" + password + "'"));
            OnlineAccount.setEmail(email);
            OnlineAccount.setIsLoggedIn(true);
            return "Home";
        }
        return "Login";
    }
    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    public String Logout()
    {
        OnlineAccount.DestroySession();
        return "Login";
    }
    
    
}
