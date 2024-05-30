/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FPTSHOP
 */
public class HandlerFile implements Runnable {

    private Model_File_Sender sender;
    private BufferedReader reader;
    private Socket socket;

    public HandlerFile(Model_File_Sender sender , Socket socket ) {
        this.sender = sender;
        this.socket = socket ;
        try {
             this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.equals("send_fileID")) {
                    String x = reader.readLine();
                    System.out.println("sendFileID " + x);
                    String[] parts = x.split(" ", 2);
                    int id = Integer.parseInt(parts[0]);
                    sender.startSend(id);
                } else if (inputLine.equals("reponse_send_file")) {
                    String s1 = reader.readLine();
                    System.out.println("s " + s1);
                    boolean m = Boolean.parseBoolean(s1);
                    if (m) {
                        try {
                            if (!sender.getData().isFinish()) {
                                if (sender.getEvent() != null) {
                                    sender.getEvent().onSendng(sender.getPercentage());
                                }
                                System.out.println("true ne");
                                System.out.println(Service.getInstance().getFileSender().size());
                                sender.sendingFile();
                            } else {
                                System.out.println("false ne");
                                Service.getInstance().fileSendFinish(sender);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
