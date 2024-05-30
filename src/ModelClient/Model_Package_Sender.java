/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Model_Package_Sender {
    private int fileID ;
    private byte[] data ;
    private boolean finish ;

    public int getFileID() {
        return fileID;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Model_Package_Sender(int fileID, byte[] data, boolean finish) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
    }

    public Model_Package_Sender() {
    }
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("data", Base64.getEncoder().encodeToString(data));
            json.put("finish", finish);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
