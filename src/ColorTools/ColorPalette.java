package ColorTools;

import Main.DrawCanvas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

// Panel for the tool palette (Select, DrawRectangle, DrawCircle, Line, Erase, Fill)
public class ColorPalette extends JPanel {

    ButtonGroup bg = new ButtonGroup();
    ArrayList<ColorSelect> buttons = new ArrayList<>();

    public ColorPalette(DrawCanvas canvas) {

        setLayout(new GridLayout(3, 2));
        //setPreferredSize(new Dimension(80, 75));
        setBackground(Color.BLACK);

        ColorSelect blue = new ColorSelect(Color.BLUE, "blue", canvas);
        ColorSelect red = new ColorSelect(Color.RED, "red", canvas);
        ColorSelect orange = new ColorSelect(Color.ORANGE, "orange", canvas);
        ColorSelect yellow = new ColorSelect(Color.YELLOW, "yellow", canvas);
        ColorSelect green = new ColorSelect(Color.GREEN, "green", canvas);
        ColorSelect pink = new ColorSelect(Color.PINK, "pink", canvas);

        buttons.add(blue);
        buttons.add(red);
        buttons.add(orange);
        buttons.add(yellow);
        buttons.add(green);
        buttons.add(pink);

        for(int i = 0; i < buttons.size(); i++){
            bg.add(buttons.get(i));
            add(buttons.get(i));
        }

        // Set default tool
        blue.setSelected(true);
    }

    public void deselectAll(){
        bg.clearSelection();
    }

    public void disableAll(){
        for(ColorSelect cs: buttons){
            cs.setEnabled(false);
            cs.setBackground(Color.GRAY);
        }
    }

    public void enableAll(){
        for(ColorSelect cs: buttons){
            cs.setEnabled(true);
            cs.setBackground(cs.color);
        }
    }
}