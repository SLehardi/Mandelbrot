package Mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

class ColorList {

  // 'colList' is the color list we are going to fill and keep. Because it is 
  // public, if someone wants a color list (of 256 random colors), they just do:
  // ColorList colors = new ColorList(); // 'colors' will now contain 256 random colors!
  // To access a specific color (eg. the color at index 12), the user just does:
  // Color aColor = colors.colList.get(12); // .get() is a method of ArrayList
  public ArrayList<java.awt.Color>colList = new ArrayList<java.awt.Color>();


  ColorList() {
    Random rng = new Random(); // rand num generator 

    for(int i=0; i<256; i++){ 
    //loop 256 times
      //creates random integers for r,g,b values from 0-255
      int r =rng.nextInt(256);
      int g =rng.nextInt(256);
      int b =rng.nextInt(256);
      //creates a new color with corresponding rgb values
      Color c= new Color(r,g,b);
      //adds the color to the array
      colList.add(c);
    }
    
  }// constructor 
                  
}// ColorList


 