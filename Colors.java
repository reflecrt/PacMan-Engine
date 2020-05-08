import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Colors
{
	private ArrayList<Colour> colorsList = new ArrayList<Colour>();
	private String[] splitString;

	public Colors(File coloursFile)
	{
		try
		{
			Scanner scanner = new Scanner(coloursFile);
			String line = scanner.nextLine();
			while(scanner.hasNextLine())
			{
				//Read each line between spaces and create a tile.
                if(!line.startsWith("//"))
                {
                	splitString = line.split(",");
                	Colour color = new Colour(splitString[0],splitString[1]);
                	colorsList.add(color);
                }
                line = scanner.nextLine();
                }
			}
		
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public Color getColor(String name)
	{
		boolean found = false;
		for(int i = 0; i < colorsList.size(); i++)
		{
			if(colorsList.get(i).name.equalsIgnoreCase(name))
			{
				found = true;
				return colorsList.get(i).color;
			}
		}

		if(!found)
			System.out.println("Color: " + name + " does not exist.");
				
		return null;
	}

	public Color getColor(int colorID)
	{
		if(colorID < colorsList.size())
			return colorsList.get(colorID).color;
		else
			System.out.println("colorID: " + colorID + " is out of bounds by: " + (colorID - colorsList.size()));

		return null;
	}

	class Colour
	{
		public String name;
		public Color color; 

		public Colour(String name, String hex)
		{
			this.name = name;
			color = Color.decode(hex);
		}  
	}
}
