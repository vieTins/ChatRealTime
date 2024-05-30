/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Model_Reques_File {
   private int fileID  ;
   private long currentLength  ;

    public int getFileID() {
        return fileID;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public Model_Reques_File(int fileID, long currentLength) {
        this.fileID = fileID;
        this.currentLength = currentLength;
    }

    public Model_Reques_File() {
    }
   public JSONObject toJsonObject () {
       try {
           JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("currentLength", currentLength);
            return json;
       } catch (Exception e) {
           return null ;
       }
   }
}
