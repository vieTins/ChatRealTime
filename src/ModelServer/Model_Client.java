/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import java.net.Socket;

/**
 *
 * @author FPTSHOP
 */
public class Model_Client {

    public Socket getClient() {
        return client;
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

    public Model_Client(Socket client, Model_User_Account user) {
        this.client = client;
        this.user = user;
    }

    public Model_Client() {
    }
    private  Socket client ;
    private Model_User_Account user ;
    
}
