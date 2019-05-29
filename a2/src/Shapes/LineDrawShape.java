package Shapes;

import java.awt.*;

public class LineDrawShape extends DrawShape {

    int XEnd;
    int YEnd;

    public LineDrawShape(int x, int y, int s, Color c, int xend, int yend){
        super(x, y, s, c, "line");
        XEnd = xend;
        YEnd = yend;
    }

    @Override
    public void draw(Graphics2D g2, boolean selected){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(color);

        g2.drawLine(XInit, YInit, XEnd, YEnd);

        // If selected
        if(selected) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, 2, 0, new float[]{10}, 0));
        }

        g2.drawLine(XInit, YInit, XEnd, YEnd);
    }

    public int getXEnd(){
        return XEnd;
    }

    public int getYEnd(){
        return YEnd;
    }

    public void setXEnd(int x){
        XEnd = x;
    }

    public void setYEnd(int y){
        YEnd = y;
    }
}
