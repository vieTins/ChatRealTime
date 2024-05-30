/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Model_Receive_Image {

    private int fileID;
    private String image;
    private int width;
    private int height;

    public Model_Receive_Image(int fileID, String image, int width, int height) {
        this.fileID = fileID;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Model_Receive_Image(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fileID = obj.getInt("fileID");
            image = obj.getString("image");
            width = obj.getInt("width");
            height = obj.getInt("height");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public int getFileID() {
        return fileID;
    }

    public String getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
  public void setImage(String image) {
        this.image = image;
    }
  

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public JSONObject toJsonObject () {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("image", image);
            json.put("width", width);
            json.put("height", height);
            return json;
        } catch (Exception e) {
           return null ;    
        }
    }

    public Model_Receive_Image() {
    }
    
}
