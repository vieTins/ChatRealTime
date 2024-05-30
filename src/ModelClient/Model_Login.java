/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import ModelServer.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Model_Login {
    private String userName ;
    private String passWord ;

    public Model_Login() {
    }

    public Model_Login(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
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
    public JSONObject toJsonObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("userName", userName);
            obj.put("passWord", passWord);
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }
    
}
