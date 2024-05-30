/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

/**
 *
 * @author FPTSHOP
 */
public class Model_Message {

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Model_Message(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public Model_Message() {
    }

    private boolean action;
    private String message;
}