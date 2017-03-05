/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.Part;

/**
 *
 * @author Harsh
 */
@Named(value = "uploadFileTest")
@SessionScoped
public class UploadFileTest implements Serializable {

    /**
     * Creates a new instance of UploadFileTest
     */
    public UploadFileTest() {
    }
    
    private Part file;
    private final int userId = OnlineAccount.getUserId();
    private final String baseUrl = "E:\\UHCL Study\\By_Semester\\Fall2016\\Java\\Projects\\Group_Project\\Latest\\Chirp\\web\\Images";

    public String getBaseUrl() {
        return baseUrl;
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public String Upload() 
    {
        String finalUrl = "E:\\UHCL Study\\By_Semester\\Fall2016\\Java\\Projects\\Group_Project\\Latest\\Chirp\\web\\Images";
        try {
            // File newDir = new File("E:\\UHCL Study\\By_Semester\\Fall2016\\Java\\Projects\\Group_Project\\Chirp\\web\\Images\\" + userId);
            
            //deleteDirectory(newDir);
            
            // newDir.mkdir();
            finalUrl += "\\"+file.getSubmittedFileName() ;
            
            
            InputStream in = file.getInputStream();
            File f;
            f = new File(finalUrl);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);

            byte[] data = new byte[1024];
            int length;

            while ((length = in.read(data)) > 0) {
                out.write(data, 0, length);
            }

            out.close();
            in.close();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", f.getAbsolutePath());
            // uploaded = true;
            
            String imgUrl = "Images/" + file.getSubmittedFileName();
            
            String sql = "Update userdetails set profilepicUrl = '" + imgUrl +"' where userId =" + OnlineAccount.getUserId();
            DB_Operations.InsertUpdateQuery(sql);

        } catch (Exception e) {
        }
        return "Home";
    }
    
    public boolean deleteDirectory(File path) 
    {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
