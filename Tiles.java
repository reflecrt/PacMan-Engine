import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Color;
import java.awt.Graphics;

public class Tiles
{
	private ArrayList<Tile> tilesList = new ArrayList<Tile>();
	private ArrayList<String> lines = new ArrayList<String>();
	private Colors colours;
	private File tilesFile;

	public Tiles(File tilesFile, Colors colours)
	{
		this.tilesFile = tilesFile;
		this.colours = colours; 
		try
		{
			Scanner scanner = new Scanner(tilesFile);
			while(scanner.hasNextLine())
			{
				//Read each line between spaces and create a tile.
                String line = scanner.nextLine();
                if(!line.startsWith("//"))
                {
                	if(!line.startsWith(" "))
                	{
                		while(!line.startsWith(" "))
                		{
                			lines.add(line);
                			if(scanner.hasNextLine())
                				line = scanner.nextLine();
                			else
                				break;
                		}
                	}

                	//MAKE SURE TO END THE FILE WITH A SPACE ON A NEW LINE.
                	if(line.startsWith(" "))
                	{
                		Tile tile = new Tile(lines);
                		tilesList.add(tile);
                		lines = new ArrayList<String>();
                	}
                }
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	    
	}

	public void renderTile(int tileID, RenderAll renderer, int xPosition, int yPosition)
	{
	    for(int i = 0; i < tilesList.get(tileID).names.length; i++)
	    {
		    renderer.renderPart(tilesList.get(tileID).names[i], xPosition, yPosition,
				                tilesList.get(tileID).parts[i], tilesList.get(tileID).colors[i]);
	    }
	}

	public void renderTile(int tileID, RenderAll renderer, int xPosition, int yPosition, int length, int breadth, String scaling)
	{
		Rectangle[] tempParts = new Rectangle[tilesList.get(tileID).parts.length];
		for(int i = 0; i < tempParts.length; i++)
		{
			tempParts[i] = new Rectangle();
			tempParts[i].equalsTo(tilesList.get(tileID).parts[i]);
		}

		makeSize(length, breadth, tempParts, 0, 0, scaling);

	    for(int i = 0; i < tilesList.get(tileID).names.length; i++)
	    {
		    renderer.renderPart(tilesList.get(tileID).names[i], xPosition, yPosition,
				                tempParts[i], tilesList.get(tileID).colors[i]);
	    }
	}

	public void renderTile(int tileID, RenderAll renderer, int xPosition, int yPosition, Rectangle[] parts)
	{
	    for(int i = 0; i < tilesList.get(tileID).names.length; i++)
	    {
		    renderer.renderPart(tilesList.get(tileID).names[i], xPosition, yPosition,
				                parts[i], tilesList.get(tileID).colors[i]);
	    }
	}

	public void makeSize(int length, int breadth, Rectangle[] parts, int x, int y, String scaling)
	{
	   int X = 0, Y = 0, i;
       double factorX, factorY;

       for(i = 0; i < parts.length; i++)
            {
       	        parts[i].x -= x;
       	        parts[i].y -= y;

       	        if(parts[i].x + parts[i].w > X)
       		        X = parts[i].x + parts[i].w;

       	        if(parts[i].y + parts[i].h > Y)
       		        Y = parts[i].y + parts[i].h;
            }
       
       if(scaling.equalsIgnoreCase("DownScale"))
       {
       //System.out.println("Area:" + (X * Y) + " X:" + X + " Y:" + Y );

            if((X * Y) > (length * breadth))
            {
       	        factorX = (double)(length) / X; 
       	        factorY = (double)(breadth) / Y;

       	        for(i = 0; i < parts.length; i++)
       	        {
       	    	    parts[i].x = (int) Math.ceil((double)(parts[i].x) * factorX);
       	    	    parts[i].x += x;
       	    	    parts[i].w = (int) Math.ceil((double)parts[i].w * factorX);
       	    	    parts[i].y = (int) Math.ceil((double)(parts[i].y) * factorY);
       	    	    parts[i].y += y;
       	    	    parts[i].h = (int) Math.ceil((double)parts[i].h * factorY);
       	        }
            }
            else
            {
       	        for(int j = 0; j < parts.length; j++)
       	        {
       		        parts[j].x += x;
       	            parts[j].y += y;       	
       	        }       	
            }
       }
       else if(scaling.equalsIgnoreCase("UpScale"))
       {
       //System.out.println("Area:" + (X * Y) + " X:" + X + " Y:" + Y );

            if((X * Y) < (length * breadth))
            {
       	        factorX = (double)(length) / X; 
       	        factorY = (double)(breadth) / Y;

       	        for(i = 0; i < parts.length; i++)
       	        {
       	    	    parts[i].x = (int) Math.ceil((double)(parts[i].x) * factorX);
       	    	    parts[i].x += x;
       	    	    parts[i].w = (int) Math.ceil((double)parts[i].w * factorX);
       	    	    parts[i].y = (int) Math.ceil((double)(parts[i].y) * factorY);
       	    	    parts[i].y += y;
       	    	    parts[i].h = (int) Math.ceil((double)parts[i].h * factorY);
       	        }
            }
            else
            {
       	        for(int j = 0; j < parts.length; j++)
       	        {
       		    parts[j].x += x;
       	        parts[j].y += y;       	
       	        }       	
            }
       }

	}


	public int size()
	{
		return tilesList.size();
	}

	public Rectangle[] getParts(int tileID)
	{
		return tilesList.get(tileID).parts;
	} 

	class Tile
	{
		public String name;
		public String[] names;
		public Rectangle[] parts;
		public Color[] colors;

		public Tile(ArrayList<String> lines)
		{
			name = lines.get(0);
			names = new String[lines.size() - 1];
			parts = new Rectangle[lines.size() - 1];
			colors = new Color[lines.size() - 1];

            for(int i = 1; i < lines.size(); i++)
            {
                String[] splitString = lines.get(i).split(",");
                names[i - 1] = splitString[0];

                parts[i - 1] = new Rectangle();
                parts[i - 1].x = Integer.parseInt(splitString[1]);
                parts[i - 1].y = Integer.parseInt(splitString[2]);
                parts[i - 1].w = Integer.parseInt(splitString[3]);
                parts[i - 1].h = Integer.parseInt(splitString[4]);

                colors[i - 1] = colours.getColor(splitString[5]);
            }
		}
	}
}