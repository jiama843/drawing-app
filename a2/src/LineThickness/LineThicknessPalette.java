package LineThickness;

import Main.DrawCanvas;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class LineThicknessPalette extends JPanel{

    ButtonGroup bg = new ButtonGroup();
    ArrayList<LineThickness> buttons = new ArrayList<>();

    public LineThicknessPalette(DrawCanvas canvas) {

        setLayout(new GridLayout(3, 1));
        setBackground(Color.WHITE);

        LineThickness small = new LineThickness(5, canvas, "src/resources/thickness_1.png", 5);
        LineThickness medium = new LineThickness(12, canvas, "src/resources/thickness_2.png", 15);
        LineThickness large = new LineThickness(32, canvas, "src/resources/thickness_3.png", 25);

        buttons.add(small);
        buttons.add(medium);
        buttons.add(large);

        for(int i = 0; i < buttons.size(); i++){
            bg.add(buttons.get(i));
            add(buttons.get(i));
        }

        // Set default tool
        small.setSelected(true);
    }

    public void disableAll(){
        for(LineThickness lt: buttons){
            lt.setEnabled(false);
        }
    }

    public void enableAll(){
        for(LineThickness lt: buttons){
            lt.setEnabled(true);
        }
    }
}
