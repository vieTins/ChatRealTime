/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import Controller.EventFileReceiver;
import Controller.PublicEnvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private Model_User_Account user;
    private PrintWriter out;
    private BufferedReader in;
    private List<ClientListener> clients = new ArrayList<>();
    private List<Model_File_Sender> fileSender;
    private Model_File_Sender sender ;
    private List<Model_File_Receiver> fileReceiver;
    private Model_File_Receiver mfr; 
    public static synchronized Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }
    private Model_File_Sender data;

    private Service() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
    }

    public List<Model_File_Sender> getFileSender() {
        return fileSender;
    }

    public void setData(Model_File_Sender data) {
        this.data = data;
    }
    
    public synchronized Socket getClient() {
        return client;
    }

    public synchronized void setClient(Socket client) {
        this.client = client;
        try {
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Model_User_Account getUser() {
        return user;
    }

    public synchronized void setUser(Model_User_Account user) {
        this.user = user;
    }

    public synchronized PrintWriter getOut() {
        return out;
    }

    public synchronized BufferedReader getIn() {
        return in;
    }

    public void startServer() {
        try {
            client = new Socket(IP, PORT_NUMBER);
            setClient(client);
            ClientListener clientListener = new ClientListener(client);
            clients.add(clientListener);
            new Thread(clientListener).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public Model_File_Sender addFile(File file, Model_Send_Message message) throws IOException {
        data = new Model_File_Sender(file ,client, message);
        System.out.println(".." + data.getAccfile().length());
        System.out.println("lenghth " + fileSender.size());
        message.setFile(data); 
        fileSender.add(data);
        System.out.println("lenghth " + fileSender.size());
        if (fileSender.size() == 1) {
            System.out.println("true neu file size = 1");
            data.initSend(); 
        }
        return data ;
    }
    public void fileSendFinish (Model_File_Sender data) throws IOException {
        fileSender.remove(data) ;
        if (!fileSender.isEmpty()) {
            System.out.println("true neu hoan thanh");
             sender =  fileSender.get(0);
             sender.initSend(); 
        }  
        else {
            System.out.println("ok");
            sender = null;
        }
    }
    public void fileReceiveFinish(Model_File_Receiver data) throws IOException {
        fileReceiver.remove(data);
        if (!fileReceiver.isEmpty()) {
            fileReceiver.get(0).initReceive();
        }
    }

    public void addFileReceiver(int fileID, EventFileReceiver event) throws IOException {
        mfr = new Model_File_Receiver(fileID, client, event);
        System.out.println("add File Receiver " + mfr.toString());
        fileReceiver.add(mfr);
        if (fileReceiver.size() == 1) {
            mfr.initReceive();
        }
    }
    private void error(Exception e) {
        System.err.println(e);
    }

    public Model_File_Sender getData() {
        return data;
    }

    public Model_File_Sender getModelFileSender() {
        return sender;
    }

    public Model_File_Receiver getMfr() {
        return mfr;
    }
    
}