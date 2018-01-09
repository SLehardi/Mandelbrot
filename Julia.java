package Mandelbrot;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;

//fractal image where c is constant and z changes
public class Julia { 
  
  //creating members for class Julia
  public float xtable[][];
  public int xRes,yRes;
  public double zxMin,zxMax,zyMin,zyMax;
  public ArrayList <java.awt.Color> colors;
  
  //constructor for class julia
  public Julia(int xRes, int yRes, double zxMin,double zxMax,double zyMin,double zyMax) 
  {
    //initializing variables
   this.xRes=xRes; this.yRes=yRes;
   this.zxMin=zxMin; this.zxMax=zxMax; this.zyMin=zyMin; this.zyMax=zyMax;
   xtable=new float [yRes][xRes];
 
   //creating a color list
   ColorList cols = new ColorList();
   colors=cols.colList;
   
   //setting a constant c and z values
   ComplexNumber c = new ComplexNumber(-0.7,0.27015);
   //zxMin=-1.5; zxMax=1.5; zyMin=-1; zyMax=1;
   
   //double loop to fill xtable with initial gray scale values
   for(int y=0; y<yRes;y++) {
     for(int x=0; x<xRes;x++){
       double zreal=zxMin+(double)x/((double)xRes-1.0)*(zxMax-zxMin);
       double zimag=zyMin+(double)y/((double)yRes-1.0)*(zyMax-zyMin);
       ComplexNumber z = new ComplexNumber(zreal,zimag);
       
       
       int maxIter=200;
       int iter=0;
       
       //while loop that runs as long as iter is less than max iter and z magnitude is less than 2
       while(iter<maxIter && z.abs()<2) {
         z=c.add(z.squared());
         iter++;
       }
       
       //if absolute of z is greater than 2 save value as iter, if not set the value as 0
       if(z.abs()>=2) {
         xtable[y][x]=iter;
       }else {
         xtable[y][x]=0;
       }
     }
   }
  }
  
  //method that sets a pixel to a color with rgb values
  public void setPixel(Graphics g, int x, int y, int red, int grn, int blu) {
    Color c = new Color(red,grn,blu);
    g.setColor(c);
    g.drawLine(x,y,x,y);
  }// setPixel()
  
  //method changes values from xtable to color values taken from an index from the colorlist
  public void displayFractal(Graphics g) {
    for(int y=0; y<yRes;y++) {
     for(int x=0; x<xRes;x++){
       Color c=colors.get((int)xtable[y][x]);
       int red= c.getRed();
       int green= c.getGreen();
       int blue= c.getBlue();
       setPixel(g,x,y,red,green,blue);
     }
    }
  }// displayFractal()
   
  //method that saves fractal to ppm 
  public void saveFractal(String fileName) throws IOException {
    File f = new File("C:\\temp\\"+fileName);
    FileOutputStream fout = new FileOutputStream(f);
    PrintStream out = new PrintStream(fout);
    // header
    out.println("P3\r\n" + xRes + " " + yRes + "\r\n200\r\n"); // P2 xres yres maxval
    
    for(int y=0; y<yRes;y++) {
     for(int x=0; x<xRes;x++){
       Color c=colors.get((int)xtable[y][x]);
       int red= c.getRed();
       int green= c.getGreen();
       int blue= c.getBlue();
       out.println(red + " " + green + " " + blue);
    }    
   }
     out.close();
  }// saveFractal()

  // main() function uses jframe, jpanel, bufferedimage, jlabel to displace the fractal image and save it to ppm
  public static void main(String[] args) {
    
    int xResolution=640;
    int yResolution=480;
    Julia jul = new Julia(xResolution,yResolution,-1.5,1.5,-1,1);//creating object
    JFrame frame = new JFrame(); // our outer window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // grid of subcomponents, here, a 1x1 grid (just one subcomponent)
    GridLayout gl = new GridLayout(1,1);
    gl.setVgap(0);gl.setHgap(0);
    frame.setLayout(gl); 

    //creates panel with preffered size
    JPanel jP = new JPanel();
    jP.setPreferredSize(new Dimension(xResolution,yResolution));
    frame.add(jP);
    
    //produces buffered image with a border and size
    BufferedImage img = new BufferedImage(xResolution,yResolution,BufferedImage.TYPE_INT_RGB);
    JLabel jL = new JLabel(new ImageIcon(img));
    jL.setPreferredSize(new Dimension(xResolution,yResolution));
    jL.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jP.add(jL);
    Graphics graphic= img.createGraphics();
    jul.displayFractal(graphic); //calls on display Fracal to show graphic
    
    try{ //saves fractal to ppm file
      jul.saveFractal("Julia.ppm");
    }catch(IOException e){
      e.printStackTrace();
    }                    
    
    frame.setVisible(true); 
    frame.setResizable(false);
    frame.pack(); // arrange all the panels 
                        
  }// main()

  
}// Mandelbrot

