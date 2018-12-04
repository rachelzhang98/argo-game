import java.io.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.*;


public class SaveImage {
  
  private final static int TotalArgos = 14567;
  private String ArgoNum;
  private ImageIcon TempImage;
  private ImageIcon SalImage;
  private ImageIcon WorldMap;
  
  public SaveImage(){
/*pick a random argo number from text file*******************/
    pickRandom();   
    try{
/*GET TEMPERATURE IMAGE**************************/
    URL url = new URL("http://www.ifremer.fr/co/co0533/co053301/"+ArgoNum+"_sec_temp.png");
    BufferedImage c = ImageIO.read(url);
    c = coverNumber(c); //UNCOMMENT LATER
    TempImage = new ImageIcon(c);
/*GET SALINITY IMAGE*********************/
    url = new URL("http://www.ifremer.fr/co/co0533/co053301/"+ArgoNum+"_sec_psal.png");
    c = ImageIO.read(url);
    c = coverNumber(c); //UNCOMMENT LATER
    SalImage = new ImageIcon(c);
    
    File filename = new File("worldmappixel.png");
    c = ImageIO.read(filename);
    WorldMap = new ImageIcon(c);
    }
    catch(IOException e){
      System.out.println(e);
    }
  }
  public ImageIcon getTempImage(){
      return TempImage;
  }
  
  public ImageIcon getSalImage(){
      return SalImage;
  }
  
  public ImageIcon getMap(){
      return WorldMap;
  }
  
  public BufferedImage coverNumber(BufferedImage c){
    int Xstart = 220 - (ArgoNum.length() - 7)*5;
    int Xlength = (ArgoNum.length()*7)+45;
    for (int x = Xstart; x < 220+Xlength; x++)
      for (int y = 34; y < 59; y++)
         c.setRGB(x,y,16711679); //white. try 16711679?
    return c;
  }
  private void pickRandom(){
    Random rand = new Random();
    int index = rand.nextInt(TotalArgos); //all viable, checked edge cases.
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

    
  }
  
  public static void main(String[] args){
    //testing code
    SaveImage i = new SaveImage();
    System.out.println(i.getTempImage());
  }
  
 
}