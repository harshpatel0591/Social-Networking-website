/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */
public class SearchEntity 
{
    private String searchResultText;
    private int searchResultType;
    private int searchResultId;
    
    public SearchEntity(String txt,int id,int type)
    {
        searchResultId = id;
        searchResultText = txt;
        searchResultType = type;
    }

    public String getSearchResultText() {
        return searchResultText;
    }

    public void setSearchResultText(String searchResultText) {
        this.searchResultText = searchResultText;
    }

    public int getSearchResultType() {
        return searchResultType;
    }

    public void setSearchResultType(int searchResultType) {
        this.searchResultType = searchResultType;
    }

    public int getSearchResultId() {
        return searchResultId;
    }

    public void setSearchResultId(int searchResultId) {
        this.searchResultId = searchResultId;
    }

    
    
    
    
}
