import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUI implements GameObjects
{
	private Rectangle rect = new Rectangle();
	private Color color;
	private GUIbutton[] buttons = null;
	private GUIbutton button;
	private Graphics2D graphics;
	private boolean draw = false;

	public GUI(Color color, GUIbutton[] buttons, int x, int y)
	{	
		rect.x = x;
		rect.y = y;

		this.color = color;
		this.buttons = buttons;

		if(color != null)
		{
			rect.w = 1280;
			rect.h = 720;
		}
	}

	public GUI(Color color, GUIbutton button, int x, int y)
	{
		rect.x = x;
		rect.y = y;

		this.color = color;
		this.button = button;

		if(color != null)
		{
			rect.w = 1280;
			rect.h = 720;
		}
	}

	public void render(RenderAll renderer)
	{
		//System.out.println(buttons.length);
		if(color != null)
		{
		    renderer.renderRectangle(rect, color);
		}
	
		if(buttons != null)
			for(int i = 0; i < buttons.length; i++)
                if(buttons[i] != null)
                    buttons[i].render(renderer, rect);
                
		else if(button != null)
			button.render(renderer);
	}

	public void update(PacMan game)
    {
    	if(buttons != null)
    	{
    		for(int i = 0; i < buttons.length; i++)
                if(buttons[i] != null)
                    buttons[i].update(game);
    	}
    	else if(button != null)
    		button.update(game);
    }

    public boolean handleMouseClick(Rectangle mouseRectangle)
    {
    	boolean hasStopped = false;
    	if(rect.w ==0 || rect.h == 0 || mouseRectangle.intersects(rect))
    	{
    		for(int currentButton = buttons.length - 1; currentButton >= 0; currentButton--)
    		{
    			boolean result = buttons[currentButton].handleMouseClick(mouseRectangle);
    			if(hasStopped == false)
    				hasStopped = result; 
    		}
    	}

    	return hasStopped;
    }

    public void addButtons(GUIbutton[] newButtons)
    {
    	GUIbutton[] tempButtons = buttons;
    	buttons = new GUIbutton[buttons.length + newButtons.length];
        
        for(int i = 0; i < tempButtons.length; i++)
        {
           buttons[i] = tempButtons[i];
        }

        for(int j = tempButtons.length; j < buttons.length; j++)
        {
        	buttons[j] = newButtons[j - tempButtons.length];
        }
    }

    public void addButtons(GUIbutton newButton)
    {
    	GUIbutton[] tempButtons = buttons;
    	buttons = new GUIbutton[buttons.length + 1];
        
        for(int i = 0; i < tempButtons.length; i++)
        {
           buttons[i] = tempButtons[i];
        }
        	buttons[tempButtons.length] = newButton;
    }

    public void removeButtons(int start, int end)
    {
        GUIbutton[] newButtons = new GUIbutton[buttons.length - ((end - start) + 1)];

        for(int i = start; i <= end; i++)
        {
            buttons[i] = null;
        }

        int j = 0;
        for(int i = 0; i < buttons.length; i++)
        {
            if(buttons[i] != null)
            {
                newButtons[j] = buttons[i];
                j++;
            }
        }

        buttons = newButtons;

        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].setSerial(i);
        }
    }

    public void removeButtons(int serial)
    {
        GUIbutton[] newButtons = new GUIbutton[buttons.length - 1];

        buttons[serial] = null;

        int j = 0;
        for(int i = 0; i < buttons.length; i++)
        {
            if(buttons[i] != null)
            {
                newButtons[j] = buttons[i];
                j++;
            }
        }

        buttons = newButtons;

        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].setSerial(i);
        }
    }

    public void bringToTop(int serial)
    {
        GUIbutton tempButton = buttons[serial];
        GUIbutton tempButton2 = buttons[buttons.length - 1];

        for(int i = buttons.length - 2; i >= serial; i--)
        {
            GUIbutton tempButton3 = buttons[i];
            buttons[i] = tempButton2;
            tempButton2 = tempButton3;

            buttons[i].setSerial(i);
        }

        buttons[buttons.length - 1] = tempButton;
        buttons[buttons.length - 1].setSerial(buttons.length - 1);
    }

    public void replace(GUIbutton[] buttons)
    {
    	this.buttons = buttons;
    }

    public GUIbutton getButton(int serial)
    {
        return buttons[serial];
    }

    public int numberOfButtons()
    {
        return buttons.length;
    }
 
} 