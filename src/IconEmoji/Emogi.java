/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IconEmoji;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author FPTSHOP
 */
public class Emogi {
    private static Emogi instance  ;
    public static Emogi getInstance () {
        if (instance == null) {
            instance = new Emogi();
        }
        return instance;
    }

    private Emogi() {

    }

    public List<Model_Emoji> getStyle1() {
        List<Model_Emoji> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            list.add(new Model_Emoji(i, new ImageIcon(getClass().getResource("/IconEmoji/IconEmoji/" + i + ".png"))));
        }
        return list;
    }

    public List<Model_Emoji> getStyle2() {
        List<Model_Emoji> list = new ArrayList<>();
        for (int i = 7; i <= 10; i++) {
            list.add(new Model_Emoji(i, new ImageIcon(getClass().getResource("/IconEmoji/IconEmoji/" + i + ".png"))));
        }
        return list;
    }
    public Model_Emoji getEmoji (int id) { 
        return new Model_Emoji(id , new ImageIcon(getClass().getResource("/IconEmoji/IconEmoji/" + id + ".png"))); 
    }
}
