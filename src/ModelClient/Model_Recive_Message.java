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
public class Model_Recive_Message {
    private String fromEmail ;
    private  String text ;
    private Model_Receive_Image dataImage ;

    public String getFromEmail() {
        return fromEmail;
    }

    public String getText() {
        return text;
    }

    public Model_Receive_Image getDataImage() {
        return dataImage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDataImage(Model_Receive_Image dataImage) {
        this.dataImage = dataImage;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    
    public Model_Recive_Message(String fromEmail, String text, MessageType messageType) {
        this.fromEmail = fromEmail;
        this.text = text;
        this.messageType = messageType;
    }
    private MessageType messageType;

    public Model_Recive_Message(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromEmail = obj.getString("fromEmail");
            text = obj.getString("text");
            if (!obj.isNull("dataImage")) {
                dataImage = new Model_Receive_Image(obj.get("dataImage")) ;
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue()) ;
            json.put("fromEmail", fromEmail);
            json.put("text", text);
            if (dataImage!=null) {
                  json.put("dataImage", dataImage.toJsonObject()) ;
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    public Model_Recive_Message() {
    }
    
}
