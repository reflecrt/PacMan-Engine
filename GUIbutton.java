import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public abstract class GUIbutton implements GameObjects
{
	protected Graphics2D graphics;
	protected Color color;
	protected Rectangle rect;
	protected int serial;
	protected boolean isWithinTheTile = false;
	protected int counter = 0;
	public boolean buttonsRemoved = false; 

	public GUIbutton(Color color, Rectangle rect)
	{
		this.color = color;
		this.rect = rect;
	}

	public void render(RenderAll renderer)
	{}

	public void render(RenderAll renderer, Rectangle interfaceRectangle)
	{
		renderer.renderRectangle(rect, interfaceRectangle, color);
	}

	public void update(PacMan game){}

	public boolean handleMouseClick(Rectangle mouseRectangle)
	{
		if(mouseRectangle.intersects(rect))
		{
			isWithinTheTile = true;
			activate();
			return true;
		}
		else
		{
			isWithinTheTile = false;
			if(counter != 0)
			{
				deactivate();
			}
		}

		return false;
	}

	//TileButton.
	public Rectangle[] getParts()
	{
		return null;
	}
	public Rectangle getRect()
	{
		return null;
	}
	public MouseEventListener getMouseListener()
	{
		return null;
	}
	public Color getColor()
	{
		return null;
	}
	public void isBeingResized(boolean isBeingResized){};

    //ReSizeButton.
    public void setTileSerial(int tileSerial){};
	public void setColor(Color color){};

	public abstract void activate();
	public abstract void deactivate();

	public void setSerial(int serial)
	{
		this.serial = serial;
	}

	public int getSerial()
	{
		return serial;
	}
}