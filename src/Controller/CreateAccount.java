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

public class CreateAccount {

    public void createAccountUSer(String userName, String passWordLogin, String conFirmPass, String email) throws Exception {
        try {
            RSA rsa = new RSA();
            String publicKeyStr = rsa.publicKeyToString();
            System.out.println("Public Key: " + publicKeyStr);
//           mã hóa mật khẩu trước 
            String encryptedData = rsa.encrypt(passWordLogin);
            System.out.println("Encrypted Data: " + encryptedData);
//           giải mã mật khẩu 
            PublicKey publicKey = stringToPublicKey(publicKeyStr);
            String decryptedData = rsa.decrypt(encryptedData);
            System.out.println("Decrypted Data: " + decryptedData);
            Connection connection = null;
            PreparedStatement statement = null;
            String url = "jdbc:sqlserver://LAPTOP-QAOB3NRI\\SQLEXPRESS:1433;databaseName=ChatApplication;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "123456789";
            try {
                connection = DriverManager.getConnection(url, username, password);
                String queryChek = "Select * from User where email =? ";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryChek)) {
                    preparedStatement.setString(1, email);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Your email account has been created", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!passWordLogin.equals(conFirmPass)) {
                        JOptionPane.showMessageDialog(null, "Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String sql = "Insert into User(userName , passWord , email ) values (  ? ,? ,?)";
                        PreparedStatement statement2 = connection.prepareCall(sql);
                        statement2.setString(1, userName);
//                       lưu mật khẩu đã mã hóa lên dbs 
                        statement2.setString(2, encryptedData);
                        statement2.setString(3, email);
                        statement2.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Đóng kết nối và câu lệnh
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
