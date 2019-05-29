import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
   A picture whose pixels can be read and written.
*/
public class Picture
{ 
   private String source;
   private JFrame frame;
   private JLabel label;
   private BufferedImage image;

   /**
      Constructs a picture with no image.
 * @param  
   */
   public Picture()
   {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      label = new JLabel("(No image)");
      frame.add(label);
      frame.pack();
      frame.setVisible(true);      
   }

   /**
      Gets the width of this picture.
      @return the width
   */ 
   public int getWidth() { return image.getWidth(); }
   
   /**
      Gets the height of this picture.
      @return the height
   */ 
   public int getHeight() { return image.getHeight(); }
   
   /**
      Loads a picture from a given source. 
      @param source the image source. If the source starts
      with http://, it is a URL, otherwise, a filename.
   */ 
   public void load(String source)
   {
      try 
      {
         this.source = source;
         BufferedImage img;
         if (source.startsWith("http://"))
            img = ImageIO.read(new URL(source).openStream());
         else
            img = ImageIO.read(new File(source));

         setImage(img);        
      }
      catch (Exception ex)
      {
         this.source = null;
         ex.printStackTrace();
      }
   }      

   /**
      Reloads this picture, undoing any manipulations.
   */ 
   public void reload()
   {
      load(source);
   }
   
   /**
      Displays a file chooser for picking a picture.
   */ 
   public void pick()
   {
      JFileChooser chooser = new JFileChooser(".");
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      {
         load(chooser.getSelectedFile().getAbsolutePath());
      }
   }   

   private void setImage(BufferedImage image)
   {
      this.image = image;
      label.setIcon(new ImageIcon(image));
      label.setText(" ");
      frame.pack();
   }

   /**
      Gets the color of a pixel.
      @param x the column index (between 0 and getWidth() - 1)
      @param y the row index (between 0 and getHeight() - 1)
      @return the color of the pixel at position (x, y)
   */ 
   public Color getColorAt(int x, int y)
   {
      Raster raster = image.getRaster();
      ColorModel model = image.getColorModel();
      int argb = model.getRGB(raster.getDataElements(x, y, null));
      return new Color(argb, true);
   }

   /**
      Sets the color of a pixel.
      @param x the column index (between 0 and getWidth() - 1)
      @param y the row index (between 0 and getHeight() - 1)
      @param c the color for the pixel at position (x, y)
   */
   public void setColorAt(int x, int y, Color c)
   {
      WritableRaster raster = image.getRaster();
      ColorModel model = image.getColorModel();
      Object colorData = model.getDataElements(c.getRGB(), null);
      raster.setDataElements(x, y, colorData);
      label.repaint();
   } 
   
   public void printPixelRGB(int pixel) {
	    //int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    System.out.println("rgb: " + red + ", " + green + ", " + blue);
	  }
   
   public void printPixelBrightness(int pixel) {
	    System.out.println("brightness: " + pixel);
	  }
   
   public int getRed(int pixel) {
	   int red = (pixel >> 16) & 0xff;
	   return red;
   }
   
   public int getGreen(int pixel) {
	   int green = (pixel >> 8) & 0xff;
	   return green;
   }
   
   public int getBlue(int pixel) {
	   int blue = pixel & 0xff;
	   return blue;
   }
   
   public int[][] convertTo2D() {
	      int width = image.getWidth();
	      int height = image.getHeight();
	      int[][] result = new int[height][width];
	      System.out.println("Pixel matrix size: " + width + "x" + height + ". Now iterating through contents.");

	      for (int row = 0; row < height; row++) {
	         for (int col = 0; col < width; col++) {
	            result[row][col] = image.getRGB(col, row);
	            //printPixelRGB(result[row][col]);
	         }
	      }

	      return result;
	   }
   
   public int[][] brightnessMatrix(int[][] d2Array){
	   int width = image.getWidth();
	   int height = image.getHeight();
	   int red;
	   int blue;
	   int green;
	   int brightness;
	   int[][] result = new int[height][width];
	   
	   for(int row = 0; row < height; row++) {
		   for(int col = 0; col < width; col++) {
			   red = getRed(d2Array[row][col]);
			   blue = getBlue(d2Array[row][col]);
			   green = getBlue(d2Array[row][col]);
			   brightness = (red + blue + green)/3;
			   result[row][col] = brightness;
			   //printPixelBrightness(result[row][col]);
		   }
	   }
	   
	   return result;
   }
   
