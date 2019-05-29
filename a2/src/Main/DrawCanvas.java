package Main;

import java.awt.*;
import javax.sound.midi.SysexMessage;
import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.event.*;

import LineThickness.LineThicknessPalette;
import Shapes.*;
import ColorTools.*;
import DrawTools.*;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import static javafx.scene.input.KeyCode.M;


public class DrawCanvas extends JComponent{

    public static final double LINE_THRESHOLD = 5.0;

    int mouseXInit;
    int mouseYInit;

    int mouseX;
    int mouseY;

    public static String mode = "arrow";
    public static int strokeSize = 5;
    public static Color color = Color.BLACK;

    // Keep track of index of current selected shape (-1 is unselected)
    public int shapeSelected = -1;

    // Offsets for selected shape
    int offsetX;
    int offsetY;

    int offsetXEnd;
    int offsetYEnd;

    // Toolbar stuff
    public ToolPalette tp;
    public ColorPalette cp;
    public JButton chooser;
    public LineThicknessPalette ltp;

    public ArrayList<DrawShape> shapes = new ArrayList<>();

    public DrawCanvas(int x, int y, int w, int h){

        setBackground(Color.WHITE);
        setLocation(x, y);
        setSize(x + w, y + h);
        setFocusable(true);

        // Check for esc button
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    shapeSelected = -1;
                }
                repaint();
            }
        });

        // Add listeners for motion tracking
        addMouseMotionListener( new MotionListener() );

        // Add listeners for click motions
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Set focus to canvas when clicked
                requestFocus();

                mouseXInit = e.getX();
                mouseYInit = e.getY();

                if(mode.equals("arrow")){

                    // Select Closest Shape
                    // Start from back and iterate to front of arrlist
                    // -> Use hit detection based on shape as you go along
                    shapeSelected = selectShape();

                    if(shapeSelected != -1) {
                        offsetX = mouseXInit - shapes.get(shapeSelected).getXinit();
                        offsetY = mouseYInit - shapes.get(shapeSelected).getYinit();

                        if (shapes.get(shapeSelected).shapeType.equals("line")) {

                            LineDrawShape ls = (LineDrawShape) shapes.get(shapeSelected);

                            offsetXEnd = mouseXInit - ls.getXEnd();
                            offsetYEnd = mouseYInit - ls.getYEnd();
                        }
                    }
                }
                else if(mode.equals("line") || mode.equals("circle") || mode.equals("square")){
                    shapeSelected = -1;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int mouseStartX = Math.min(mouseXInit, mouseX);
                int mouseStartY = Math.min(mouseYInit, mouseY);
                int diameter = Math.max(Math.abs(mouseX - mouseXInit), Math.abs(mouseY - mouseYInit));

                if(mode.equals("line")){
                    shapes.add(new LineDrawShape(mouseXInit, mouseYInit, strokeSize, color, mouseX, mouseY));
                }
                else if(mode.equals("circle")){
                    if(mouseStartX == mouseX && mouseStartY == mouseY) {
                        shapes.add(new CircleDrawShape(mouseXInit - diameter, mouseYInit - diameter, strokeSize, color, diameter));
                    }
                    else if(mouseStartX == mouseXInit && mouseStartY == mouseY){
                        shapes.add(new CircleDrawShape(mouseXInit, mouseYInit - diameter, strokeSize, color, diameter));
                    }
                    else if(mouseStartX == mouseXInit && mouseStartY == mouseYInit){
                        shapes.add(new CircleDrawShape(mouseXInit, mouseYInit, strokeSize, color, diameter));
                    }
                    else{
                        shapes.add(new CircleDrawShape(mouseXInit - diameter, mouseYInit, strokeSize, color, diameter));
                    }
                }
                else if(mode.equals("square")){
                    shapes.add(new SquareDrawShape(mouseStartX, mouseStartY, strokeSize, color, Math.abs(mouseX - mouseXInit), Math.abs(mouseY - mouseYInit)));
                }
                else if(mode.equals("eraser")){

                    // Set new mouse inits for sake of erasing
                    mouseXInit = e.getX();
                    mouseYInit = e.getY();

                    //Select shape to remove
                    shapeSelected = selectShape();

                    if(shapeSelected != -1) {
                        shapes.remove(shapeSelected);
                    }
                    shapeSelected = -1;
                }
                else if(mode.equals("fill")){

                    // Set new mouse inits for sake of filling
                    mouseXInit = e.getX();
                    mouseYInit = e.getY();

                    //Select shape to fill
                    shapeSelected = selectShape();

                    if(shapeSelected != -1) {
                        // Check if shape can be filled and fill it
                        if (shapes.get(shapeSelected).shapeType.equals("square")) {
                            SquareDrawShape sq = (SquareDrawShape) shapes.get(shapeSelected);
                            sq.fill(color);
                        } else if (shapes.get(shapeSelected).shapeType.equals("circle")) {
                            CircleDrawShape cir = (CircleDrawShape) shapes.get(shapeSelected);
                            cir.fill(color);
                        }
                    }

                    shapeSelected = -1;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < shapes.size(); i++){
            shapes.get(i).draw(g2, i == shapeSelected);
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(color);

        int mouseStartX = Math.min(mouseXInit, mouseX);
        int mouseStartY = Math.min(mouseYInit, mouseY);
        int diameter = Math.max(Math.abs(mouseX - mouseXInit), Math.abs(mouseY - mouseYInit));

        if(mode.equals("line")) {
            g2.drawLine(mouseXInit, mouseYInit, mouseX, mouseY);
        }
        else if(mode.equals("circle")) {
            if(mouseStartX == mouseX && mouseStartY == mouseY) {
                g2.drawOval(mouseXInit - diameter, mouseYInit - diameter, diameter, diameter);
            }
            else if(mouseStartX == mouseXInit && mouseStartY == mouseY){
                g2.drawOval(mouseXInit, mouseYInit - diameter, diameter, diameter);
            }
            else if(mouseStartX == mouseXInit && mouseStartY == mouseYInit){
                g2.drawOval(mouseXInit, mouseYInit, diameter, diameter);
            }
            else{
                g2.drawOval(mouseXInit - diameter, mouseYInit, diameter, diameter);
            }
        }
        else if(mode.equals("square")) {
            g2.drawRect(mouseStartX, mouseStartY, Math.abs(mouseX - mouseXInit), Math.abs(mouseY - mouseYInit));
        }
    }

    // Modify stroke size or color type
    public void setStroke(int s){
        strokeSize = s;

        if(shapeSelected != -1){
            shapes.get(shapeSelected).setStroke(strokeSize);
            repaint();
        }
    }

    public void setColor(Color c){
        color = c;

        if(shapeSelected != -1){
            shapes.get(shapeSelected).setColor(color);
            repaint();
        }
    }

    // Modify stroke size or color type
    public void setMode(String m){
        mode = m;
    }

    public void deselect(){
        shapeSelected = -1;

        // Probably not the best way to do this (just draw some stuff offscreen)
        mouseXInit = -1;
        mouseYInit = -1;
        mouseX = -1;
        mouseY = -1;
        repaint();
    }

    public void clear(){
        shapes = new ArrayList<>();
        shapeSelected = -1;

        // Probably not the best way to do this (just draw some stuff offscreen)
        mouseXInit = -1;
        mouseYInit = -1;
        mouseX = -1;
        mouseY = -1;
        repaint();
    }

    public void clean(){
        shapeSelected = -1;

        // Probably not the best way to do this (just draw some stuff offscreen)
        mouseXInit = 0;
        mouseYInit = 0;
        mouseX = 0;
        mouseY = 0;
        repaint();
    }

    public int selectShape(){

        for(int i = shapes.size() - 1; i >= 0; i--){

            // Check if hit test is valid
            if(shapes.get(i).shapeType.equals("line")){
                LineDrawShape line = (LineDrawShape) shapes.get(i);

                double d2 = Line2D.ptSegDist(line.getXinit(), line.getYinit(), line.getXEnd(), line.getYEnd(), mouseXInit, mouseYInit);

                // Hit detected -> add to back (most recent)
                if(d2 < LINE_THRESHOLD){
                    return i;
                }
            }
            else if(shapes.get(i).shapeType.equals("circle")){
                CircleDrawShape circle = (CircleDrawShape) shapes.get(i);

                double radius = Math.sqrt(Math.pow(mouseXInit - circle.getCenterX(), 2) + Math.pow(mouseYInit - circle.getCenterY(), 2));

                // Hit detected (inside circle) -> add to back (most recent)
                if(radius <= circle.getRadius()){
                    return i;
                }
            }
            else if(shapes.get(i).shapeType.equals("square")){
                SquareDrawShape square = (SquareDrawShape) shapes.get(i);

                // Hit detected (inside Rectangle) -> add to back (most recent)
                if(mouseXInit >= square.getXinit() &&
                        mouseXInit <= square.getXinit() + square.getWidth() &&
                            mouseYInit >= square.getYinit() &&
                                mouseYInit <= square.getYinit() + square.getHeight()){
                    return i;
                }
            }
        }
        return -1;
    }

    public void addPalettes(ToolPalette t, ColorPalette col, JButton c, LineThicknessPalette l){
        tp = t;
        cp = col;
        chooser = c;
        ltp = l;
    }

    public void disableButtons(String mode){
        if(mode == "eraser"){
            if(cp != null && ltp != null && chooser != null) {
                cp.disableAll();
                chooser.setEnabled(false);
                ltp.disableAll();
            }
        }
        else if(mode == "fill"){
            if(cp != null && ltp != null && chooser != null) {
                cp.enableAll();
                chooser.setEnabled(true);
                ltp.disableAll();
            }
        }
        else{
            if(cp != null && ltp != null && chooser != null) {
                cp.enableAll();
                chooser.setEnabled(true);
                ltp.enableAll();
            }
        }
    }

    private class MotionListener implements MouseMotionListener{
        public void mouseMoved(MouseEvent e){}

        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            if(mode.equals("arrow") && shapeSelected != -1){

                // repaint last shape on the stack (transformations)
                if(shapes.get(shapeSelected).shapeType.equals("line")) {
                    LineDrawShape ls = (LineDrawShape) shapes.get(shapeSelected);
                    ls.setXInit(mouseX - offsetX);
                    ls.setYInit(mouseY - offsetY);
                    ls.setXEnd(mouseX - offsetXEnd);
                    ls.setYEnd(mouseY - offsetYEnd);
                }
                else if(shapes.get(shapeSelected).shapeType.equals("square")) {
                    SquareDrawShape sq = (SquareDrawShape) shapes.get(shapeSelected);
                    sq.setXInit(mouseX - offsetX);
                    sq.setYInit(mouseY - offsetY);
                }
                else if(shapes.get(shapeSelected).shapeType.equals("circle")) {
                    CircleDrawShape cir = (CircleDrawShape) shapes.get(shapeSelected);
                    cir.setXInit(mouseX - offsetX);
                    cir.setYInit(mouseY - offsetY);
                }
            }
            repaint();
        }
    }
}
