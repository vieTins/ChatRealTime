package ModelClient;

import Controller.EventFileSender;
import Controller.EventLogin;
import Controller.EventMessage;
import Controller.EventRegister;
import ModelClient.MessageType;

import Controller.PublicEnvent;
import ModelServer.Model_File;
import ModelServer.ServiceFile;
import View.Image_Item;
import View.Login;
import View.Panel_More;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientListener implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private OutputStream out;
    private EventMessage storedMessage;
    private boolean success;
    private Model_User_Account user;
    private String extension;
    private Model_File model_File;
    private Model_Recive_Message message;

    public ClientListener(Socket socket) throws FileNotFoundException {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = Service.getInstance().getClient().getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(socket);
        String inputLine;
        try {
            PublicEnvent.getInstance().addEventRegister(new EventRegister() {
                @Override
                public void register(Model_Register data, EventMessage message) {
                    PrintWriter writer = new PrintWriter(out, true);
                    writer.println("register");
                    writer.println(data.toJsonObject().toString());
                    storedMessage = message;
                }

                @Override
                public void login(Model_Login data) {
                    PublicEnvent.getInstance().getEventMain().showLoading(true);
                    PrintWriter writer = new PrintWriter(out, true);
                    writer.println("login");
                    writer.println(data.toJsonObject().toString());
                }
            });
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (inputLine.startsWith("list_user")) {
                    System.out.println("Received: " + inputLine);
                    String userLine = reader.readLine();
                    System.out.println("userline " + userLine);
                    List<Model_User_Account> users = new ArrayList<>();
                    JSONArray jSONArray = new JSONArray(userLine);
                    System.out.println(jSONArray.toString());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Model_User_Account user = new Model_User_Account();
                        user.setUserName(jSONObject.getString("userName"));
                        user.setGender(jSONObject.getString("gender"));
                        user.setImage(jSONObject.getString("image"));
                        user.setStatus(jSONObject.getBoolean("status"));
                        user.setEmail(jSONObject.getString("email"));
                        if (!user.getEmail().equals(Service.getInstance().getUser().getEmail())) {
                            users.add(user);
                        }
                    }
                    PublicEnvent.getInstance().getEventMenuLeft().newUser(users);
                } else if (inputLine.startsWith("reponse_message")) {
                    String response = reader.readLine();
                    System.out.println("phan hoi  : " + response);
                    String[] parts = response.split(" ", 2);
                    success = Boolean.parseBoolean(parts[0]);
                    String messageText = parts[1];
                    Model_Message ms = new Model_Message(success, messageText);
                    System.out.println(success);
                    if (success) {
                        String user = reader.readLine();
                        System.out.println("reader " + user.toString());
                        JSONObject userJsonObject = new JSONObject(user);
                        Model_User_Account account = new Model_User_Account(userJsonObject);
                        System.out.println("account " + account.toString());
                        Service.getInstance().setUser(account);
                    }
                    storedMessage.callMessage(ms);
                } else if (inputLine.startsWith("reponse_login")) {

                    try {
                        String reponse = reader.readLine();
                        if (reponse.length() > 0) {
                            String parts[] = reponse.split(" ", 2);
                            boolean action = Boolean.parseBoolean(parts[0]);
                            if (action) {
                                String model_user_account = parts[1];
                                JSONObject userJsonObject = new JSONObject(model_user_account);
                                Service.getInstance().setUser(new Model_User_Account(userJsonObject));
                                PublicEnvent.getInstance().getEventMain().showLoading(false);
                                PublicEnvent.getInstance().getEventMain().initChat();
                            } else {
                                PublicEnvent.getInstance().getEventMain().showLoading(false);
                            }
                        } else {
                            PublicEnvent.getInstance().getEventMain().showLoading(false);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (inputLine.startsWith("user_status")) {
                    String response = reader.readLine();
                    System.out.println("phan hoi  : " + response);
                    String[] parts = response.split(" ", 2);
                    String email = parts[0];
                    boolean status = Boolean.parseBoolean(parts[1]);
                    System.out.println("status " + status);
                    if (status) {
//                        connect 
                        PublicEnvent.getInstance().getEventMenuLeft().userConnect(email);
                    } else {
//                        disconnect 
                        PublicEnvent.getInstance().getEventMenuLeft().userDisconnect(email);

                    }
                } else if (inputLine.startsWith("recevie_ms")) {
                    String recevieMs = reader.readLine();
                    System.out.println("recevieMs " + recevieMs);
                    JSONObject MS = new JSONObject(recevieMs);
                    String messageTypeValue = MS.getString("messageType");
                    int m;
                    if (messageTypeValue.equals("1")) {
                        m = 1;
                    } else if (messageTypeValue.equals("2")) {
                        m = 2;
                    } else if (messageTypeValue.equals("4")) {
                        m = 4;
                    } else {
                        m = 3;
                    }
                    MessageType messageType = MessageType.toMessageType(m);
                    message = new Model_Recive_Message();
                    message.setMessageType(messageType);
                    message.setFromEmail(MS.getString("fromEmail"));
                    message.setText(MS.getString("text"));
                    
                    PublicEnvent.getInstance().getEventChat().receviedMessage(message);
                } else if (inputLine.equals("send_fileID")) {
                    String x = reader.readLine();
                    System.out.println("sendFileID " + x);
                    String[] parts = x.split(" ", 1);
                    int id = Integer.parseInt(parts[0]);
                    Model_File_Sender sender = Service.getInstance().getData();
                    sender.startSend(id);
                } else if (inputLine.startsWith("recevie_imagaData")) {
                    String recevieMs = reader.readLine();
                    System.out.println("recevieMs " + recevieMs);
                    JSONObject MS = new JSONObject(recevieMs);
                    String messageTypeValue = MS.getString("messageType");
                    int m;
                    if (messageTypeValue.equals("1")) {
                        m = 1;
                    } else if (messageTypeValue.equals("2")) {
                        m = 2;
                    } else if (messageTypeValue.equals("4")) {
                        m = 4;
                    } else {
                        m = 3;
                    }
                    MessageType messageType = MessageType.toMessageType(m);
                    message = new Model_Recive_Message();
                    message.setMessageType(messageType);
                    message.setFromEmail(MS.getString("fromEmail"));
                    message.setText(MS.getString("text"));
                    Model_Receive_Image mri = new Model_Receive_Image();
                    mri.setFileID(MS.getInt("fileID"));
                    mri.setHeight(MS.getInt("height"));
                    mri.setWidth(MS.getInt("width"));
                    mri.setImage(MS.getString("dataImage"));
                    System.out.println("data image");
                    message.setDataImage(mri);
                    System.out.println("messag-" + message);
                    PublicEnvent.getInstance().getEventChat().receviedMessage(message);
                } else if (inputLine.startsWith("reponse_send_file")) {
                    String s1 = reader.readLine();
                    boolean m = Boolean.parseBoolean(s1);
                    Model_File_Sender sender = Service.getInstance().getData();
                    System.out.println("data sender " + sender.getEvent());
                    if (m) {
                         Image_Item item = new Image_Item() ;
                         item.getProgress().setVisible(false);
                         Service.getInstance().fileSendFinish(sender);
                    }
                } else if (inputLine.startsWith("reponse_get_file")) {
                    String inRead = reader.readLine();
                    System.out.println("inread " + inRead);
                    String[] parts = inRead.split(" ", 2);
                    Model_File_Receiver mrf = Service.getInstance().getMfr();
                    mrf.setFileExtension(parts[0].trim());
                    mrf.setFileSize(Long.parseLong(parts[1].trim()));
                    mrf.setFile(new File(mrf.getPath_File() + mrf.getFileID() + mrf.getFileExtension()));
                    mrf.setAccFile(new RandomAccessFile(mrf.getFile(), "rw"));
                    mrf.getEvent().onStartReceiving();
                    System.out.println("mrf " + mrf.toString());
//                    bắt đầu lưu file
                    mrf.startSaveFile();
                } else if (inputLine.startsWith("reponse_reques_file")) {
                    String arrayBytes = reader.readLine();
                    System.out.println("arrayBytes " + arrayBytes);
                    byte[] receivedData = Base64.getDecoder().decode(arrayBytes);
                    Model_File_Receiver mrf = Service.getInstance().getMfr();
                    System.out.println("recievedData " + receivedData.toString());
                        mrf.writeFile(receivedData);
                        mrf.getEvent().onFinish(new File(mrf.getPath_File() + mrf.getFileID() + mrf.getFileExtension()));
                        Service.getInstance().fileReceiveFinish(mrf);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
