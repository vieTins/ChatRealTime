/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.EventImageView;
import Controller.EventMain;
import Controller.PublicEnvent;
import ModelClient.Model_User_Account;
import ModelServer.Service;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author FPTSHOP
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public Main() {
        initComponents();
        init(); 
    }
    public void init() {
        setIconImage(new ImageIcon(getClass().getResource("/ChatIcon/logo.png")).getImage());
        view_Image.setVisible(false);
        home.setVisible(false);
        login.setVisible(true); 
        loading.setVisible(false);
        initEvent() ;
        ModelClient.Service.getInstance().startServer(); 
    }
    private void initEvent() {
        PublicEnvent.getInstance().addEventMain(new EventMain() {
            @Override
            public void showLoading(boolean show) {
                loading.setVisible(show);
            }
            @Override
            public void initChat() {
                home.setVisible(true);
                login.setVisible(false); 
                    try {
                    PrintWriter writer = new PrintWriter(ModelClient.Service.getInstance().getClient().getOutputStream(), true); 
                    writer.println("list_user");
                    writer.println(ModelClient.Service.getInstance().getUser().getEmail()); 
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void selectUser(Model_User_Account user) {
                home.setUser(user);
            }

            @Override
            public void updateUser(Model_User_Account user) {
                home.updateUser(user);
            }
        }
        );
        PublicEnvent.getInstance().addEventImageView( new EventImageView() {
            @Override
            public void viewImage(Icon image) {
                view_Image.viewImage(image);
            }

            @Override
            public void saveImage(Icon image) {
                System.out.println("Save Image next Update ");
            }
            
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boder = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        loading = new View.Loading();
        login = new View.Login();
        view_Image = new View.View_Image();
        home = new View.Home();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        boder.setBackground(new java.awt.Color(255, 255, 255));
        boder.setLayout(new java.awt.BorderLayout());

        jLayeredPane1.setLayout(new java.awt.CardLayout());
        jLayeredPane1.add(loading, "card5");
        jLayeredPane1.add(login, "card4");
        jLayeredPane1.setLayer(view_Image, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(view_Image, "card3");
        jLayeredPane1.add(home, "card2");

        boder.add(jLayeredPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boder, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boder, javax.swing.GroupLayout.PREFERRED_SIZE, 601, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatArcIJTheme.setup() ;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boder;
    private View.Home home;
    private javax.swing.JLayeredPane jLayeredPane1;
    private View.Loading loading;
    private View.Login login;
    private View.View_Image view_Image;
    // End of variables declaration//GEN-END:variables
}
