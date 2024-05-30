/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import Controller.DataBaseConnection;
import blurHash.BlurHash;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

/**
 *
 * @author FPTSHOP
 */
// quản lý các hoạt động liên quan đến tệp , thêm nhận đóng tệp 
public class ServiceFile {

    private int fileID;
    private String fileExtension;

    public ServiceFile() {
        this.con = DataBaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
         this.fileSenders = new HashMap<>();
    }

    public Model_File addFileReceiver(String fileExtension) throws SQLException {
        Model_File data;
        PreparedStatement p = con.prepareStatement(Insert, PreparedStatement.RETURN_GENERATED_KEYS);
        p.setString(1, fileExtension);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        if (r.next()) {
            fileID = r.getInt(1);
        }
        data = new Model_File(fileID, fileExtension);
        r.close();
        p.close();
        return data;
    }

    public void updateBlurHashDone(int fileID, String blurHash) throws SQLException {
            PreparedStatement p = con.prepareStatement(Update_Blur_Hash_Done );
            p.setString(1, blurHash);
            p.setInt(2, fileID);
            p.execute() ;
            p.close();
    }

    public void updateDone(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(update_Done);
        p.setInt(1, fileID);
        p.execute();
        p.close();
    }
// tạo một đối tượng nhận tệp và lưu vào fileReceivers 
    public void initFile(Model_File file, Model_Send_Message message) throws IOException {
        fileReceivers.put(file.getFileID(), new Model_File_Recevier(message, toFileObject(file))); 
    }

    public void receiveFile(Model_Package_Sender dataPackage) throws IOException {
        if (!dataPackage.isFinish()) {
            System.out.println("true");
            System.out.println("data backage " + dataPackage.getData());
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            System.out.println("close");
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }

    public Model_Send_Message closeFile(Model_Recive_Image dataImage) throws IOException, SQLException {
        Model_File_Recevier file = fileReceivers.get(dataImage.getFileID());
        if (file.getMessage().getMessageType() == MessageType.IMAGE.getValue()) {
               file.getMessage().setText("");
               String blurHash = convertFileToBlurHash(file.getFile() , dataImage) ;
               updateBlurHashDone(dataImage.getFileID(), blurHash);
        } else {
            updateDone(dataImage.getFileID());
        }
        fileReceivers.remove(dataImage.getFileID()) ;
        return file.getMessage();
    }
    private String convertFileToBlurHash (File file , Model_Recive_Image dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file) ;
        Dimension size = getAutoSize(new Dimension(img.getWidth() , img.getHeight()), new Dimension(200 , 200)) ;
        BufferedImage newImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB) ;
        Graphics2D g2 = newImage.createGraphics() ;
        g2.drawImage(img, 0, 0, size.width, size.height, null);
        String blurhash = BlurHash.encode(newImage);
        dataImage.setWidth(size.width);
        dataImage.setHeight(size.height);
        dataImage.setImage(blurhash);
        return blurhash;    
    }

    public Dimension getAutoSize(Dimension fromSize, Dimension toSize) {
        int w = toSize.width;
        int h = toSize.height;
        int iw = fromSize.width;
        int ih = fromSize.height;
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    public File toFileObject(Model_File file) {
        return new File(Path_File + file.getFileID() + file.getFileExtension());
    }
    public Model_File getFile (int fileID ) throws  SQLException {
        PreparedStatement p = con.prepareStatement(Get_file_extension) ;
        p.setInt(1, fileID);
        ResultSet r = p.executeQuery() ;
        while (r.next()) {
            fileExtension = r.getString(1) ;
        }
        Model_File data =  new Model_File(fileID, fileExtension) ;
        r.close();
        p.close(); 
        return data ;
    }
    public synchronized Model_File initFile (int fileID)  throws IOException , SQLException {
        Model_File file; 
        if (!fileSenders.containsKey(fileID)) {
            file = getFile(fileID ) ;
            fileSenders.put(fileID, new Model_File_Sender(file, new File(Path_File + fileID + file.getFileExtension()))) ;
        }
        else {
            file = fileSenders.get(fileID).getData() ;
        }
        return file ;
    }
    public byte[] getFileData (long currentLength , int fileID) throws IOException, SQLException {
        initFile(fileID) ;
        return fileSenders.get(fileID).read(currentLength ); 
    }
    public long getFileSize (int fileID) {
        return fileSenders.get(fileID).getFileSize() ;
    }
//    SQL
   private static final String Path_File = "D:\\AdvancedJAVA\\ChatApplication\\Server_Data\\";
    private final String Insert = "insert into files (FileExtension) values (?)";
    private final String Update_Blur_Hash_Done ="UPDATE TOP (1) files SET BlurHash = ?, Status = '1' WHERE fileID = ?" ;
    private final String update_Done = "Update TOP(1) files set Status ='1' where fileID =? " ;
    private final String Get_file_extension =" select top (1) [FileExtension] from files where fileID = ?" ;
//    instance
    private final Connection con;
//     sử dụng một map để lưu các các đối tượng 
    private final Map<Integer, Model_File_Recevier> fileReceivers;
    private final Map<Integer , Model_File_Sender> fileSenders;

}
