import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TileEditorButton extends GUIbutton
{
	private PacMan game;
	private int tileID;
	private Color color;
	private Color tempColor;
	private boolean isRed = false;

	public TileEditorButton(PacMan game, int tileID, Rectangle rect, Color color)
	{
		super(color, rect);

		this.game = game;
		this.tileID = tileID;
		this.color = color;
		tempColor = color;
	}

	@Override
	public void render(RenderAll renderer, Rectangle interfaceRectangle)
	{
		renderer.renderRoundRectangle(rect, interfaceRectangle, color, 10, 10);
		game.getTiles().renderTile(tileID, renderer, rect.x + interfaceRectangle.x  + 3, rect.y + interfaceRectangle.y + 1, rect.w - 5, rect.w - 5, "DownScale");	
	}

    @Override
	public void update(PacMan game)
	{
		if(tileID == game.getSelectedTile())
		{
			if(!isRed)
			{
				color = game.getColors().getColor(15);
				isRed = true;
			}
		}
		else
		{
			if(isRed)
			{
				color = tempColor;
                isRed = false;
			}
		}
	}

	public void activate()
	{
		counter++;
		if(counter == 2)
		{
		    counter = 0;
            game.setSelectedTile(-1);
		}
		else
		   game.setSelectedTile(tileID);

		//System.out.println(tileID);
	}

	public void deactivate()
	{
        counter = 0;
	}
}