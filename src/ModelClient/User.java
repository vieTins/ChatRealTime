/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

// model_register 
public class User {
    private String userName ;
    private String passWord ;
    private String emailUser ;
    private String confirmPassWord ;

    public User(String userName, String passWord, String emailUser, String confirmPassWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.emailUser = emailUser;
        this.confirmPassWord = confirmPassWord;
    }

    public User() {
    }
    
    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", passWord=" + passWord + ", emailUser=" + emailUser + ", confirmPassWord=" + confirmPassWord + '}';
    }
}
