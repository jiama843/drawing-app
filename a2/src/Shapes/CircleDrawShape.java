package Shapes;

import java.awt.*;

public class CircleDrawShape extends DrawShape {

    int radius;

    boolean filled = false;
    Color fillColor;

    public CircleDrawShape(int x, int y, int s, Color c, int d){
        super(x, y, s, c, "circle");
        radius = d/2;
    }

    @Override
    public void draw(Graphics2D g2, boolean selected){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(color);

        g2.drawOval(XInit, YInit, radius*2, radius*2);

        // If selected
        if(selected) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, 2, 0, new float[]{10}, 0));
        }

        g2.drawOval(XInit, YInit, radius*2, radius*2);

        // If filled
        if (filled){
            g2.setColor(fillColor);
            g2.fillOval(XInit, YInit, radius*2, radius*2);
        }
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int r){
        radius = r;
    }

    public void fill(Color c){
        filled = true;
        fillColor = c;
    }

    public int getCenterX(){
        return XInit + radius;
    }

    public int getCenterY(){
        return YInit + radius;
    }
}
