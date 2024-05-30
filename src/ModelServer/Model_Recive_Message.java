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
public class Model_Recive_Message {

    private String fromEmail;
    private String text;
    private int messageType;
    private Model_Recive_Image dataImage;

    public Model_Recive_Message(String fromEmail, String text, int messageType, Model_Recive_Image dataImage) {
        this.fromEmail = fromEmail;
        this.text = text;
        this.messageType = messageType;
        this.dataImage = dataImage;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getText() {
        return text;
    }

    public int getMessageType() {
        return messageType;
    }

    public Model_Recive_Image getDataImage() {
        return dataImage;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setDataImage(Model_Recive_Image dataImage) {
        this.dataImage = dataImage;
    }

    public Model_Recive_Message() {
    }

}