   //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
   public char correspondingChar(int pixel) {
	   char ascii = 'e';
	   if (pixel >= 0 && pixel <= 4) {
		   ascii = '`';
	   }
	   else if (pixel > 4 && pixel <= 8) {
		   ascii = '^';
	   }
	   else if(pixel > 8 && pixel <= 12) {
		   ascii = '.';
	   }
	   else if(pixel > 12 && pixel <= 16) {
		   ascii = ',';
	   }
	   else if(pixel > 16 && pixel <= 20) {
		   ascii = ':';
	   }
	   else if(pixel > 20 && pixel <= 24) {
		   ascii = ';';
	   }
	   else if(pixel > 24 && pixel <= 28) {
		   ascii = 'I';
	   }
	   else if(pixel > 28 && pixel <= 32) {
		   ascii = 'l';
	   }
	   else if(pixel > 32 && pixel <= 36) {
		   ascii = '!';
	   }
	   else if(pixel > 36 && pixel <= 40) {
		   ascii = 'i';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 40 && pixel <= 44) {
		   ascii = '~';
	   }
	   else if(pixel > 44 && pixel <= 48) {
		   ascii = '+';
	   }
	   else if(pixel > 48 && pixel <= 52) {
		   ascii = '=';
	   }
	   else if(pixel > 52 && pixel <= 56) {
		   ascii = '_';
	   }
	   else if(pixel > 56 && pixel <= 60) {
		   ascii = '-';
	   }
	   else if(pixel > 60 && pixel <= 64) {
		   ascii = '?';
	   }
	   else if(pixel > 64 && pixel <= 68) {
		   ascii = ']';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 68 && pixel <= 72) {
		   ascii = '[';
	   }
	   else if(pixel > 72 && pixel <= 76) {
		   ascii = '}';
	   }
	   else if(pixel > 76 && pixel <= 80) {
		   ascii = '{';
	   }
	   else if(pixel > 80 && pixel <= 84) {
		   ascii = '1';
	   }
	   else if(pixel > 84 && pixel <= 88) {
		   ascii = ')';
	   }
	   else if(pixel > 88 && pixel <= 92) {
		   ascii = '(';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 92 && pixel <= 96) {
		   ascii = '|';
	   }
	   else if(pixel > 96 && pixel <= 100) {
		   ascii = '/';
	   }
	   else if(pixel > 100 && pixel <= 104) {
		   ascii = 't';
	   }
	   else if(pixel > 104 && pixel <= 108) {
		   ascii = 'f';
	   }
	   else if(pixel > 108 && pixel <= 112) {
		   ascii = 'j';
	   }
	   else if(pixel > 112 && pixel <= 116) {
		   ascii = 'r';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 116 && pixel <= 120) {
		   ascii = 'x';
	   }
	   else if(pixel > 120 && pixel <= 124) {
		   ascii = 'n';
	   }
	   else if(pixel > 124 && pixel <= 128) {
		   ascii = 'u';
	   }
	   else if(pixel > 128 && pixel <= 132) {
		   ascii = 'v';
	   }
	   else if(pixel > 132 && pixel <= 136) {
		   ascii = 'c';
	   }
	   else if(pixel > 136 && pixel <= 140) {
		   ascii = 'z';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 140 && pixel <= 144) {
		   ascii = 'X';
	   }
	   else if(pixel > 144 && pixel <= 148) {
		   ascii = 'Y';
	   }
	   else if(pixel > 148 && pixel <= 152) {
		   ascii = 'U';
	   }
	   else if(pixel > 152 && pixel <= 156) {
		   ascii = 'J';
	   }
	   else if(pixel > 156 && pixel <= 160) {
		   ascii = 'C';
	   }
	   else if(pixel > 160 && pixel <= 164) {
		   ascii = 'L';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 164 && pixel <= 168) {
		   ascii = 'Q';
	   }
	   else if(pixel > 168 && pixel <= 172) {
		   ascii = '0';
	   }
	   else if(pixel > 172 && pixel <= 176) {
		   ascii = 'O';
	   }
	   else if(pixel > 176 && pixel <= 180) {
		   ascii = 'Z';
	   }
	   else if(pixel > 180 && pixel <= 184) {
		   ascii = 'm';
	   }
	   else if(pixel > 184 && pixel <= 188) {
		   ascii = 'w';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 188 && pixel <= 192) {
		   ascii = 'q';
	   }
	   else if(pixel > 192 && pixel <= 196) {
		   ascii = 'p';
	   }
	   else if(pixel > 196 && pixel <= 200) {
		   ascii = 'd';
	   }
	   else if(pixel > 200 && pixel <= 204) {
		   ascii = 'b';
	   }
	   else if(pixel > 204 && pixel <= 208) {
		   ascii = 'k';
	   }
	   else if(pixel > 208 && pixel <= 212) {
		   ascii = 'h';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 212 && pixel <= 216) {
		   ascii = 'a';
	   }
	   else if(pixel > 216 && pixel <= 220) {
		   ascii = 'o';
	   }
	   else if(pixel > 220 && pixel <= 224) {
		   ascii = '*';
	   }
	   else if(pixel > 224 && pixel <= 228) {
		   ascii = '#';
	   }
	   else if(pixel > 228 && pixel <= 232) {
		   ascii = 'M';
	   }
	   else if(pixel > 232 && pixel <= 236) {
		   ascii = 'W';
	   }
	 //`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$
	   else if(pixel > 236 && pixel <= 240) {
		   ascii = '&';
	   }
	   else if(pixel > 240 && pixel <= 244) {
		   ascii = '8';
	   }
	   else if(pixel > 244 && pixel <= 246) {
		   ascii = '%';
	   }
	   else if(pixel > 246 && pixel <= 248) {
		   ascii = 'B';
	   }
	   else if(pixel > 248 && pixel <= 250) {
		   ascii = '@';
	   }
	   else if(pixel > 250 && pixel <= 260) {
		   ascii = '$';
	   }
	   return ascii;
   }
   
   public char[][] asciiMatrix(int[][] brightness){
	   int width = image.getWidth();
	   int height = image.getHeight();
	   int pixel;
	   char asciiSymb;
	   char[][] result = new char[height][width];
	   
	   for (int row = 0; row < height; row++) {
		   for(int col = 0; col < width; col++) {
			   pixel = brightness[row][col];
			   asciiSymb = correspondingChar(pixel);
			   result[row][col] = asciiSymb;
		   }
	   }
	   
	   
	   return result;
   }
   
   public void printAscii(char[][] ascii) throws FileNotFoundException{
	   int width = image.getWidth();
	   int height = image.getHeight();
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