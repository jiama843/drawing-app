package LineThickness;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Main.DrawCanvas;

public class LineThickness extends JToggleButton{

    int size;
    DrawCanvas canvas;

    LineThickness(int s, DrawCanvas dc, String path, int icon_height) {

        size = s;
        canvas = dc;
        setBorder(new LineBorder(Color.LIGHT_GRAY, 3));

        // Add image
        try {
            Image i = Toolkit.getDefaultToolkit().getImage(path);
            i = i.getScaledInstance(50, icon_height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(i));
        } catch (Exception exc) {
            System.out.println(exc);
        }

        addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                // Check for unselected/selected
                if(e.getStateChange() == ItemEvent.SELECTED){
                    setBorder(new LineBorder(Color.BLACK, 3));
                    canvas.setStroke(size);
                }
                else {
                    setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
                }
            }
        });
    }
}
