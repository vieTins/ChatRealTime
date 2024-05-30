/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IconEmoji;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 *
 * @author FPTSHOP
 */
public class Model_Emoji {
    int  id ;
    Icon icon ;

    public int getId() {
        return id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Model_Emoji(int id, Icon icon) {
        this.id = id;
        this.icon = icon;
    }

    public Model_Emoji() {
    }
    public Model_Emoji toSize (int x , int y ) {
        return new Model_Emoji(id, new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
    }
}
