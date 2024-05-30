/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author FPTSHOP
 */
// kết nối DBS
public class DataBaseConnection {
    private static DataBaseConnection instance  ;
    private Connection connection;
    public static DataBaseConnection getInstance (){
        if (instance == null) {
            instance = new DataBaseConnection() ;
        }
        return instance ;
    }
    private DataBaseConnection () {
        
    }
    public void connectToDatabase () throws SQLException {
        String url = "jdbc:sqlserver://LAPTOP-QAOB3NRI\\SQLEXPRESS:1433;databaseName=ChatApplication;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "123456789";
        connection = DriverManager.getConnection(url, username, password) ;
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
    
}
