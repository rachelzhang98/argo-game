import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class GuessTab extends JPanel 
{ 
  private SaveImage i;
  private JLabel imageLabel;

  public GuessTab(SaveImage i) 
  { 
    this.i = i;
//    imageLabel = new JLabel(i.getTempImage());
//    add(imageLabel);
  }
}