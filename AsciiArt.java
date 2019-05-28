import java.awt.Color;
import java.util.Arrays;

public class AsciiArt {
	public static void main(String args[])
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
		System.out.println(Arrays.deepToString(asciiArray));
}
}
