package mma7;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FeedForward 
{
    
   FeedForward()
   {
        
   }
   
   
    ///Data///
    private  ArrayList<Integer>image=new ArrayList<>();
    private  ArrayList<Integer> quantizer=new ArrayList<>();
    private  ArrayList<Integer> code=new ArrayList<>();
    private  image img =new image();
     
    private void getImageinList(String path)
    {
        img.getimage(path);        
        for(int i=0;i<img.getHeight();++i){
            for(int j=0;j<img.getWidth();++j)
            {
                image.add(img.getPixel(i, j));
            }
        }
    }
    
    
    
    public void buildQuantizer(int min,int max,int levels)
    {
        int step=(max-min)/levels;
        
        quantizer.add(min-1);
        for(int i=0;i<levels-1;++i)
        {
           quantizer.add(min+step);
           min+=step;
        }
        quantizer.add(max+1);
     
        System.out.println("Quantizier: "+quantizer);
    }
    
    
    
    public int getQ(int num)
    {
       for(int i=0;i<quantizer.size();++i){
           if(num<quantizer.get(i))
               return i-1;
       }
      return -1;
    }
    
    
    public int getQ_1(int ind)
    {
      return (quantizer.get(ind+1)+quantizer.get(ind))/2;
    }
    
    
    private void writeMyCode(int levels)
    {
        int min=256;
        int max=-256;
        
         for(int i=1;i<image.size();++i)
         {
            int diff=image.get(i)-image.get(i-1);
            
                    if(diff>max)
                    {
                        max=diff;
                       System.out.println("max"+diff);
                    }
                    if(diff<min)
                    {
                        min=diff;
                       System.out.println("min"+diff);
                    }
         }
         
          buildQuantizer(min,max,levels);
          
          code.add(image.get(0));
          
          for(int i=1;i<image.size();++i)
          {
             code.add(getQ(image.get(i)-image.get(i-1))); 
          }
    }
    
    
    
    private void writeImage()
    {
        int ind=0,value=0;
         for(int i=0;i<img.getHeight();++i){
            for(int j=0;j<img.getWidth();++j){
               
                if(i==0&&j==0)
                {
                    img.setPixel(i, j,code.get(ind));
                    value=code.get(ind);
                }
                else
                {
                    img.setPixel(i, j,value+getQ_1(code.get(ind)));
                    value+=getQ_1(code.get(ind));
                }
                //System.out.println("Value: "+value);
                if(value>255)
                     value=125;
                else if(value<0)
                     value=125;
                
                ++ind;
            }
        }
    }
    

    private void printDataOnFile(String name) {
		try {
	        BufferedWriter fw= new BufferedWriter(new FileWriter(name));
                        Integer siz= quantizer.size();
                        fw.write(siz.toString()+" ");
			for(Integer v : quantizer)
                                             {
                                   fw.write(v.toString()+" ");    
			}
                        for(Integer i : code)
                         fw.write(i.toString()+" ");  
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
    
    
    
    private void readDataFromFile(String name) 
    {
              quantizer.clear();
              code.clear();
		try {
		Scanner s=new Scanner (new File(name));
                        int siz=s.nextInt();
                        for(int k=0;k<siz;++k){
                          int v=s.nextInt();
                          quantizer.add(v);
                        }
                        while(s.hasNextInt())
                            code.add(s.nextInt());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
}
    
    
    public  void compress(String path,int levels)
    {
          getImageinList(path);
          
          writeMyCode(levels);
          
          printDataOnFile("C:\\Users\\hp\\Documents\\NetBeansProjects\\FFDPCM\\FFDPCM\\File.txt");
    }
   
    public void decompress()
    {
    
    readDataFromFile("C:\\Users\\hp\\Documents\\NetBeansProjects\\FFDPCM\\FFDPCM\\File.txt");
    
    writeImage();
    
    img.writeimage();
    }
    
}
