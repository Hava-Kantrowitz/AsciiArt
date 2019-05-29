import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class AsciiArt {
	public static void main(String args[]) throws FileNotFoundException
	 {
	  
		Picture myPicture = new Picture();
		
		
		
		myPicture.load("C:\\Users\\havak\\Downloads\\ascii-pineapple.jpg");
		int width = myPicture.getWidth();
		int height = myPicture.getHeight();
		
		System.out.println("Successfully loaded image!");
		System.out.println("Image size is " + width + "x" + height +".");
		
		int[][] rgbArray = myPicture.convertTo2D();
		System.out.println("Successfully arrayed image!");
		
		int[][] brightnessArray = myPicture.brightnessMatrix(rgbArray);
		System.out.println("Successfully arrayed brightness score!");
		
		char[][] asciiArray = myPicture.asciiMatrix(brightnessArray);
		
		
        printAscii(asciiArray, width, height);
        
}
	public static void printAscii(char[][] ascii, int width, int height) throws FileNotFoundException{
  	   
  	   char letter;
  	   
  	   //System.setOut(new PrintStream(new FileOutputStream("out.txt")));
  	   
  	   for (int row = 0; row < height; row++) {
  		   for(int col = 0; col < width; col++) {
  			   letter = ascii[row][col];
  			   System.out.print(letter);
  		   }
  		   System.out.println();
  	   }
  	   
  	   //return result;
     }
}
