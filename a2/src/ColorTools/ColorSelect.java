package ColorTools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Main.DrawCanvas;

public class ColorSelect extends JToggleButton{

    Color color;
    String colorName;
    DrawCanvas canvas;

    public ColorSelect(Color c, String s, DrawCanvas dc) {

        color = c;
        colorName = s;
        canvas = dc;

        setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        setBackground(color);
        setOpaque(true);

        addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                // Check for unselected/selected
                if(e.getStateChange() == ItemEvent.SELECTED){
                    setBorder(new LineBorder(Color.BLACK, 3));
                    canvas.setColor(color);
                }
                else {
                    setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
                }
            }
        });
    }
}
