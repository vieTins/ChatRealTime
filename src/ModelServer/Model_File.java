/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

/**
 *
 * @author FPTSHOP
 */
public class Model_File {
    private int fileID ;
    private String fileExtension ;

    public int getFileID() {
        return fileID;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Model_File(int fileID, String fileExtension) {
        this.fileID = fileID;
        this.fileExtension = fileExtension;
    }

    public Model_File() {
    }
    
}
