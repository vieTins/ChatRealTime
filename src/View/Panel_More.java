/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Controller.PublicEnvent;
import IconEmoji.Emogi;
import IconEmoji.Model_Emoji;
import ModelClient.MessageType;
import ModelClient.Model_File_Sender;
import ModelClient.Model_Send_Message;
import ModelClient.Model_User_Account;
import ModelClient.Service;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author FPTSHOP
 */
public class Panel_More extends javax.swing.JPanel {

    /**
     * Creates new form Panel_More
     */
    private JPanel panelHeader;
    private JPanel panelDetail;
    private Model_User_Account user;
    private Model_Send_Message message;
    private Model_File_Sender sender;

    public Model_User_Account getUser() {
        return user;
    }

    public Panel_More() {
        initComponents();
        init();
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

    private void init() {
        setLayout(new MigLayout("fillx"));
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.LINE_AXIS));
        panelHeader.add(getButtonImage());
        panelHeader.add(getButtonFile());
        panelHeader.add(getEmojiStyle1());
        panelHeader.add(getEmojiStyle2());
        add(panelHeader);
        add(panelHeader, "w 100%, h 30!, wrap");
        panelDetail = new JPanel();
        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));  // sử dụng wrap layout 
        JScrollPane ch = new JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new JScrollBar());

        add(ch, "w 100% , h 100%");
    }

    private JButton getButtonImage() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/ChatIcon/image.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ch = new JFileChooser();
                ch.setMultiSelectionEnabled(true);
                ch.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || isImageFile(f);
                    }

                    @Override
                    public String getDescription() {
                        return "Image File";
                    }
                });
                int option = ch.showOpenDialog(Main.getFrames()[0]);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File files[] = ch.getSelectedFiles();
                    handleFileUpload(files);
//                     try {
//                         for (File file : files) {
//                             message = new Model_Send_Message(Service.getInstance().getUser().getEmail() , user.getEmail() , "" , MessageType.IMAGE) ;
////                           Tạo một nơi chứa file
//                             Service.getInstance().addFile(file, message);
//                             PublicEnvent.getInstance().getEventChat().sendMessage(message);
//                         }
//                     } catch (Exception e1) {
//                         e1.printStackTrace();
//                     }

                }
            }
        });
        return cmd;
    }

    private JButton getButtonFile() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/ChatIcon/link.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ch = new JFileChooser();
                ch.showOpenDialog(Main.getFrames()[0]);
            }
        });
        return cmd;
    }

    private JButton getEmojiStyle1() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(Emogi.getInstance().getEmoji(1).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emogi.getInstance().getStyle1()) {
                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }
        });
        return cmd;
    }

    private JButton getEmojiStyle2() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/IconEmoji/IconEmoji/7.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emogi.getInstance().getStyle2()) {
                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }
        });
        return cmd;
    }

    private void clearSelected() {
        for (Component c : panelHeader.getComponents()) {
            if (c instanceof OptionButton) {
                ((OptionButton) c).setSelected(false);
            }
        }
    }

    private JButton getButton(Model_Emoji data) {
        JButton c = new JButton(data.getIcon());
        c.setName(data.getId() + "");
        c.setBorder(new EmptyBorder(3, 3, 3, 3));
        c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.setContentAreaFilled(false);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message = new Model_Send_Message(Service.getInstance().getUser().getEmail(), user.getEmail(), data.getId() + "", MessageType.EMOJI);
                sendMessage(message);
                PublicEnvent.getInstance().getEventChat().sendMessage(message);
            }
        });
        return c;
    }

    private void sendMessage(Model_Send_Message data) {
        try {
            PrintWriter writer = new PrintWriter(Service.getInstance().getClient().getOutputStream(), true);
            writer.println("send_to_user");
            writer.println(data.toJsonObject().toString());
        } catch (IOException ex) {
            Logger.getLogger(Panel_More.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
    }

    public Model_Send_Message getMessage() {
        return message;
    }

    private void handleFileUpload(File[] files) {
        try {
            for (File file : files) {
                message = new Model_Send_Message(Service.getInstance().getUser().getEmail(), user.getEmail(), "", MessageType.IMAGE);
//               Tạo một nơi chứa file
                this.sender = Service.getInstance().addFile(file, message);
                PublicEnvent.getInstance().getEventChat().sendMessage(message);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
