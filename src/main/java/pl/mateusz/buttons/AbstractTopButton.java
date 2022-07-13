package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractTopButton extends JTextField {
    protected static void initialConfigure(JTextField textfield, String initialValue) {
        textfield.setText(initialValue);
        textfield.setPreferredSize(new Dimension(50,30));
        textfield.setBackground(new Color(78,0,0));
        textfield.setForeground(new Color(255,0,0));
        textfield.setFont(new Font("Consolas",Font.PLAIN,25));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setBorder(BorderFactory.createEtchedBorder());
        textfield.setEditable(false);
    }
}
