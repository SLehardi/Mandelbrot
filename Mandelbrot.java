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

//fractal imageMandelbrot
public class Mandelbrot {
  
  //creating members for the class
  public float xtable[][];
  public int xRes,yRes;
  public double cxMin,cxMax,cyMin,cyMax;
  public ArrayList <java.awt.Color> colors;
  
  //constructor class 
  public Mandelbrot(int xRes, int yRes, double cxMin,double cxMax,double cyMin,double cyMax) 
  {
   //initializing variables and defining xTable object
   this.xRes=xRes; this.yRes=yRes;
   this.cxMin=cxMin; this.cxMax=cxMax; this.cyMin=cyMin; this.cyMax=cyMax;
   xtable=new float [yRes][xRes];
 
   //getting a list of colors
   ColorList cols = new ColorList();
   colors=cols.colList;
   
    //double loop to fill xtable with initial gray scale values
   for(int y=0; y<yRes;y++) {
     for(int x=0; x<xRes;x++){
       double creal=cxMin+(double)x/((double)xRes-1.0)*(cxMax-cxMin);
       double cimag=cyMin+(double)y/((double)yRes-1.0)*(cyMax-cyMin);
       ComplexNumber c = new ComplexNumber(creal,cimag);
       
       //setting an initial z complex number
       ComplexNumber z = new ComplexNumber(0,0);
       int maxIter=255;
       int iter=0;
       
       //while loop that runs as long as iter is less than maxiter and absolute value of z is less than 2
       while(iter<maxIter && z.abs()<2) {
         z=c.add(z.squared());
         iter++;
       }
       
       //if absolute of z is greater, set iter to xtable else set 0 if loops ends because of iterations
       if(z.abs()>=2) {
         xtable[y][x]=iter;
       }else {
         xtable[y][x]=0;
       }
     }
   }
  }
  
  
  //method that sets a pixel a color
  public void setPixel(Graphics g, int x, int y, int red, int grn, int blu) {
    Color c = new Color(red,grn,blu);
    g.setColor(c);
    g.drawLine(x,y,x,y);
  }// setPixel()
  
  //method that allows the fractal to get displayed by getting an iter value from an index to set pixel at the point
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
   
  //method that saves the fractal to file as ppm file utilzing headers and saving rgb values
  public void saveFractal(String fileName) throws IOException {
    File f = new File("C:\\temp\\"+fileName);
    FileOutputStream fout = new FileOutputStream(f);
    PrintStream out = new PrintStream(fout);
    // header
    out.println("P3\r\n" + xRes + " " + yRes + "\r\n255\r\n"); // P2 xres yres maxval
    
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
    Mandelbrot mand = new Mandelbrot(xResolution,yResolution,-2,1,-1,1);
    JFrame frame = new JFrame(); // our outer window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // grid of subcomponents, here, a 1x1 grid (just one subcomponent)
    GridLayout gl = new GridLayout(1,1);
    gl.setVgap(0);gl.setHgap(0);
    frame.setLayout(gl); 

    //creating panel and frame
    JPanel jP = new JPanel();
    jP.setPreferredSize(new Dimension(xResolution,yResolution));
    frame.add(jP);
    
    BufferedImage img = new BufferedImage(xResolution,yResolution,BufferedImage.TYPE_INT_RGB);
    JLabel jL = new JLabel(new ImageIcon(img));
    jL.setPreferredSize(new Dimension(xResolution,yResolution));
    jL.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jP.add(jL);
    
    //displaying graphic
    Graphics graphic= img.createGraphics();
    mand.displayFractal(graphic);
    
    //saving fractal to file
    try{
      mand.saveFractal("Mandelbrot.ppm");
    }catch(IOException e){
      e.printStackTrace();
    }                    
    
    frame.setVisible(true); // show to the world :) 
    frame.setResizable(false);
    frame.pack(); // arrange all the panels 
                        
  }// main()

  
}// Mandelbrot
