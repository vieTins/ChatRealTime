/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProgressCircleUI;

import javax.swing.JProgressBar;

/**
 *
 * @author FPTSHOP
 */
//  JProgressBar là thành phần được sử dụng để biểu diễn tiến trình của một nhiệm vụ
public class Progress extends JProgressBar {
//     ban đầu được đặt thành None 

    private ProgressType progressType = ProgressType.NONE;

    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
//        cập nhật lại giao diện người dùng 
        repaint();
    }

    public Progress() {
//        không làm mờ 
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }

//    enum định nghĩa các loại tiến trình khác nhau mà Progress có thể hiển thị 
    public static enum ProgressType {
        NONE, DOWN_FILE, CANCEL, FILE
    }
}
