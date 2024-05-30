/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import ModelClient.Model_Login;
import ModelClient.Model_Register;

/**
 *
 * @author FPTSHOP
 */
public interface EventRegister {
     public void register (Model_Register data , EventMessage message)  ;
     public void login(Model_Login data) ;
}
