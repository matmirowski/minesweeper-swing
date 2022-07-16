package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;

@Setter
@Getter

public class Field extends JButton {
    private int x_cord;
    private int y_cord;
    private boolean hidden = true;
    private boolean marked = false;
    private FieldType type = FieldType.EMPTY;

    public Field( int x, int y) {
        x_cord = x;
        y_cord = y;
        setIcon(new ImageIcon("images/icons/hidden-field.png"));
    }

    public void mark() {
        marked = true;
        setIcon(new ImageIcon("images/icons/marked-field.png"));
    }

    public void unmark() {
        marked = false;
        this.setIcon(new ImageIcon("images/icons/hidden-field.png"));
    }

    public void display() {
        hidden = false;
        String path = "images/icons/";
        path += type.toString().toLowerCase();
        path += ".png";
        setIcon(new ImageIcon(path));
    }


//    public void setIcon(String iconType) {
//        switch (iconType) {
//            case "Marked" -> {
//                this.setIcon(new ImageIcon("images/icons/marked-field.png"));
//            }
//            case "Bomb" -> {
//                this.setIcon(new ImageIcon("images/icons/bomb.png"));
//            }
//            case "Hidden" -> {
//                this.setIcon(new ImageIcon("images/icons/hidden-field.png"));
//            }
//            case "Empty" -> {
//                this.setIcon(new ImageIcon("images/icons/empty-field.png"));
//            }
//            case "1" -> {
//                this.setIcon(new ImageIcon("images/icons/1.png"));
//            }
//            case "2" -> {
//                this.setIcon(new ImageIcon("images/icons/2.png"));
//            }
//            case "3" -> {
//                this.setIcon(new ImageIcon("images/icons/3.png"));
//            }
//            case "4" -> {
//                this.setIcon(new ImageIcon("images/icons/4.png"));
//            }
//            case "5" -> {
//                this.setIcon(new ImageIcon("images/icons/5.png"));
//            }
//            case "6" -> {
//                this.setIcon(new ImageIcon("images/icons/6.png"));
//            }
//            case "7" -> {
//                this.setIcon(new ImageIcon("images/icons/7.png"));
//            }
//            case "8" -> {
//                this.setIcon(new ImageIcon("images/icons/8.png"));
//            }
//        }
//    }




}
