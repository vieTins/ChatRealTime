/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class OptionButton extends JButton {

    public OptionButton() {
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @Override
    public void setSelected(boolean bln) {
        super.setSelected(bln);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        if (isSelected()) {
            grphcs.setColor(new Color(110, 213, 255));
            grphcs.fillRect(0, getHeight() - 2, getWidth(), getHeight());
        }
    }
}