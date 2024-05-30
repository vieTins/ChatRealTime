/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import java.io.Serializable;

/**
 *
 * @author FPTSHOP
 */
public class Model_Register implements Serializable{

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

    public Model_Register(String userName, String password , String email ) {
        this.userName = userName;
        this.password = password;
    }

    public Model_Register() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    private String userName;
    private String password;
    private String email ;
}