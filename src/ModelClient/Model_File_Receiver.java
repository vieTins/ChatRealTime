/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import Controller.EventFileReceiver;
import io.socket.client.Ack;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author FPTSHOP
 */
public class Model_File_Receiver {
    private int fileID ;
    private File file ;
    private long fileSize ;
    private String fileExtension ;
    private RandomAccessFile accFile ;
    private Socket socket ;
    private EventFileReceiver event ;
    private final String Path_File ="D:\\AdvancedJAVA\\ChatApplication\\Client_Data\\" ;

    public int getFileID() {
        return fileID;
    }

    public File getFile() {
        return file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public Model_File_Receiver() {
    }

    public Socket getSocket() {
        return socket;
    }

    public EventFileReceiver getEvent() {
        return event;
    }

    public String getPath_File() {
        return Path_File;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setEvent(EventFileReceiver event) {
        this.event = event;
    }
    public Model_File_Receiver(int fileID, Socket socket, EventFileReceiver event) {
        this.fileID = fileID;
        this.socket = socket;
        this.event = event;
    }
    public void initReceive () throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(new DataOutputStream(Service.getInstance().getClient().getOutputStream()) , true) ;
        writer.println("get_File");
        writer.println(fileID);
    }
    public void startSaveFile () throws IOException , JSONException{
        Model_Reques_File data = new Model_Reques_File(fileID, accFile.length()) ;
        PrintWriter writer = new PrintWriter(new DataOutputStream(Service.getInstance().getClient().getOutputStream()) , true) ;
        writer.println("reques_file");
        writer.println(data.toJsonObject().toString());
        System.out.println("data ssf " + data.toJsonObject().toString());
    }

    public synchronized long writeFile(byte[] data) throws IOException {
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public double getPercentage() throws IOException {
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public void close() throws IOException {
        accFile.close();
    }
}
