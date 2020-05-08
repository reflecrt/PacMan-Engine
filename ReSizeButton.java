import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ReSizeButton extends GUIbutton
{
	private Color color;
	private Rectangle rect;
	private Rectangle[] parts;
	private Rectangle[] tempParts;
	private Rectangle[] originalParts;
	private String whatToChange;
	private MouseEventListener mouseListener;
	private Rectangle tileRectangle;
	private Rectangle originalTileRectangle = new Rectangle();
	private int tileSerial;
	private int[] cursorCoordinates = new int[2];
	private String side = "";

	public ReSizeButton(Rectangle rect, int tileSerial, String whatToChange, GUI gui, Color color)
	{
		super(color, rect);

		this.rect = rect;
		this.tileSerial = tileSerial;

		tileRectangle = gui.getButton(tileSerial).getRect();
		originalTileRectangle.equalsTo(tileRectangle);

		mouseListener = gui.getButton(tileSerial).getMouseListener();

		parts = gui.getButton(tileSerial).getParts();

		this.color = color;

		this.whatToChange = whatToChange;

		tempParts = new Rectangle[parts.length];
		originalParts = new Rectangle[parts.length];
		for(int i = 0; i < parts.length; i++)
		{
			tempParts[i] = new Rectangle();
			originalParts[i] = new Rectangle();

			tempParts[i].equalsTo(parts[i]);
			originalParts[i].equalsTo(parts[i]);
		}

		if(whatToChange.equalsIgnoreCase("X"))
       	    if(Math.abs(tileRectangle.x - rect.x) > Math.abs((tileRectangle.x + tileRectangle.w) - rect.x))
       		    side = "right";
       		else
       			side = "left";

        if(whatToChange.equalsIgnoreCase("Y"))
       	    if(Math.abs(tileRectangle.y - rect.y) > Math.abs((tileRectangle.y + tileRectangle.h) - rect.y))
       		    side = "down";
       		else
       			side = "up";
	}

	public ReSizeButton(Rectangle rect, String whatToChange, GUI gui)
	{
		this(rect, -1, whatToChange, gui, Color.white);
	}

	@Override
	public void render(RenderAll renderer, Rectangle interfaceRectangle)
	{
		renderer.renderRectangle(rect, interfaceRectangle, color);	
	}

    @Override
	public void update(PacMan game)
	{
       if(isWithinTheTile)
       {
       	    if(mouseListener.isDragging())
       	    {
       	    	game.getGUI().getButton(tileSerial).isBeingResized(true);

       	        for(int i = 0; i < parts.length; i++)
		        {
			        tempParts[i].equalsTo(originalParts[i]);
		        }

       	    	String scaling;

       	        if(whatToChange.equalsIgnoreCase("X"))
       	        {
       		        if(side.equals("right"))
       		        {
                        tileRectangle.w += mouseListener.getX() - rect.x - cursorCoordinates[0];
                        tileRectangle.w = Math.abs(tileRectangle.w);
                        rect.x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    }
                    else
                    {
                    	tileRectangle.x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    	tileRectangle.w -= mouseListener.getX() - rect.x - cursorCoordinates[0];
                    	tileRectangle.w = Math.abs(tileRectangle.w);
                        rect.x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    }
       	        }

       	        else if(whatToChange.equalsIgnoreCase("Y"))
       	        {
                    if(side.equals("down"))
       		        {
                        tileRectangle.h += mouseListener.getY() - rect.y - cursorCoordinates[1];
                        tileRectangle.h = Math.abs(tileRectangle.h);
                        rect.y += mouseListener.getY() - rect.y - cursorCoordinates[1];
                    }
                    else
                    {
                    	tileRectangle.y += mouseListener.getY() - rect.y - cursorCoordinates[1];
                    	tileRectangle.h -= mouseListener.getY() - rect.y - cursorCoordinates[1];
                    	tileRectangle.h = Math.abs(tileRectangle.h);
                        rect.y += mouseListener.getY() - rect.y - cursorCoordinates[1];
  
                    }
       	        }

       	        else if(whatToChange.equalsIgnoreCase("XY"))
       	        { 
                    tileRectangle.w += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    tileRectangle.w = Math.abs(tileRectangle.w);
                    rect.x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    tileRectangle.h += mouseListener.getY() - rect.y - cursorCoordinates[1];
                    tileRectangle.h = Math.abs(tileRectangle.h);
                    rect.y += mouseListener.getY() - rect.y - cursorCoordinates[1];
       	        }

		        if(tileRectangle.w * tileRectangle.h > originalTileRectangle.w * originalTileRectangle.h)
                    scaling = "UpScale";
                else
                	scaling = "DownScale";

                Rectangle tempRect = new Rectangle();
                tempRect.equalsTo(game.getTileRectangle(tempParts));

                for(int i = 0; i < parts.length; i++)
                {

                	tempParts[i].x += tileRectangle.x - tempRect.x;
                	tempParts[i].y += tileRectangle.y - tempRect.y;
                }

                game.getTiles().makeSize(tileRectangle.w, tileRectangle.h, tempParts, tileRectangle.x, tileRectangle.y, scaling);

                for(int i = 0; i < parts.length; i++)
		        {
		        	//System.out.println("x"+ tempParts[0].x + "y"+ tempParts[0].y + "w"+ tempParts[0].w + "h"+ tempParts[0].h);
		            parts[i].equalsTo(tempParts[i]);
		        }

            }
            else
                game.getGUI().getButton(tileSerial).isBeingResized(false);  
        }
	}
    
    @Override
	public void setTileSerial(int tileSerial)
	{
		this.tileSerial = tileSerial;
	}
    
    @Override
	public void setColor(Color color)
	{
		this.color = color;
	}

	public void activate()
	{
		cursorCoordinates[0] = (mouseListener.getX() - rect.x);
		cursorCoordinates[1] = (mouseListener.getY() - rect.y);
	}

	public void deactivate(){};
}