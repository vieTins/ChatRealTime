/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import Controller.EventFileSender;
import ModelClient.MessageType;
import ModelServer.Model_Client;
import ModelServer.Model_File;
import ModelServer.Model_Message;
import ModelServer.Model_Recive_Image;
import ModelServer.Model_Register;
import ModelServer.Model_Send_Message;
import ModelServer.ServiceUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.json.JSONException;
import org.json.JSONObject;
import ModelServer.Model_User_Account;
import static io.socket.client.IO.socket;
import java.io.DataOutputStream;
import java.util.Base64;

/**
 *
 * @author FPTSHOP
 */
// xử lý client
public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea textArea;
    private ModelServer.Service service;
    private ServiceUser serviceUser;
//    private List<Model_Client> listClient;
    private Model_Send_Message msm;

    public ClientHandler(Socket socket, JTextArea textArea) {
        this.clientSocket = socket;
        this.textArea = textArea;
        
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("inputLine " + inputLine);
                    // Xử lý yêu cầu từ client ở đây
                    switch (inputLine) {
                        case "register":
                            // Đọc thông tin đăng ký từ client
                            String registerInfo = in.readLine();
                            Model_Register user = parseRegisterInfo(registerInfo);
                            // xử lý đăng ký 
                            Model_Message message = new ServiceUser().register(user);
                            // gửi phản hồi về client
                            out.println("reponse_message");
                            out.println(message.isAction() + " " + message.getMessage());
                            textArea.append(message.isAction() + "  " + message.getMessage() + " \n ");
                            // Gửi thông tin người dùng đăng ký về client
                            JSONObject userJsonObject = new JSONObject(message.getData());
                            out.println(userJsonObject.toString());
                            if (message.isAction()) {
                                textArea.append("User has reqgistered: " + registerInfo + "\n");
                                service.getInstance(textArea).broadcast((Model_User_Account) message.getData());
                                service.getInstance(textArea).addClient(clientSocket, (Model_User_Account) message.getData());
                            }
                            break;
                        case "list_user":
                            String email = in.readLine();
                            handdleListUser(email);
                            break;
                        case "login":
                            String account = in.readLine();
                            ObjectMapper objectMapper = new ObjectMapper();
                            ModelServer.Model_Login login = objectMapper.readValue(account, ModelServer.Model_Login.class);
                            Model_User_Account loginUser = ModelServer.Service.getInstance(textArea).getServiceUser().login(login);
                            String jsonAccount = objectMapper.writeValueAsString(loginUser);
                            if (loginUser != null) {
                                out.println("reponse_login");
//                                    chuyển một chuỗi json
                                out.println(true + " " + jsonAccount.toString());
                                service.getInstance(textArea).addClient(clientSocket, loginUser);
                                service.getInstance(textArea).broadcastOnConnect(loginUser.getEmail());
                            } else {
                                out.println("reponse_login");
                                out.println(false);
                            }
                            break;
                        case "send_to_user":
//                                lấy thông tin từ client gửi đến server 
                            String messageUser = in.readLine();
                            System.out.println("thong tin tu client : " + messageUser);
//                                 chuyển sang đối tượng json từ chuỗi 
                            JSONObject MS = new JSONObject(messageUser);
                            String messageTypeValue = MS.getString("messageType");
                            int m = 0;
                            if (messageTypeValue.equals("1")) {
                                m = 1;
                            } else if (messageTypeValue.equals("2")) {
                                m = 2;
                            } else if (messageTypeValue.equals("3")) {
                                m = 3;
                            } else {
                                m = 4;
                            }
//                          MessageType messageType = MessageType.toMessageType(m);
                            msm = new ModelServer.Model_Send_Message();
                            msm.setMessageType(m);
                            msm.setFromEmail(MS.getString("fromEmail"));
                            msm.setToEmail(MS.getString("toEmail"));
                            msm.setText(MS.getString("text"));
                            sendToClient(msm);
                            break;
                        case "send_file":
                                String messageuser = in.readLine();
                                System.out.println("messageuser " + messageuser);
                                System.out.println(messageuser);
                                JSONObject userObject = new JSONObject(messageuser);
                                ModelServer.Model_Package_Sender model_Package_Sender = new ModelServer.Model_Package_Sender();
                                String base64Data = userObject.getString("data");
                                System.out.println("chuoi base64 " + base64Data);
                                byte[] data = Base64.getDecoder().decode(base64Data);
                                System.out.println("chuoi sau ma hoa " + data);
                                model_Package_Sender.setData(data);
                                model_Package_Sender.setFinish(userObject.getBoolean("finish"));
                                model_Package_Sender.setFileID(userObject.getInt("fileID"));
                                System.out.println("data " + model_Package_Sender.getData());
                                ModelServer.Service.getInstance(null).getServiceFile().receiveFile(model_Package_Sender);
                            if (model_Package_Sender.isFinish()) {
                                out.println("reponse_send_file");
                                out.println("true");
//                                 gửi đi event 
                                System.out.println("true");
                                ModelServer.Model_Recive_Image dataImgae = new ModelServer.Model_Recive_Image();
                                dataImgae.setFileID(model_Package_Sender.getFileID());
                                System.out.println("iD databackage " + model_Package_Sender.getFileID());
                                ModelServer.Model_Send_Message messageU = ModelServer.Service.getInstance(null).getServiceFile().closeFile(dataImgae);
//                               gửi đến client messageU
                                sendTempFileToClient(messageU, dataImgae);
                            } else {
                                System.out.println("false");
                                out.println("reponse_send_file");
                                out.println("true");
                                System.out.println("true");
                                ModelServer.Model_Recive_Image dataImgae = new ModelServer.Model_Recive_Image();
                                dataImgae.setFileID(model_Package_Sender.getFileID());
                                System.out.println("iD databackage " + model_Package_Sender.getFileID());
                                ModelServer.Model_Send_Message messageU = ModelServer.Service.getInstance(null).getServiceFile().closeFile(dataImgae);
//                              gửi đến client messageU
                                sendTempFileToClient(messageU, dataImgae);
                            }
                            break;
                         case "get_File":
                             String reponse = in.readLine() ;
                             System.out.println("reponse " + reponse);
                             int t = Integer.parseInt(reponse) ;
                             ModelServer.Model_File file = ModelServer.Service.getInstance(null).getServiceFile().initFile(t);
                             long fileSize = ModelServer.Service.getInstance(null).getServiceFile().getFileSize(t) ;
                             System.out.println("t " + file.getFileID() + fileSize);
                             out.println("reponse_get_file");
                             out.println(file.getFileExtension() + " " + String.valueOf(fileSize) );
                            break;
                         case "reques_file":
                            String reques = in.readLine();
                            System.out.println("readline " + reques);
                            JSONObject jsono = new JSONObject(reques) ;
                            Model_Reques_File mrf = new Model_Reques_File() ;
                            mrf.setCurrentLength(jsono.getLong("currentLength"));
                            mrf.setFileID(jsono.getInt("fileID"));
                            byte[] datas = ModelServer.Service.getInstance(null).getServiceFile().getFileData(mrf.getCurrentLength() , mrf.getFileID()) ;
                            if (datas!= null) {
                                String encodedString = Base64.getEncoder().encodeToString(datas);
                                System.out.println("datas " + encodedString);
                                System.out.println("data deo null");
                                out.println("reponse_reques_file");
                                out.println(encodedString);
                            }
                            else {
                                System.out.println("data null me roi ");
                            } 
                            break;
                        default:
                            throw new AssertionError();
                    }
                }
            } catch (IOException e) {
                if (e instanceof java.net.SocketException && "Connection reset".equals(e.getMessage())) {
                    System.out.println("Connection reset by client: " + clientSocket);
                } else {
                    e.printStackTrace();
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
                    out.println("reponse_send_file");
                    out.println("true");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                out.println("reponse_send_file");
                out.println("true");
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handdleListUser(String email) throws SQLException, IOException {
//          gửi danh sách user đến server 
        ModelServer.ServiceUser serviceUser = new ModelServer.ServiceUser();
        List<ModelServer.Model_User_Account> list = serviceUser.getUser(email);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(list);
//          gửi chuỗi json tới client 
        out.println("list_user");
        out.println(jsonString);
    }

    private Model_Register parseRegisterInfo(String json) {
        json = json.replaceAll("[{}\"]", "");
        String[] keyValuePairs = json.split(",");
        Model_Register user = new Model_Register();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            String key = entry[0].trim();
            String value = entry[1].trim();
            switch (key) {
                case "userName":
                    user.setUserName(value);
                    break;
                case "password":
                    user.setPassword(value);
                    break;
                case "email":
                    user.setEmail(value);
                    break;
            }
        }
        return user;
    }

    public void sendMessage(Model_User_Account account) throws JsonProcessingException {
        List<Model_User_Account> list = new ArrayList<>();
        list.add(account);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(list);
        out.println("list_user");
        out.println(jsonString);
    }

    public String removeClient(Socket client) {
        for (Model_Client d : ModelServer.Service.getInstance(null).getClients()) {
            System.out.println("remove " + d);
            if (d.getClient() == client) {
                ModelServer.Service.getInstance(null).getClients().remove(d);
                System.out.println("email " + d.getUser().getEmail());
                return d.getUser().getEmail();
            }
        }
        return "";
    }

    public void sendMessageConnect(String email) {
        out.println("user_status");
        out.println(email + " " + true);
    }

    public void sendMessageDisconnect(String email) {
        out.println("user_status");
        out.println(email + " " + false);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    private void disconnect() throws JsonProcessingException {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String email = service.getInstance(textArea).removeClient(clientSocket);
        if (!email.isEmpty()) {
            textArea.append("Client with email " + email + " disconnected\n");
            service.getInstance(textArea).broadcastDisconnect(email);
        }   
    }

    private void sendToClient(ModelServer.Model_Send_Message data) throws JSONException, IOException {
        if (data.getMessageType() == MessageType.IMAGE.getValue() || data.getMessageType() == MessageType.FILE.getValue()) {
            try {
                Model_File file = ModelServer.Service.getInstance(null).getServiceFile().addFileReceiver(data.getText());
                ModelServer.Service.getInstance(null).getServiceFile().initFile(file, data);
                out.println("send_fileID");
                out.println(file.getFileID());
            } catch (Exception e) { 
                e.printStackTrace();
            }
        } else {
            for (Model_Client c : service.getInstance(null).getClients()) {
                if (c.getUser().getEmail().equals(data.getToEmail())) {
                    PrintWriter writer = new PrintWriter(c.getClient().getOutputStream(), true);
                    writer.println("recevie_ms");
//                  gửi tới client nội dung tin nhắn 
                    ModelServer.Model_Recive_Message ms = new ModelServer.Model_Recive_Message(data.getFromEmail(), data.getText(), data.getMessageType(), null);
//                  chuyển sang json Object để gửi 
                    JSONObject json = new JSONObject();
                    json.put("messageType", ms.getMessageType());
                    json.put("fromEmail", ms.getFromEmail());
                    json.put("text", ms.getText());
                    json.put("imageData", ms.getDataImage());
                    System.out.println("Send to client : " + json.toString());
                    writer.println(json.toString());
                    break;
                }
            }
        }

    }

    private void sendTempFileToClient(Model_Send_Message data, ModelServer.Model_Recive_Image dataImage) throws JSONException, IOException {
        for (Model_Client c : service.getInstance(null).getClients()) {
            if (c.getUser().getEmail().equals(data.getToEmail())) {
                PrintWriter writer = new PrintWriter(c.getClient().getOutputStream(), true);
                writer.println("recevie_imagaData");
                ModelServer.Model_Recive_Message message = new ModelServer.Model_Recive_Message(data.getFromEmail(), data.getText(), data.getMessageType(), dataImage);
                JSONObject json = new JSONObject();
                json.put("messageType", message.getMessageType());
                json.put("fromEmail", message.getFromEmail());
                json.put("text", message.getText());
                json.put("fileID", message.getDataImage().getFileID());
                json.put("width" , message.getDataImage().getWidth()) ;
                json.put("height", message.getDataImage().getHeight());
                json.put("dataImage", message.getDataImage().getImage());
                System.out.println("dataImage " + message.getDataImage().getImage());
                System.out.println("sendTempFileToClient " + json.toString());
                writer.println(json.toString());
                break;
            }
        }
    }
}
