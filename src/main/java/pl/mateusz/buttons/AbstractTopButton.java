package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Contains common code for MineCounter and Stopwatch
 * Params: textField (mineCounter or stopwatch), initialValue (value displayed on object)
 */
public abstract class AbstractTopButton extends JTextField {
    protected static void initialConfigure(JTextField textField, String initialValue) {
        textField.setText(initialValue);
        textField.setPreferredSize(new Dimension(65,30));
        textField.setBackground(new Color(78,0,0));
        textField.setForeground(new Color(255,0,0));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(BorderFactory.createEtchedBorder());
        textField.setEditable(false);

        // Custom font loading & setting
        InputStream is = AbstractTopButton.class.getResourceAsStream("/fonts/upheavtt.ttf");
        if (is != null) {
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                textField.setFont(customFont.deriveFont(32F));
                is.close();
            }
            catch (FontFormatException | IOException e) {
                e.printStackTrace();
                //Setting default font if font causes exceptions
                textField.setFont(new Font("Consolas",Font.PLAIN,25));
            }
        }
    }
}
