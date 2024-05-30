/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

/**
 *
 * @author FPTSHOP
 */
public class Model_User_Account {
    String userName ;
    String gender ;
    String image  ;
    boolean status ;
    String email;
    public Model_User_Account(String userName, String gender, String image, boolean status, String email) {
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Model_User_Account() {
    }
    
}
