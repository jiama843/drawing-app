package Shapes;

import java.awt.*;

public class SquareDrawShape extends DrawShape {

    int width;
    int height;

    boolean filled = false;
    Color fillColor;

    public SquareDrawShape(int x, int y, int s, Color c, int w, int h){
        super(x, y, s, c, "square");
        width = w;
        height = h;
    }

    @Override
    public void draw(Graphics2D g2, boolean selected){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(color);

        g2.drawRect(XInit, YInit, width, height);

        // If selected
        if(selected) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, 2, 0, new float[]{10}, 0));
        }

        g2.drawRect(XInit, YInit, width, height);

        // If filled
        if (filled){
            g2.setColor(fillColor);
            g2.fillRect(XInit,YInit,width,height);
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int w){
        width = w;
    }

    public void setHeight(int h){
        height = h;
    }

    public void fill(Color c){
        filled = true;
        fillColor = c;
    }
}
