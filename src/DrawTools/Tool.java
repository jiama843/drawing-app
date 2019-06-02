package DrawTools;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Main.DrawCanvas;

public class Tool extends JToggleButton{

    String toolName;
    DrawCanvas canvas;

    Tool(String s, DrawCanvas dc, String path) {

        toolName = s;
        canvas = dc;
        setBorder(new LineBorder(Color.LIGHT_GRAY, 3));

        // Add image
        try {
            Image i = Toolkit.getDefaultToolkit().getImage(path);
            i = i.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
                    canvas.setMode(toolName);
                    if(toolName.equals("eraser")){
                        canvas.disableButtons("eraser");
                    }
                    else if(toolName.equals("fill")){
                        canvas.disableButtons("fill");
                    }
                    else{
                        canvas.disableButtons("other");
                    }
                }
                else {
                    if(toolName.equals("arrow")){
                        canvas.deselect();
                    }
                    setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
                }
            }
        });
    }
}
