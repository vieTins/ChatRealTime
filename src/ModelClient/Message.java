/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

/**
 *
 * @author FPTSHOP
 */
public class Message {
    private boolean action ;
    private String message ;

    public Message() {
    }

    public Message(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public boolean isAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
