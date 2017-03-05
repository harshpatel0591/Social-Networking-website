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
@Named(value = "pageHeader")
@RequestScoped
public class PageHeader {

    private boolean isLoggedIn;

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    
    
    
    /**
     * Creates a new instance of PageHeader
     */
    public PageHeader() 
    {
        isLoggedIn = OnlineAccount.getIsLoggedIn();
    }
    
}
