package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractTopButton extends JTextField {
    protected static void initialConfigure(JTextField textfield, String initialValue) {
        textfield.setText(initialValue);
        textfield.setPreferredSize(new Dimension(65,30));
        textfield.setBackground(new Color(78,0,0));
        textfield.setForeground(new Color(255,0,0));

        InputStream is = AbstractTopButton.class.getResourceAsStream("/fonts/upheavtt.ttf");
        if (is != null) {
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                textfield.setFont(customFont.deriveFont(32F));
                is.close();
            }
            catch (FontFormatException | IOException e) {
                e.printStackTrace();
                //Setting default font if font causes exceptions
                textfield.setFont(new Font("Consolas",Font.PLAIN,25));
            }
        }

        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setBorder(BorderFactory.createEtchedBorder());
        textfield.setEditable(false);
    }
}
