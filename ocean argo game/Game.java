import java.io.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.*;

public class Game {
  
  private final static int TotalArgos = 14567;
  private static final int EARTH_RADIUS = 6371;
  private ArgoFloat currentArgo;
  private ImageIcon WorldMap;
  private int turnsLeft;
  private int score;
  private int lastKmOff;
  
  public Game(){
    try {
      File filename = new File("worldmappixel.png");
      BufferedImage c = ImageIO.read(filename);
      WorldMap = new ImageIcon(c);
    }
    catch(IOException e){
      System.out.println(e);
    }
    turnsLeft = 5;
    score = 0;
    lastKmOff = 0;
    newArgo();
    
  }
  
  public void newTurn(){ 
    turnsLeft--;
  }
  
  public void newArgo(){
    currentArgo = pickRandom(); 
  }
  
  //returns score for the turn
  public int calculate(double glat, double glong){
    double distance = distance(glat, glong, currentArgo.getLat(), currentArgo.getLong());
    lastKmOff = (int)(distance);
    return (int)(5000*Math.pow(0.9995,distance));
  }
  
  
  public ImageIcon getTempImage(){
    return currentArgo.getTempImage();
  }
  
  public ImageIcon getSalImage(){
    return currentArgo.getSalImage();
  }
  
  public ImageIcon getMap(){
    return WorldMap;
  }
  
  public int getScore() {
    return score;
  }
  
  public int getTurns() {
    return turnsLeft;
  }
  
  public ArgoFloat getArgo() {
    return currentArgo;
  }
  public int getKM() {
    return lastKmOff;
  }
  
  public void increaseScore(int increase) {
    score = score + increase;
  }
  
  public void markMap() {
    Image image = WorldMap.getImage();
    BufferedImage buffered = (BufferedImage) image;
    int tempx = (int)(currentArgo.getLong());
    int x;
    if (tempx > 0)
      x = tempx-20;
    else
      x = tempx+340;
    int y = (int)(-1*currentArgo.getLat()+90);
    try{
      for (int i = x-3; i < x+3; i++)
        for (int j = y-3; j < y+3; j++)
        buffered.setRGB(i,j,new Color(255,0,0).getRGB());//red
    }catch(ArrayIndexOutOfBoundsException e) {
      System.out.println(e);
    }
    WorldMap = new ImageIcon(buffered);
  }
  
  private ArgoFloat pickRandom(){
    Random rand = new Random();
    String ArgoNum = "";
    int index = rand.nextInt(TotalArgos); 
    try {
      File f = new File("argonums.txt");
      Scanner fileScan = new Scanner(f);
      for(int i = 0; i < index; i++){
        fileScan.next();}
      
      ArgoNum = fileScan.next();
      System.out.println(ArgoNum);
      
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    return new ArgoFloat(ArgoNum);
  }
  
  
  //https://github.com/jasonwinn/haversine
  //returns the distance in km
  public double distance(double startLat, double startLong,
                         double endLat, double endLong) {
    
    double dLat  = Math.toRadians((endLat - startLat));
    double dLong = Math.toRadians((endLong - startLong));
    
    startLat = Math.toRadians(startLat);
    endLat   = Math.toRadians(endLat);
    
    double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
    return EARTH_RADIUS * c; 
  }
  
  public static double haversin(double val) {
    return Math.pow(Math.sin(val / 2), 2);
  }
  
  public static void main(String[] args){
  }
  
}