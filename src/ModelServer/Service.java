/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import ModelClient.ClientHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import  ModelServer.Model_User_Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import ModelServer.ServiceUser;
import java.io.PrintWriter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FPTSHOP
 */
public class Service {
    //    list danh sách các client
    private List<ClientHandler> clients = new ArrayList<>();
    private static Service instance;
    private ServerSocket server ; 
    private JTextArea textArea;
    private Socket socket ;
    private final int PORT_NUMBER = 9999;
    private ServiceUser serviceUser;
    private List<Model_Client> listClient;
    private ServiceFile serviceFile ;
    private Model_File model_File ;

    public synchronized static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }

    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        listClient = new ArrayList<>();
        serviceFile = new ServiceFile() ;
        model_File= new Model_File();
    }

    public Model_File getModel_File() {
        return model_File;
    }

    public ServiceFile getServiceFile() {
        return serviceFile;
    }
    
    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public List<Model_Client> getClients() {
        return listClient;
    }
    
    public void startServer () {
        try {
           server = new ServerSocket(PORT_NUMBER);
           System.out.println("Server started on port : " + PORT_NUMBER);
           textArea.append("Server started on port : " + PORT_NUMBER + "\n");
//           xử lý khi có client kết nối đến server 
           new Thread(() -> {
                while (true) {
                    try {
                        socket = server.accept() ;
                        System.out.println(socket);
                        textArea.append("New Client Connected \n");
                        ClientHandler clientHandler = new ClientHandler(socket , textArea);
                        clients.add(clientHandler );    
                        new Thread(clientHandler).start();
                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
        }
    }
    public void broadcast(Model_User_Account account) throws JsonProcessingException {
        for (ClientHandler clientHandler : clients) {
             clientHandler.sendMessage(account);
        }
    }
    public void broadcastOnConnect(String email) throws JsonProcessingException {
        for (ClientHandler clientHandler : clients) {
             clientHandler.sendMessageConnect(email);
        }
    }
    public void broadcastDisconnect(String email) throws JsonProcessingException {
        for (ClientHandler clientHandler : clients) {
             clientHandler.sendMessageDisconnect(email);
        }
    }
    public void addClient (Socket client , Model_User_Account user) {
        listClient.add(new Model_Client(client, user)) ;
    }
    public String removeClient(Socket client) {
        for (Model_Client d : listClient) {
            if (d.getClient() == client) {
                listClient.remove(d);
                System.out.println("email " + d.getUser().getEmail());
                return d.getUser().getEmail();  
            }
        }
        return "";
    }
   
}
