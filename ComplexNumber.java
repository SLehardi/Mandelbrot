package Mandelbrot;


public class ComplexNumber {
  //members for ComplexNumber
  public double real;
  public double imag;
  
  //initializing variables
  public ComplexNumber(double i, double j) 
  {
    real = i;
    imag = j;
  }
  
  //method that adds a ComplexNumber to another ComplexNumber
  public ComplexNumber add(ComplexNumber c2)
  {
    ComplexNumber c1=this;
    
    double i=c1.real+c2.real;
    double j=c1.imag+c2.imag;
    
    return new ComplexNumber(i,j);
  }
  
  //method that squares a certain complex number
   public ComplexNumber squared()
   {
    
    double i=(real*real)-(imag*imag);
    double j=2*real*imag;
    
    return new ComplexNumber(i,j);
  } 
    
  //method getting the magnitude of a complex number
  public double abs()
  {
    double absolute= Math.sqrt((real*real)+(imag*imag));
    
    return absolute;
  } 
    
/* Following code is used to test methods above
   public String toString() 
   {
       if (imag <  0)
        {
          return real + " - " + (-imag) + "i";
        }
        else{
          return real + " + " + imag + "i";
        }
    } 
  public static void main(String[] args) {
    
    ComplexNumber c1= new ComplexNumber(3,4);
    ComplexNumber c2= new ComplexNumber(-2,1);
   
    System.out.println(c1.add(c2));
    System.out.println(c2.squared());
    System.out.println(c2.abs());
     
  }// main()
*/     
}// ComplexNumber
