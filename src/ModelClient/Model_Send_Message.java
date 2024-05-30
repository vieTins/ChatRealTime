/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import ModelClient.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Model_Send_Message {

    private String fromEmail;
    private String toEmail;
    private String text;
    private MessageType messageType;
    private Model_File_Sender file;

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

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Model_File_Sender getFile() {
        return file;
    }

    public void setFile(Model_File_Sender file) {
        this.file = file;
    }

    public Model_Send_Message(String fromEmail, String toEmail, String text, MessageType messageType) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.text = text;
        this.messageType = messageType;
    }

    public Model_Send_Message() {
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromEmail", fromEmail);
            json.put("toEmail", toEmail);
            if (messageType == MessageType.FILE || messageType == MessageType.IMAGE) {
                json.put("text", file.getFileExtension());
            } else {
                json.put("text", text);
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
