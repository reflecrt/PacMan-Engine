import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RenderAll
{
	private PacMan game;
	private Graphics2D graphics;
	private int width;
	private int height;

	public RenderAll(PacMan game, Graphics2D graphics, int width, int height)
	{
		this.game = game;
		this.graphics = graphics;

		graphics.setColor(game.getColors().getColor(13));
		graphics.fillRect(0 , 0, width, height);		
	}

	public void renderPart(String name, int x, int y, Rectangle part, Color color)
	{
		graphics.setColor(color);

		//System.out.println("NAME:" + name + " " + "x:" + (part.x) + " " + "y:" + (part.y) + " " + "w:" + part.w + " " + "h:" + part.h);
		
		if(name.equalsIgnoreCase("Rect"))
			graphics.fillRect(part.x + x, part.y + y, part.w, part.h);
		else if(name.equalsIgnoreCase("Oval"))
			graphics.fillOval(part.x + x, part.y + y, part.w, part.h);
		else if(name.equalsIgnoreCase("Triangle"))
		    fillTriangle(part.x + x, part.y + y, part.w, part.h, color);		
	}

	public void renderRectangle(Rectangle rect, Rectangle offsetRectangle, Color color)
	{
		graphics.setColor(color);
		graphics.fillRect(rect.x + offsetRectangle.x, rect.y + offsetRectangle.y, rect.w, rect.h);
	}

	public void renderRectangle(Rectangle rect, Color color)
	{
		graphics.setColor(color);
		graphics.fillRect(rect.x, rect.y, rect.w, rect.h);
	}

	public void renderOutlineRectangle(Rectangle rect, Color color)
	{
		graphics.setColor(color);
		graphics.drawRect(rect.x, rect.y, rect.w, rect.h);
	}

	public void renderOutlineRectangle(Rectangle rect, Rectangle offsetRectangle, Color color)
	{
		graphics.setColor(color);
		graphics.drawRect(rect.x + offsetRectangle.x, rect.y + offsetRectangle.y, rect.w, rect.h);
	}

	public void renderRoundRectangle(Rectangle rect, Color color, int x, int y)
	{
		graphics.setColor(color);
		graphics.fillRoundRect(rect.x, rect.y, rect.w, rect.h, x, y);
	}

	public void renderRoundRectangle(Rectangle rect, Rectangle offsetRectangle, Color color, int x, int y)
	{
		graphics.setColor(color);
		graphics.fillRoundRect(rect.x + offsetRectangle.x, rect.y + offsetRectangle.y, rect.w, rect.h, x, y);
	}



	private void fillTriangle(int x, int y, int width, int height, Color color)
	{
		int[] X = new int[3];
		int[] Y = new int[3];
		
		X[0] = x + (int)(Math.ceil((double)width / 2.0));
		X[1] = x;
		X[2] = x + width;

		Y[0] = y;
		Y[1] = y + height;
		Y[2] = Y[1];

        graphics.setColor(color);
        graphics.fillPolygon(X, Y, 3);
	}

	public void resetTiles()
	{
        game.resetTiles();
	}
}