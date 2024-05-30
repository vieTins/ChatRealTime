/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author FPTSHOP
 */
public class Model_Register implements Serializable{
    private String userName ; 
    private String passWord ; 
    private String email ;

    public Model_Register(String userName, String passWord , String email) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Model_Register() {
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

     public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            json.put("password", passWord);
            json.put("email", email) ;
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
