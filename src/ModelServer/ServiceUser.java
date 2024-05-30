/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import Controller.DataBaseConnection;
import Controller.RSA;
import View.P_Register;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.PasswordView;

/**
 *
 * @author FPTSHOP
 */
public class ServiceUser {

    private final Connection con;
    private String email;
    private String pass;
    private RSA rsa;

    public ServiceUser() {
        try {
            this.rsa = new RSA();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.con = DataBaseConnection.getInstance().getConnection();
         
    }

    public Model_Message register(Model_Register data) {

        //        check user exit
        Model_Message message = new Model_Message();
        try {
            PreparedStatement p = con.prepareStatement(Check_User);
            p.setString(1, data.getEmail());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                message.setAction(false);
                message.setMessage("User already exit");
            } else {
                message.setAction(true);
            }
            r.close();
            p.close();
            if (message.isAction()) {
//                add user
//                mã hóa mật khẩu
                String passWord = data.getPassword();
                String publicKeyStr = rsa.publicKeyToString();
                String privateKeyStr = rsa.privateKeyToString();

                // Chuyển đổi chuỗi khóa công khai và khóa riêng thành đối tượng PublicKey và PrivateKey
                PublicKey publicKey = RSA.stringToPublicKey(publicKeyStr);
                PrivateKey privateKey = RSA.stringToPrivateKey(privateKeyStr);

                // Tạo một đối tượng RSA khác với khóa công khai và khóa riêng đã chuyển đổi
                RSA rsa2 = new RSA(publicKey, privateKey);
                String encryptPass = rsa2.encrypt(passWord);
                System.out.println("mat khau : " + passWord + "mat khau duoc ma hoa la : " + encryptPass);
                con.setAutoCommit(false);
                p = con.prepareStatement(Insert_User);
                p.setString(1, data.getUserName());
                p.setString(2, encryptPass);
                p.setString(3, data.getEmail());
                p.execute();
                p.close();
//                create user account
                p = con.prepareStatement(Insert_User_Account);
                p.setString(1, data.getEmail());
                p.setString(2, data.getUserName());
                p.execute() ;
                p.close(); 
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("ok");
                message.setData(new Model_User_Account(data.getUserName(), "", "", true, data.getEmail()));  
                System.out.println("t "+message.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setAction(false) ;
            message.setMessage("Server Error");
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return message;
    }
        public  List<Model_User_Account> getUser (String email) throws SQLException  {
        List<Model_User_Account> list = new ArrayList<>() ;
        PreparedStatement p = con.prepareStatement(Select_User_Account) ;
        p.setString(1, email);
        ResultSet rs = p.executeQuery() ;
        while (rs.next()) {
            String emailUser = rs.getString("email"); 
            String userName = rs.getString("userName") ;
            String gender = rs.getString("gender") ;
            String image = rs.getString("imageString"); 
            list.add(new Model_User_Account(userName, gender, image, checkUserStatus(emailUser), emailUser));
        }
        rs.close(); 
        p.close();  
        return list ;
}
        public Model_User_Account login (Model_Login login) throws SQLException, NoSuchAlgorithmException, Exception {
            Model_User_Account data = null ;
            PreparedStatement p2 = con.prepareStatement(getUserPass) ;
            p2.setString(1, login.getUserName());
            ResultSet r2 = p2.executeQuery();
            while (r2.next()) {
                pass = r2.getString("passWord");
            }
            System.out.println("pass " + pass);
//          giải mã mật khẩu được mã hóa
            String publicKeyStr = rsa.publicKeyToString();
            String privateKeyStr = rsa.privateKeyToString();

            // Chuyển đổi chuỗi khóa công khai và khóa riêng thành đối tượng PublicKey và PrivateKey
            PublicKey publicKey = RSA.stringToPublicKey(publicKeyStr);
            PrivateKey privateKey = RSA.stringToPrivateKey(privateKeyStr);

            // Tạo một đối tượng RSA khác với khóa công khai và khóa riêng đã chuyển đổi
            RSA rsa3 = new RSA(publicKey, privateKey);
            String decrpytPass = rsa3.decrypt(pass);
            System.out.println("mat khau duoc giai ma la " + decrpytPass);
            if (decrpytPass.equals(login.getPassWord())) {
                PreparedStatement p = con.prepareStatement(Login);
                p.setString(1, login.getUserName());
//              p.setString(2, login.getPassWord());
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    String emailLogin = r.getString("email");
                String userName = r.getString("userName");
                String gender = r.getString("gender");
                String imaString = r.getString("imageString");
                data = new Model_User_Account(userName, gender, imaString, true, emailLogin);
            }
            r.close();
            p.close();
        }
        return data;
    }
        private boolean checkUserStatus (String email) {
            List<Model_Client> clients = Service.getInstance(null).getClients();
            for (Model_Client c : clients) {
                if (c.getUser().getEmail().equals(email)) {
                    return true ;
                }
            }
            return false ;
        }
        
//    SQL
    private final String Login = "select UserLogin.email , UserAccount.userName , UserAccount.gender ,	UserAccount.imageString from UserLogin inner join UserAccount on UserLogin.email = UserAccount.email where UserLogin.userName COLLATE Latin1_General_BIN = ? AND UserAccount.status = '1';" ;
    private final String Select_User_Account = "select userName , email , gender , imageString  from UserAccount where [status] ='1' and email <> ?" ;
    private final String Insert_User = "Insert into UserLogin (userName , passWord , email) values (?,?,?)";
    private final String Insert_User_Account = "Insert into UserAccount (email , userName ) values (?,?)" ;
    private final String Check_User = "Select * from UserLogin where email = ?";    
    private final String getUserPass = "select passWord from [dbo].[UserLogin] where userName COLLATE Latin1_General_BIN = ? " ;

 
}
