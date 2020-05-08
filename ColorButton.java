import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ColorButton extends GUIbutton
{
	private PacMan game;
	private int colorID;
	private Color color;
	private Rectangle colorButtonRectangle = new Rectangle();
	private Color tempColor;
	private boolean isOrange = false;

	public ColorButton(PacMan game, int colorID, Rectangle rect, Color color)
	{
		super(color, rect);

		this.game = game;
		this.colorID = colorID;

		colorButtonRectangle.equalsTo(rect);
		colorButtonRectangle.x += 2;
		colorButtonRectangle.y += 2;
		colorButtonRectangle.w -= 4;
		colorButtonRectangle.h -= 4;

		this.color = color;
		tempColor = color;
	}

	@Override
	public void render(RenderAll renderer, Rectangle interfaceRectangle)
	{
		renderer.renderRectangle(rect, interfaceRectangle, color);
		renderer.renderRectangle(colorButtonRectangle, interfaceRectangle, game.getColors().getColor(colorID));				
	}

    @Override
	public void update(PacMan game)
	{
		 if(colorID == game.getSelectedColor())
		 {
		 	if(!isOrange)
		 	{
		 		color = game.getColors().getColor(108);
		 		isOrange = true;
		 	}
	     }
		 else
		 {
		 	if(isOrange)
		 	{
		 		color = tempColor;
                isOrange = false;
		 	}
		 }
	}

	public void activate()
	{
		 counter++;
		 if(counter == 2)
		 {
	        counter = 0;
            game.setSelectedColor(-1);
		 }
		 else
		    game.setSelectedColor(colorID);

		System.out.println(colorID);
	}

	public void deactivate()
	{
        counter = 0;
	}
}