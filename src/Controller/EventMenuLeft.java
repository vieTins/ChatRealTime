/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;
import ModelClient.Model_User_Account ;
import java.util.List;
/**
 *
 * @author FPTSHOP
 */
public interface EventMenuLeft {
    public void newUser(List<Model_User_Account> users) ;
    public void userConnect (String email) ;
    public void userDisconnect (String email) ;
}
