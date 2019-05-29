package Shapes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.*;

import com.sun.glass.ui.MenuBar;

public class DrawShape implements Serializable {
  private static final long serialVersionUID = 1L;

  int strokeSize;
  Color color;

  int XInit;
  int YInit;

  public String shapeType;

  public DrawShape(){}

  public DrawShape(int x, int y, int s, Color c, String st){
    XInit = x;
    YInit = y;

    strokeSize = s;
    color = c;

    shapeType = st;
  }

  public void draw(Graphics2D g2, boolean selected){}

  public void setColor(Color c){
    color = c;
  }

  public void setStroke(int s){
    strokeSize = s;
  }

  public int getXinit(){
    return XInit;
  }

  public int getYinit(){
    return YInit;
  }

  public void setXInit(int x){
    XInit = x;
  }

  public void setYInit(int y){
    YInit = y;
  }
}