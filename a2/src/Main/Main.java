package Main;

import java.awt.*;
import javax.swing.*;

import java.awt.datatransfer.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import DrawTools.*;
import ColorTools.*;
import LineThickness.*;
import Shapes.DrawShape;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


/* TODO:
 
Draw widgets (Swing):
 -> Toolbar
 -> Menu
 
Draw Shapes:
 -> Arbitrary Shapes
 -> Next class

Layout:
 -> Menu (Top)
 -> Toolbars (Side)
    -> Tools
    -> Colours
    -> Line

  Implementation method:
  -> From within JPanel, use "GridLayout". 
  -> Add GridLayout(2, 3), then add components in order

 -> Canvas

 -> Hit detection
  -> Circle:
    -> Calculate distance from radius

*/

public class Main{

  public static void main(String [] args){

    JFrame window = new JFrame("Doodle (jl2ma)");
    window.setSize(800, 600);
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create Canvas
    DrawCanvas canvas = new DrawCanvas(100, 0, 700, 600);

    // Create and populate top menu bar
    JMenuBar menuBar = createMenuBar(canvas);
    window.setJMenuBar(menuBar);

    // Create and populate toolbar
    JPanel toolbar = createToolbar(canvas);
    window.add(toolbar);

    // Very interesting case where we need to initialize canvas first, but add it after
    window.getContentPane().add(canvas);

    window.setVisible(true);
  }

  // Creates Top Menu Bar
  public static JMenuBar createMenuBar(DrawCanvas canvas){
    JMenuBar menuBar = new JMenuBar();

    // Create File Tab
    JMenu file = new JMenu("File");

    // Create File sub-tab
    JMenuItem newfile = new JMenuItem("New");
    newfile.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        canvas.clear();
      }
    });
    file.add(newfile);

    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        saveDrawing(canvas);
      }
    });
    file.add(save);

    JMenuItem load = new JMenuItem("Load");
    load.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        loadDrawing(canvas);
      }
    });
    file.add(load);

    JMenuItem copy = new JMenuItem("Copy");
    copy.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){

        // Copy canvas to system-level clipboard
        BufferedImage b = (BufferedImage) canvas.createImage(canvas.getWidth(), canvas.getHeight());
        canvas.paint(b.getGraphics());

        TransferableImg tImage = new TransferableImg(b);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(tImage, tImage);
      }
    });
    file.add(copy);
    menuBar.add(file); // Add to Menu Bar

    // Create useless view tab
    JMenu view = new JMenu("View");
    menuBar.add(view);

    return menuBar;
  }

  // Creates side toolbar
  public static JPanel createToolbar(DrawCanvas canvas){
    JPanel toolbar = new JPanel();

    GridBagLayout gb = new GridBagLayout();
    toolbar.setLayout(gb);
    toolbar.setSize(100, 557);
    //toolbar.setAlignmentX( Component.LEFT_ALIGNMENT );

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 1;

    gbc.gridx = 0;
    gbc.gridy = 0;
    ToolPalette toolPalette = new ToolPalette(canvas);
    gb.setConstraints(toolPalette, gbc);
    toolbar.add(toolPalette);

    gbc.gridx = 0;
    gbc.gridy = 1;
    ColorPalette colorPalette = new ColorPalette(canvas);
    gb.setConstraints(colorPalette, gbc);
    toolbar.add(colorPalette);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weighty = 0.25;
    JButton chooser = new JButton("chooser");

    chooser.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Color newColor = JColorChooser.showDialog(chooser, "Choose Color", DrawCanvas.color);
        canvas.setColor(newColor);
        colorPalette.deselectAll();
      }
    });

    gb.setConstraints(chooser, gbc);
    toolbar.add(chooser);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weighty = 0.5;
    LineThicknessPalette lineThicknessPalette = new LineThicknessPalette(canvas);
    gb.setConstraints(lineThicknessPalette, gbc);
    toolbar.add(lineThicknessPalette);

    canvas.addPalettes(toolPalette, colorPalette, chooser, lineThicknessPalette);

    return toolbar;
  }

  public static void saveDrawing(DrawCanvas canvas){

    JFileChooser fc = new JFileChooser();
    int option = fc.showSaveDialog(canvas);

    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      System.out.println("Save as: " + file.getAbsolutePath());

      try {

        //Open file
        FileOutputStream fileOut = new FileOutputStream(file.getPath());
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        //Write shapes to output
        objOut.writeObject(canvas.shapes);
      }
      catch(Exception exc){
        System.out.println(exc);
        System.out.println("Cannot write to file");
      }
    }
  }

  public static void loadDrawing(DrawCanvas canvas){

    JFileChooser fc = new JFileChooser();
    int option = fc.showOpenDialog(canvas);

    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      System.out.println("Load from: " + file.getAbsolutePath());

      try {

        //Open file
        FileInputStream fileIn = new FileInputStream(file.getPath());
        ObjectInputStream objIn = new ObjectInputStream(fileIn);

        //Read file and create shapes
        ArrayList<DrawShape> s = (ArrayList<DrawShape>) objIn.readObject();
        canvas.shapes = s;
      }
      catch(Exception exc){
        System.out.println(exc);
        System.out.println("Cannot read file");
      }
    }
    canvas.clean();
    canvas.repaint();
  }

  public static class TransferableImg implements Transferable, ClipboardOwner {

    Image image;

    public TransferableImg(Image i) { image = i; }

    @Override
    public Object getTransferData(DataFlavor df){ return image; }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] df = new DataFlavor[1];
      df[0] = DataFlavor.imageFlavor;
      return df;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor df) { return true; }

    @Override
    public void lostOwnership(Clipboard cp, Transferable t) {}
  }
}