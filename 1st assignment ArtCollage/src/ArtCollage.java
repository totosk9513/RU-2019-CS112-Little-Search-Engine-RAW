/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author:
 *
 *************************************************************************/

import java.awt.Color;
import java.util.Scanner;
public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
   
  
    private Picture Scaler(Picture source, int w, int h) //this initialize the constructor as black first
    {
        Picture scaled = new Picture(w , h);
        for (int tcol = 0; tcol < w; tcol++)
        {
        	//System.out.println(tcol); //debugger
            for(int trow = 0; trow < h; trow++)
            {	
            	
                int scol = tcol * source.width() / w;
               // System.out.print(trow + " "); //debugger
                int srow = trow * source.height() / h;
                Color colr = source.get(scol, srow);
                scaled.set(tcol, trow, colr);   
               
            }
        }
        return scaled;
    }
    
    private Picture Scaler2(Picture source, int w, int h) //this initialize the constructor as black first
    {
        Picture scaled = new Picture(w , h);
        int scol = 0;
        int srow = 0;
        for (int tcol = 0; tcol < w; tcol++)
        {
        	//System.out.println(tcol); //debugger
            for(int trow = 0; trow < h; trow++)
            {	
            	if ((tcol * source.width() / w) < w && (trow * source.height() / h) < h)
            	{
            		 scol = tcol * source.width() / w;
            		 srow = trow * source.height() / h;
                    Color colr = source.get(scol, srow);
                     scaled.set(tcol, trow, colr); 
            	}
              //  System.out.print(trow + " "); //debugger
  
               
            }
        }
        return scaled;
    }
    
   
    public ArtCollage (String filename) 
    {
    	
        collageDimension = 4; 					//1.
        tileDimension = 100; 					//1.
        original = new Picture(filename);		//2.
        //"Update collage to be a scaled version of original HERE"
        collage = Scaler(original, tileDimension*collageDimension, tileDimension*collageDimension);
        
        
	    // WRITE YOUR CODE HERE
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) 
    {
    	collageDimension = cd;
        tileDimension = td;
        original = new Picture(filename);
        collage = Scaler(original, tileDimension*collageDimension, tileDimension*collageDimension);
        //"Update collage to be a scaled version of original HERE
              
	    // WRITE YOUR CODE HERE
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() 
    {
    	return collageDimension;
	    // WRITE YOUR CODE HERE
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() 
    {
    	return tileDimension;
	    // WRITE YOUR CODE HERE
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() 
    {
    	return original;
	    // WRITE YOUR CODE HERE
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() 
    {
    	return collage;
	    // WRITE YOUR CODE HERE
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() 
    {
    	original.show();
	    // WRITE YOUR CODE HERE
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() 
    {
    	collage.show();
	    // WRITE YOUR CODE HERE
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) 
    {
    	Picture replacer = new Picture(filename);
    	Picture scaleRp = Scaler(replacer, tileDimension, tileDimension);
    	int rpTileRow = 0;
    	int rpTileCol = 0;
    	
    	
    	for (int row = ((collageRow) * tileDimension); row < (collageRow +1) * tileDimension; row++)
    	{
    		if (rpTileRow >= tileDimension)
			{
				rpTileRow = 0;
			}
    		for (int col =((collageCol) * tileDimension); col < (collageCol +1) * tileDimension; col++)
    		{
    			if (rpTileCol >= tileDimension)
    			{
    				rpTileCol = 0;
    			}
    			Color rpClr = scaleRp.get(rpTileCol , rpTileRow); 
    			collage.set(col, row, rpClr);
    			//Color gray = Luminance.toGray(color);
    			//collage.set(col, row, gray);
    			rpTileCol++;
    		}
    		rpTileRow++;
    	}
	    // WRITE YOUR CODE HERE
    	 
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () 
    {
    	Picture tile = Scaler(original, tileDimension, tileDimension); //scale down the original picture to fit tiledimension
    	int tilec = 0;
    	int tiler = 0;
    	//방법 1. 4중for루프, collage디멘션 2번 안에 tiledimension 2번
    	/*for (int cr = 0; cr < collageDimension; cr++)
    	{
    		for(int cc = cr; cc < collageDimension; cc++)
    		{
    			for(int tr = cr ; tr < tileDimension; tr++)
    			{
    				for(int tc = tr; tc <tileDimension; tc++)
    				{
    					collage.setRGB(tc, tr, tile.getRGB(tc, tr));
    				}
    			}
    		}
    	}
    	*/
    	//방법 2. tiledimension * collagedimension = width and height and we reinitialize tile's row and col back to 0 when they reach to Max length of them.
    	for (int row = 0; row < tileDimension * collageDimension; row++) //row
    	{
    		if (tiler >= tileDimension)
			{
				tiler = 0; //re initialize back to the first one
			}
    		for (int col = 0; col < tileDimension * collageDimension; col++) //col
    		{
  
    			if (tilec >= tileDimension)
    			{
    				tilec = 0; //re initialize back to the first one
    			}
    			collage.setRGB(col, row, tile.getRGB(tilec, tiler));
    			tilec++;
    		}
    		tiler++;
    	}
    
	    // WRITE YOUR CODE HERE
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) //need to make (xxx,0,0) when component is red
    {
    	Color color;
		Color t;
    	for (int row = ((collageRow) * tileDimension); row < (collageRow +1) * tileDimension; row++)
    	{
    		for (int col =((collageCol) * tileDimension); col < (collageCol +1) * tileDimension; col++)
    		{
    			color = collage.get(col,row);
    			{
    				if (component.equals("red"))  
    				{
    					t = new Color(color.getRed(),0,0);
    					collage.set(col, row, t);
    				}
    				else if (component.equals("blue"))
    				{
    					t = new Color(0,0,color.getBlue());
    					collage.set(col, row, t);
    				}
    				else if (component.equals("green"))
    				{
    					t = new Color(0,color.getGreen(),0);
    					collage.set(col, row, t);
    				}
    				else System.out.println("Please enter only one of red, blue, or green. no cap");
 
    					
    			}
    			//Color gray = Luminance.toGray(color);
    			//collage.set(col, row, gray);
    			//collage.setRGB(collageRow, row, col);
    		}
    	}
    	

	    // WRITE YOUR CODE HERE
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) 
    {
    	for (int row = ((collageRow) * tileDimension); row < (collageRow + 1) * tileDimension; row++)
    	{
    		for (int col =((collageCol) * tileDimension); col < (collageCol + 1) * tileDimension; col++)
    		{
    			Color color = collage.get(col,row);
    			Color gray = Luminance.toGray(color);
    			collage.set(col, row, gray);
    		}
    	}

	    // WRITE YOUR CODE HERE
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
  /*  private static String collageInitializer (int i) // MUST NULLIFY THIS METHOD BEFORE SUBMIT THIS. This has static, this can impact on grading on Autolab 
    {
	    	if (i == 1)
	    	{
	    		return("Ariel.jpg");
	    		
	    	}
	    	else if (i == 2)
	    	{
	    		return("Baloo.jpeg");
	    	
	    	}
	    	else if (i == 3)
	    	{
	    		return("Flo.jpg");
	    
	    	}
	    	else if (i == 4)
	    	{
	    		return("Flo2.jpeg");
	    	
	    	}
	    	else 
	    	{
	    		return("PlocLilo.jpg");
	    		
	    	}
	   
    		
    }
    */
    
    public static void main (String[] args) 
    {
    	String selectedFile;
    	Scanner stdln = new Scanner(System.in);
    	int selected = 0;
    	//ArtCollage art;
    	ArtCollage art = new ArtCollage("Ariel.jpg", 200, 2);
    
        //ArtCollage art = new ArtCollage("Ariel.jpg"); 
        System.out.println("User Interface by Minseok Park");
        System.out.println("Welcome to the Collage world!");
     //   System.out.println("Press these number below to choose your cat to initialize your collage");
       // System.out.println("1: Ariel   2:Baloo   3:Flo1   4:Flo2   5:Ploc&Lilo");
        
      /*  do
        {
        	selected = stdln.nextInt();
        	art = new ArtCollage(collageInitializer(selected), 200, 4);
        }
        while(selected < 0 && selected > 5);
        */
        	
       // System.out.println("Here is your initialized picture");
      //  art.showCollagePicture();
       System.out.println("Here is your collage");
       art.makeCollage();
       
       art.replaceTile("Baloo.jpeg", 0, 1);
       art.replaceTile("Flo.jpg", 1, 0);
       art.replaceTile("Flo2.jpeg", 1, 1);
       art.colorizeTile("green", 0, 0);
       art.showCollagePicture();
       
       /*
       art.replaceTile("Ariel.jpg", 2, 2);
       art.grayscaleTile(1, 2);
       art.replaceTile("Flo2.jpeg", 1, 1);
       art.colorizeTile("blue", 1, 1);
       art.grayscaleTile(0, 1);
       art.replaceTile("weird.jpg", 3, 3);
       art.replaceTile("8282.PNG", 3, 0);
       art.colorizeTile("green", 3, 0);
       art.replaceTile("busch.jpg", 2, 0);
       art.showCollagePicture();
       */

       //art.grayscaleTile(4, 1);
       //art.colorizeTile("red", 4, 1);
       //art.showCollagePicture();
        
       /* while (selected < 0 && selected > 6)
        {
        	selected = stdln.nextInt();
        	collageInitializer(selected);
        }
        System.out.println("Your intialized collage is");
        */
       // art.showCollagePicture();
    }
}
