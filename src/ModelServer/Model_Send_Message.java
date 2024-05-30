/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import ModelClient.*;

/**
 *
 * @author FPTSHOP
 */
public class Model_Send_Message {

    private String fromEmail;
    private String toEmail;
    private String text;
    private int messageType;

    public String getFromEmail() {
        return fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getText() {
        return text;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model_Send_Message(String fromEmail, String toEmail, String text, int messageType) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.text = text;
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    
    public Model_Send_Message() {
    }

}
