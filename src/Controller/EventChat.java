/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import ModelClient.Model_Recive_Message;
import ModelClient.Model_Send_Message;

/**
 *
 * @author FPTSHOP
 */
public interface EventChat {
    public void sendMessage (Model_Send_Message data) ;
    public void receviedMessage (Model_Recive_Message data) ;
}
