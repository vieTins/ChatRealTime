/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

/**
 *
 * @author FPTSHOP
 */
public class Model_Recive_Image {
      private int fileID ;
      private String image ;
      private int width ;
      private int height  ;

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

    public Model_Recive_Image(int fileID, String image, int width, int height) {
        this.fileID = fileID;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Model_Recive_Image() {
    }
      
}
