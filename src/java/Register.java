/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Harsh
 */
@Named(value = "register")
@RequestScoped
public class Register {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
    private String gender = "";
    private String country = "-1";
    // private Date birthDay;
    private String message;

    
    
    List<String> countryOptions;


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCountryOptions() {
        return countryOptions;
    }

    //constructor
    public Register() 
    {
        countryOptions = DB_Operations.SelectQuery_StringArrayList("select * from country");
    }
    

    //method to insert user details into registration table
    public String RegisterUser() {
        boolean isError = false;
        if (firstName.equals("") || firstName == null) {
            isError = true;
            message = "Please enter first Name";
        } else if (lastName.equals("") || lastName == null) {
            isError = true;
            message = "Please enter last Name";
        } else if (email.equals("") || email == null) {
            isError = true;
            message = "Please enter Email";
        } else if (!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            isError = true;
            message = "Invalid Email address";
        } else if (userName.equals("") || userName == null) {
            isError = true;
            message = "Please enter Chirp Handle";
        } else if (password.equals("") || password == null) {
            isError = true;
            message = "Please enter password";
        } else if (password.length() < 4) {
            isError = true;
            message = "Please enter password minimum of length 4";
        } else if (confirmPassword.equals("") || confirmPassword == null) {
            isError = true;
            message = "Please enter Confirm Password";
        } else if (!confirmPassword.equals(password)) {
            isError = true;
            message = "Password does not match";
        } else if (gender == null || (!gender.equals("M") && !gender.equals("F"))) {
            isError = true;
            message = "Please select gender";
        } else if (country.equals("") || country.equals("-1") || country == null) {
            isError = true;
            message = "Please select country";
        } else if (DB_Operations.CheckIfRowExists("select * from userdetails where email='" + email + "'")) {
            isError = true;
            message = "Email already exists.Please enter another email or login using the currently enter email.";
        } else if (DB_Operations.CheckIfRowExists("select * from userdetails where UserName='" + userName + "'")) {
            isError = true;
            message = "Chirp handle already exists.Please enter different Handle.";
        }

        if (!isError) {
            String sql = "Insert into userdetails (username,password,email,firstname,lastname,countryid,gender)"
                    + " values ('" + userName + "','" + password + "','" + email + "','" + firstName + "','" + lastName + "','" + country + "','"
                    + gender + "')";

            if (!DB_Operations.InsertUpdateQuery(sql)) {
                message = "Internal Error.Please try again";
                return "Register";
            } else {
                return "Login";
            }

        } else {
            return "Register";
        }
    }

}
