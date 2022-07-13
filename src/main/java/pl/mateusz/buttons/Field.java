package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

@Setter
@Getter

public class Field extends JButton {
    private int x_cord;
    private int y_cord;
    private boolean bomb = false;
    private boolean hidden = true;
    private boolean marked = false;

    public Field( int x, int y) {
        x_cord = x;
        y_cord = y;
    }

    public void setBomb (boolean bool) {
        if (bool) {
            bomb = true;
            setIcon("Bomb");
        }
        else {
            bomb = false;
            setIcon("Hidden");
        }
    }


    public void setIcon(String iconType) {
        switch (iconType) {
            case "Marked" -> {
                this.setIcon(new ImageIcon("images/icons/marked-field.jpg"));
            }
            case "Bomb" -> {
                this.setIcon(new ImageIcon("images/icons/bomb.jpg"));
            }
            case "Hidden" -> {
                this.setIcon(new ImageIcon("images/icons/hidden-field.jpg"));
            }
            case "Empty" -> {
                this.setIcon(new ImageIcon("images/icons/empty-field.jpg"));
            }
            case "1" -> {
                this.setIcon(new ImageIcon("images/icons/1.jpg"));
            }
            case "2" -> {
                this.setIcon(new ImageIcon("images/icons/2.jpg"));
            }
            case "3" -> {
                this.setIcon(new ImageIcon("images/icons/3.jpg"));
            }
            case "4" -> {
                this.setIcon(new ImageIcon("images/icons/4.jpg"));
            }
            case "5" -> {
                this.setIcon(new ImageIcon("images/icons/5.jpg"));
            }
            case "6" -> {
                this.setIcon(new ImageIcon("images/icons/6.jpg"));
            }
            case "7" -> {
                this.setIcon(new ImageIcon("images/icons/7.jpg"));
            }
            case "8" -> {
                this.setIcon(new ImageIcon("images/icons/8.jpg"));
            }
        }
    }




}
