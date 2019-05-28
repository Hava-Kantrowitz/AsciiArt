import java.awt.Color;
import java.io.File;
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
   
   public char[][] asciiMatrix(int[][] brightness){
	   int width = image.getWidth();
	   int height = image.getHeight();
	   char[][] result = new char[height][width];
	   
	   for (int row = 0; row < height; row++) {
		   for(int col = 0; col < width; col++) {
			   result[row][col] = 'h';
			   //conversion formula here!
		   }
	   }
	   
	   return result;
   }
   
   public void printAscii(char[][] ascii){
	   int width = image.getWidth();
	   int height = image.getHeight();
	   char letter;
	   
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