package DrawTools;

import Main.DrawCanvas;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// Panel for the tool palette (Select, DrawRectangle, DrawCircle, Line, Erase, Fill)
public class ToolPalette extends JPanel {

  ButtonGroup bg = new ButtonGroup();
  ArrayList<Tool> buttons = new ArrayList<>();

  public ToolPalette(DrawCanvas canvas) {

    setLayout(new GridLayout(3, 2));
    setBackground(Color.WHITE);

    Tool arrow = new Tool("arrow", canvas, "src/resources/select_icon.png");
    Tool eraser = new Tool("eraser", canvas, "src/resources/eraser_icon.png");
    Tool line = new Tool("line", canvas, "src/resources/line_icon.png");
    Tool circle = new Tool("circle", canvas, "src/resources/circle_icon.png");
    Tool square = new Tool("square", canvas, "src/resources/square_icon.png");
    Tool fill = new Tool("fill", canvas, "src/resources/fill_icon.png");

    buttons.add(arrow);
    buttons.add(eraser);
    buttons.add(line);
    buttons.add(circle);
    buttons.add(square);
    buttons.add(fill);

    // Set default tool
    arrow.setSelected(true);

    for(int i = 0; i < buttons.size(); i++){
      bg.add(buttons.get(i));
      add(buttons.get(i));
    }
  }
}