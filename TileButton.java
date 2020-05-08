import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TileButton extends GUIbutton
{
	private PacMan game;
	private int tileID;
	private Rectangle rect = new Rectangle();
	private Rectangle[] parts;
	private Color color = Color.white;
	private boolean isSelected = false;
	private KeyboardListener keyListener;
	private int[] cursorCoordinates = new int[2];
	private MouseEventListener mouseListener;
	
    private GUIbutton[] reSizeButtons = new GUIbutton[5];
	private Rectangle[] reSizeButtonRect = new Rectangle[5];
	private boolean isBeingResized = false;
	private boolean reSizeButtonsLoaded = false;
	private boolean intersects = false;

	public TileButton(PacMan game, int tileID, int serial, Rectangle rect, Rectangle[] parts)
	{
		super(null, rect);

		this.game = game;
		this.tileID = tileID;
		this.serial = serial;
		this.rect = rect;
		this.parts = parts;
		keyListener = game.getKeyListener();

		mouseListener = game.getMouseListener();

		// for(int i = 0; i < reSizeButtonRect.length; i++)
		// 	reSizeButtonRect[i] = new Rectangle(rect.x, rect.y, 10, 10);
	}

	@Override
	public void render(RenderAll renderer, Rectangle interfaceRectangle)
	{
		renderer.renderOutlineRectangle(rect, color);
		game.getTiles().renderTile(tileID, renderer, 0, 0, parts);
	}

    @Override
	public void update(PacMan game)
	{
		if(serial == game.getSelectedTileButton() && isWithinTheTile || serial == game.getSelectedTileButton() && reSizeButtonsLoaded)
		{
			if(!isSelected)
			{
				color = game.getColors().getColor(61);
				isSelected = true;
			}
		}
		else
		{
			if(isSelected)
			{
				color = Color.white;
                isSelected = false;
			}
		}

		if(isSelected && isWithinTheTile && !isBeingResized)
		{
            if(keyListener.left())
            {
            	for(int i = 0; i < parts.length; i++)
            		parts[i].x -= 1;

            	rect.x -= 1;
            }

            if(keyListener.right())
            {
            	for(int i = 0; i < parts.length; i++)
            		parts[i].x += 1;

            	rect.x += 1;
            }

            if(keyListener.up())
            {
            	for(int i = 0; i < parts.length; i++)           		
            		parts[i].y -= 1;

            	rect.y -= 1;
            }

            if(keyListener.down())
            {
            	for(int i = 0; i < parts.length; i++)      		
            		parts[i].y += 1;
        
            	rect.y += 1;
            }

            if(mouseListener.isDragging())
            {
			    for(int i = 0; i < parts.length; i++)
                {
                    parts[i].x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                    parts[i].y += mouseListener.getY() - rect.y - cursorCoordinates[1];
                }
                rect.x += mouseListener.getX() - rect.x - cursorCoordinates[0];
                rect.y += mouseListener.getY() - rect.y - cursorCoordinates[1];
            }

        

		}

		if(reSizeButtonsLoaded)
		    setReSizeButtons();
	}
    
    @Override
	public boolean handleMouseClick(Rectangle mouseRectangle)
	{
        // System.out.println(serial);
		intersects = false;

		if(reSizeButtonsLoaded)
		for(int i = 0; i < reSizeButtons.length; i++)
		{
			if(reSizeButtons[i].handleMouseClick(mouseRectangle))
				intersects = reSizeButtons[i].handleMouseClick(mouseRectangle);
		}

		if(mouseRectangle.intersects(rect))
		{
			isWithinTheTile = true;
			activate();
			return true;
		}
		else
		{
			isWithinTheTile = false;
			if(counter != 0 && !intersects)
			{
				deactivate();
			}
		}

		return false;
	}

	@Override
	public Rectangle[] getParts()
	{
		return parts;
	}

    @Override
	public Rectangle getRect()
	{
		return rect;
	}

    @Override
	public MouseEventListener getMouseListener()
	{
		return mouseListener;
	}

    @Override
	public Color getColor()
	{
		return color;
	}

	@Override
	public void isBeingResized(boolean isBeingResized)
	{
		this.isBeingResized = isBeingResized;
	}

	private void setReSizeButtons()
	{
		Rectangle rectangle = new Rectangle(rect.x, rect.y, 8, 8);

        for(int i = 0; i < reSizeButtonRect.length; i++)
        	reSizeButtonRect[i].equalsTo(rectangle);

	    reSizeButtonRect[0].x += rect.w / 2 - reSizeButtonRect[0].w / 2;
	    reSizeButtonRect[0].y -= reSizeButtonRect[0].h / 2;

	    reSizeButtonRect[1].y += rect.h - reSizeButtonRect[0].h / 2;
	    reSizeButtonRect[1].x = reSizeButtonRect[0].x;	    

	    reSizeButtonRect[2].y += rect.h / 2 - reSizeButtonRect[2].h / 2 ;
	    reSizeButtonRect[2].x -= reSizeButtonRect[2].w / 2;	    

	    reSizeButtonRect[3].x += rect.w - reSizeButtonRect[3].w / 2;
	    reSizeButtonRect[3].y = reSizeButtonRect[2].y;	    

	    reSizeButtonRect[4].x += rect.w - reSizeButtonRect[4].w / 2;
	    reSizeButtonRect[4].y += rect.h - reSizeButtonRect[4].h / 2;

	    if(reSizeButtonsLoaded)
	    {
	    	setReSizeButtonColor();
	    	setReSizeButtonSerial();
	    } 
	}

	private void setReSizeButtonSerial()
	{
		if(reSizeButtons[0] != null)
		    for(int i = 0; i < reSizeButtons.length; i++)
			    reSizeButtons[i].setTileSerial(serial);
		else
	    	System.out.println("reSizeButtons not loaded");
	}

	private void setReSizeButtonColor()
	{
		if(reSizeButtons[0] != null)
		    for(int i = 0; i < reSizeButtons.length; i++)
			    reSizeButtons[i].setColor(color);
		else
	    	System.out.println("reSizeButtons not loaded");
	}

	private void loadReSizeButtons()
	{
		if(reSizeButtonRect[0] != null)
	    {
	    	reSizeButtons[0] = new ReSizeButton(reSizeButtonRect[0], serial, "Y", game.getGUI(), color);
            reSizeButtons[1] = new ReSizeButton(reSizeButtonRect[1], serial, "Y", game.getGUI(), color);
            reSizeButtons[2] = new ReSizeButton(reSizeButtonRect[2], serial, "X", game.getGUI(), color);
            reSizeButtons[3] = new ReSizeButton(reSizeButtonRect[3], serial, "X", game.getGUI(), color);
            reSizeButtons[4] = new ReSizeButton(reSizeButtonRect[4], serial, "XY", game.getGUI(), color);
            reSizeButtonsLoaded = true;
	    }
	    else
	    	System.out.println("reSizeButton's Rectangles not set");
		
	}

	public void activate()
	{
		        buttonsRemoved = true;
	    counter++;
	    if(!reSizeButtonsLoaded)
	    {
        
            for(int i = 0; i < reSizeButtonRect.length; i++)
        	    reSizeButtonRect[i] = new Rectangle();
            setReSizeButtons();
            loadReSizeButtons();

		    game.getGUI().addButtons(reSizeButtons);
	    }
        
        game.getGUI().bringToTop(serial);
	    for(int i = 0; i < reSizeButtons.length; i++)
            game.getGUI().bringToTop(reSizeButtons[i].getSerial());


	    cursorCoordinates[0] = (mouseListener.getX() - rect.x);
	    cursorCoordinates[1] = (mouseListener.getY() - rect.y);
	    game.setSelectedTileButton(serial);
	    //System.out.println(serial);
	}

	public void deactivate()
	{
	    counter = 0;
        unloadButtons();     
	}

	private void unloadButtons()
	{
		for(int i = 0; i < reSizeButtons.length; i++)
        {
            	//System.out.println("Tile " + serial + "  " + reSizeButtons[i].getSerial());
            	//System.out.println(serial);
			game.getGUI().removeButtons(reSizeButtons[i].getSerial());
			reSizeButtons[i] = null;
			reSizeButtonRect[i] = null;
        }

        reSizeButtonsLoaded = false; 
	}
}