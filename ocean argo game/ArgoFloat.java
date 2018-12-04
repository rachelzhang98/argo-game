import java.io.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.*;


public class ArgoFloat {
  private String ArgoNum;
  private ImageIcon TempImage;
  private ImageIcon SalImage;
  private double latitude;
  private double longitude;
  
  public ArgoFloat(String num){
    ArgoNum = num;
    try{
      /*GET TEMPERATURE IMAGE**************************/
      URL url = new URL("http://www.ifremer.fr/co/co0533/co053301/"+ArgoNum+"_sec_temp.png");
      BufferedImage c = ImageIO.read(url);
      c = coverNumber(c); 
      TempImage = new ImageIcon(c);
      /*GET SALINITY IMAGE*********************/
      url = new URL("http://www.ifremer.fr/co/co0533/co053301/"+ArgoNum+"_sec_psal.png");
      c = ImageIO.read(url);
      c = coverNumber(c); 
      SalImage = new ImageIcon(c);
      
      
      //get lat-long ****
      boolean noMatch = true;
      url = new URL("ftp://ftp.ifremer.fr/ifremer/argo/ar_index_global_traj.txt");
      Scanner s = new Scanner(url.openStream());
      String save = "";
      while (s.hasNext() && noMatch){
        String line = s.nextLine();
        if (line.indexOf("/")>-1){
          String profile = line.substring(line.indexOf("/")+1,line.indexOf("/", line.indexOf("/") + 1));
          if (profile.equals(ArgoNum)) {
            noMatch = false;
            save = line;
          } 
        }
        
      }
      
      String[] latlongList = save.split(",");
      latitude = Math.round((Double.parseDouble(latlongList[1]) + Double.parseDouble(latlongList[2]))/2);
      longitude = Math.round((Double.parseDouble(latlongList[3]) + Double.parseDouble(latlongList[4]))/2);
    }
    
    catch(IOException e) {
      System.out.println(e);
    }
  }
  
  public BufferedImage coverNumber(BufferedImage c){
    int Xstart = 220 - (ArgoNum.length() - 7)*5;
    int Xlength = (ArgoNum.length()*7)+45;
    for (int x = Xstart; x < 220+Xlength; x++)
      for (int y = 34; y < 59; y++)
      c.setRGB(x,y,16711679); //white. try 16711679?
    return c;
  }
  
  public ImageIcon getTempImage(){
    return TempImage;
  }
  
  public ImageIcon getSalImage(){
    return SalImage;
  }
  
  public double getLat(){
    return latitude;
  }
  
  public double getLong(){
    return longitude;
  }
  
  public String getArgoNum(){
    return ArgoNum;
  } 
}

