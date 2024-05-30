/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Controller.RSA.stringToPublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Login {

    private String decryptedPassword;

    public void loginIntoApp(String userName, String passwordLogin, String email) throws NoSuchAlgorithmException {
        RSA rsa = new RSA();
        String publicKeyStr = rsa.publicKeyToString();
        try {
            PublicKey publicKey = stringToPublicKey(publicKeyStr);
//            giải mã mật khẩu 
            decryptedPassword = rsa.decrypt(passwordLogin);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection connection = null;
        PreparedStatement statement = null;
        String url = "jdbc:sqlserver://LAPTOP-QAOB3NRI\\SQLEXPRESS:1433;databaseName=ChatApplication;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "123456789";
        try {   
            connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM User WHERE userName = ? AND email = ? AND passWord = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setString(3, decryptedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (userName.equals("") || passwordLogin.equals("") || email.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Full Information", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Log In Successful");
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong password , email or user name " , "Error" ,JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
