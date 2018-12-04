import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ArgoGUI{
  
  
  public static void main(String[] args){
    
    JFrame frame = new JFrame ("Argo Game");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); 
    frame.setPreferredSize(new Dimension(1200, 700));
    frame.pack(); 
    
    Game i = new Game();
    
    JPanel main = new JPanel();
    JPanel InfoPanel = new JPanel();
    JPanel MapPanel = new JPanel();
    JPanel StuffPanel = new JPanel();
    JPanel NumberPanel = new JPanel();
    JPanel LatLongPanel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel TurnPanel = new JPanel();
    
    InfoPanel.setPreferredSize(new Dimension(800,1200));
    StuffPanel.setPreferredSize(new Dimension(380,800));
    MapPanel.setPreferredSize(new Dimension(360,180));
    
    main.setPreferredSize(new Dimension(1600,700));
    main.setLayout(new BorderLayout());
    JLabel tempImage = new JLabel(i.getTempImage());
    JLabel salImage = new JLabel(i.getSalImage());
    JLabel worldMap = new JLabel(i.getMap());
    InfoPanel.add(tempImage);
    InfoPanel.add(salImage);
    
    JScrollPane scroll = new JScrollPane(InfoPanel);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setBounds(0, 0, 800, 600);
    
    StuffPanel.setLayout(new BorderLayout());
    MapPanel.setLayout(new BorderLayout());
    MapPanel.add(worldMap, BorderLayout.WEST);
    JLabel coordLat = new JLabel("Latitude: 0");
    JLabel coordLong = new JLabel("Longitude: 0");
    JLabel tempScore = new JLabel();
    JLabel turnsLeft = new JLabel();
    JLabel scoreLabel = new JLabel("Total Score: 0");
    JLabel actLat = new JLabel();
    JLabel actLong = new JLabel();
    JLabel milesOff = new JLabel();
    JButton guess = new JButton("Guess!");
    JButton newB = new JButton("New");
    guess.addActionListener(new ActionListener(){
      public void actionPerformed (ActionEvent event){
        guess.setVisible(false);
        int turnScore = i.calculate(Double.parseDouble(coordLat.getText().substring(10)),
                                    Double.parseDouble(coordLong.getText().substring(11)));
        
        i.newTurn();
        int turns = i.getTurns();
        turnsLeft.setText("Turns left: " + Integer.toString(turns));
        actLat.setText("Real Latitude: " + (double)(i.getArgo().getLat()));
        actLong.setText("Real Longitude: " + (double)(i.getArgo().getLong()));
        i.markMap();
        worldMap.setIcon(i.getMap());
        milesOff.setText("You were " + i.getKM() + " km off mark!");
        if (turns >= 0) {
          tempScore.setText("Turn Score: "+Integer.toString(turnScore));
          i.increaseScore(turnScore);
          scoreLabel.setText("Total Score: " + Integer.toString(i.getScore()));
        }
        else
        {
          newB.setText("End");
        }
      }
    });
    
    newB.addActionListener(new ActionListener(){
      public void actionPerformed (ActionEvent event){
        if(i.getTurns()>0){
          i.newArgo();
          tempImage.setIcon(i.getTempImage());
          salImage.setIcon(i.getSalImage());
          guess.setVisible(true);
        }
        else{
          frame.setVisible(false);
          frame.dispose();
          JOptionPane.showMessageDialog (null, "Total Score: " + Integer.toString(i.getScore())+ ". Congratulations!");
        }
      }
    });
    ButtonPanel.add(guess);
    ButtonPanel.add(newB);
    
    
    
    MapPanel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me)
      {
        int x;
        if (me.getX() <160)
          x = me.getX()+20;
        else if (me.getX() < 340)
          x = -1*(180 - (me.getX() - 160));
        else
          x = me.getX() - 340;
        int y = -1*(me.getY() - 91);
        coordLat.setText("Latitude: " +y);
        coordLong.setText("Longitude: " + x);
      }
    });
    
    
    TurnPanel.setLayout(new BoxLayout(TurnPanel,BoxLayout.Y_AXIS));
    TurnPanel.add(tempScore);
    TurnPanel.add(actLat);
    TurnPanel.add(actLong);
    TurnPanel.add(milesOff);
    StuffPanel.add(MapPanel, BorderLayout.NORTH);
    NumberPanel.setLayout(new BoxLayout(NumberPanel,BoxLayout.Y_AXIS));
    LatLongPanel.add(coordLat);
    LatLongPanel.add(coordLong);
    NumberPanel.add(LatLongPanel);
    NumberPanel.add(ButtonPanel);
    //NumberPanel.add(turnsLeft);
    NumberPanel.add(TurnPanel);
    NumberPanel.add(scoreLabel);
    StuffPanel.add(NumberPanel, BorderLayout.CENTER);
    
    main.add(scroll,BorderLayout.WEST);
    main.add(StuffPanel,BorderLayout.EAST);
    frame.getContentPane().add(main);
    frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    
    frame.setVisible(true);    
  }
  
  
  
}



