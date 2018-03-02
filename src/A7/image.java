package mma7;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
    public class image 
    {
         
  private int img[][]; 
  private  BufferedImage image=null;
  private int width  , height;
  
   public void getimage(String path){
       
        try {
            File i=new File(path);
            image =ImageIO.read(i);
        } catch (IOException ex) {
            Logger.getLogger(image.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        width=image.getWidth();
        height=image.getHeight();
        img=new int[width][height];
        
        for(int i=0;i<height;++i){
            for(int j=0;j<width;++j){
                int pixel=image.getRGB(j,i);
                int a=(pixel>>24)&0xff;
                int r=(pixel>>16)&0xff;
                int g=(pixel>>8)&0xff;
                int b=pixel&0xff;
                img[j][i]=r;
                
            }
        } 
    }
   
   public void writeimage(){
        
    for(int i=0;i<height;++i){
            for(int j=0;j<width;++j){
                int r=img[j][i];
                int b=img[j][i];
                int g=img[j][i];
                int p=(255<<24)|(r<<16)|(g<<8)|b;
                image.setRGB(j, i, p);
            }
         }
        File f=new File("C:\\Users\\hp\\Documents\\NetBeansProjects\\FFDPCM\\FFDPCM\\output.jpg");
        try {
            ImageIO.write(image, "jpg", f);
        } catch (IOException ex) {
            Logger.getLogger(image.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
 
   public void setPixel(int x,int y ,int value){ img[x][y]=value;}
   
   public int  getPixel(int x,int y){return img[x][y];}
   public Integer  getHeight(){return width;}
   public Integer  getWidth(){return height;}
   
}
