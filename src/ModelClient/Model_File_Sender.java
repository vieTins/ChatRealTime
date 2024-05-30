/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import Controller.EventFileSender;
import ModelServer.Model_File;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.socket.client.Ack;

/**
 *
 * @author FPTSHOP
 */
public class Model_File_Sender {

    private Model_Send_Message message;
    private int fileID;
    private String fileExtension;
    private File file;
    private long fileSize;
    private RandomAccessFile accfile;   // đối tượng để đọc tệp 
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader in;
    private Model_Package_Sender data;
    private EventFileSender event;
    

    public Model_Package_Sender getData() {
        return data;
    }

    public EventFileSender getEvent() {
        return event;
    }

    public void addEvent(EventFileSender event) {
        this.event = event;
    }
    public Model_File_Sender(File file, Socket socket) throws FileNotFoundException, IOException {
        this.accfile = new RandomAccessFile(file, "r");  // được khởi tạo để đọc tệp ở read
        this.file = file;
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.fileSize = accfile.length();
        fileExtension = getExtension(file.getName());
    }

    public Model_File_Sender(File file, Socket socket, Model_Send_Message message) throws FileNotFoundException, IOException {
        this.accfile = new RandomAccessFile(file, "r");  // được khởi tạo để đọc tệp ở read
        this.file = file;
        this.socket = socket;
        this.message = message;
        fileExtension = getExtension(file.getName());
        fileSize = accfile.length();
        this.writer = new PrintWriter(Service.getInstance().getClient().getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(Service.getInstance().getClient().getInputStream()));
    }

    // đọc tệp theo khối 
    public void initSend() throws IOException {
        System.out.println("Init file to server and wait server response");
        writer.println("send_to_user");
        writer.println(message.toJsonObject().toString());
        System.out.println("message to user" + message.toJsonObject().toString());
    }
    public synchronized byte[] readFile() throws IOException {
        long filePointer = accfile.getFilePointer();
        if (filePointer != fileSize) {
            int max = 200000;   // đọc tối đa 2000 byte từ tệp , bắt đầu từ vị trí con trỏ hiện tại
            long length = filePointer + max >= fileSize ? fileSize - filePointer : max;
            byte[] data = new byte[(int) length];
            accfile.read(data);
            System.out.println("data " + data);
            return data;
        } else {    
            return null;
        }

    }

    public void startSend(int fileID) throws IOException {
        this.fileID = fileID;
        if (event !=null) {
        event.onStartSending();
        }
        sendingFile();
    }

    public void sendingFile() throws IOException {
        data = new Model_Package_Sender();
        data.setFileID(fileID);
        System.out.println("dataID " + data.getFileID());
        byte[] bytes = readFile();
        System.out.println("bytes " + readFile());
        if (bytes != null) {
            data.setData(bytes);
            data.setFinish(false);
            System.out.println("bytes k null ");
        } else {
            data.setFinish(true);
            System.out.println("bytes null ");
            close();
        }
        writer.println("send_file");
        writer.println(data.toJsonObject().toString() + " ");
    }

    public double getPercentage() throws IOException {
        double percentage;
        long filePointer = accfile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public void close() throws IOException {
        accfile.close();
    }

    public Model_Send_Message getMessage() {
        return message;
    }

    public int getFileID() {
        return fileID;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public File getFile() {
        return file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public RandomAccessFile getAccfile() {
        return accfile;
    }

    public void setMessage(Model_Send_Message message) {
        this.message = message;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setAccfile(RandomAccessFile accfile) {
        this.accfile = accfile;
    }

    public Model_File_Sender() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    
}
