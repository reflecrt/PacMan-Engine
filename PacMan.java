import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import java.lang.Runnable;
import java.lang.Thread;
import javax.swing.JFrame;

import java.io.IOException;
import java.io.File;

public class PacMan extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();
	private RenderAll renderer;
	private Tiles tiles;
	private Colors colors;
	private Graphics2D graphics;

  private Rectangle mouseRectangle;

  private KeyboardListener keyListener = new KeyboardListener(this);
  private MouseEventListener mouseListener = new MouseEventListener(this);

	private GameObjects[] objects;
  private GUIbutton[] buttons;
  private GUI gui;

  private int selectedTile = -1;
  private int selectedTileButton = -1;
  private int selectedColor = -1;

	public PacMan()
	{
    //Makes our program shut down when exited out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Set the position and size of our frame.
		setBounds(0 ,0 , 1000, 1000);

    //Put out frame in the centre of the screen.
		setLocationRelativeTo(null);

		add(canvas);

    //Makes our frame visible.
		setVisible(true);

    //Create our object for Bufferstrategy.
    canvas.createBufferStrategy(3);

		//Load Assets.
		colors = new Colors(new File("colors.txt"));
		tiles = new Tiles(new File("tiles.txt"), colors);

		//Loading GUI.
		buttons = new GUIbutton[tiles.size() + colors.size()];

		for(int i = 0; i < tiles.size(); i++)
		{
			Rectangle tileRectangle = new Rectangle(2, i * (16 * 3 + 5), 16 * 3, 16 * 3);
			buttons[i] = new TileEditorButton(this, i, tileRectangle, colors.getColor(14));
		}
    int x = 0;
    int y = 2;
    for(int i = tiles.size(); i < buttons.length; i++)
    {
      if(x * (16 + 5) + 100 > 1280)
      {
        x = 0;
        y += 16 +5;
      }

      Rectangle tileRectangle = new Rectangle(x * (16 + 5) + 100, y, 16, 16);
      buttons[i] = new ColorButton(this, (i - tiles.size()), tileRectangle, colors.getColor(14));
      x++;
    }

		gui = new GUI(null, buttons, 3, 3);

		//Loading GameObjects.
		objects = new GameObjects[1];
		objects[0] = gui;

    //Add Listeners.
    canvas.addKeyListener(keyListener);
    canvas.addFocusListener(keyListener);
    canvas.addMouseListener(mouseListener);
    canvas.addMouseMotionListener(mouseListener);
	}
   
   public void update()
   {
   	for(int i = 0; i < objects.length; i++)
   		objects[i].update(this);
   }

   public void leftClick(int x, int y)
   {
    mouseRectangle = new Rectangle(x, y, 1, 1);
    boolean stoppedChecking = false;

    for(int i = 0; i < objects.length; i++)
      if(!stoppedChecking)
        stoppedChecking = objects[i].handleMouseClick(mouseRectangle);
      
      if(!stoppedChecking)
      {
        gui.addButtons(createSelectedTileButton(selectedTile, x, y));
      }
   }

   public void render()
   {
   	BufferStrategy bufferstrategy = canvas.getBufferStrategy();
    Graphics g = bufferstrategy.getDrawGraphics();
    graphics = (Graphics2D) g;
   	super.paint(graphics);

   	renderer = new RenderAll(this, graphics, getWidth(), getHeight());
   	for(int i = 0; i < objects.length; i++)
   	{
   		objects[i].render(renderer);
   	}

    //tiles.renderTile(0, renderer, 100, 100, 10, 10, "DownScale");

    //\graphics.fillRect(1.0f, 1.0f, 5.0f, 10.0f);

   	graphics.dispose();
   	bufferstrategy.show();
   }

   public void run()
   {
   	BufferStrategy bufferStrategy = canvas.getBufferStrategy();
   	long lastTime = System.nanoTime();
    
    //To get 60 FPS.
   	double nanoSecondConversion = 1000000000.0 / 60.0;
    double changeInSeconds = 0.0;

    while(true)
    {
    	long now = System.nanoTime();
    	changeInSeconds += (now - lastTime) / nanoSecondConversion; 
        
        while(changeInSeconds >= 1)
        {
        	update();
        	changeInSeconds = 0.0;
        }
        render();
        lastTime = now;
    }

   }

   public static void main(String[] args)
   {
   	PacMan game = new PacMan();
   	Thread gameThread = new Thread(game);
   	gameThread.start();
   }



  



  private GUIbutton createSelectedTileButton(int selectedTileID, int x, int y)
  {
      GUIbutton button;
      Rectangle[] tempParts = tiles.getParts(selectedTileID);
      Rectangle[] parts = new Rectangle[tempParts.length];
      
      for(int i = 0; i < tempParts.length; i++)
      {
        parts[i] = new Rectangle();
        parts[i].equalsTo(tempParts[i]);
        parts[i].x += x;
        parts[i].y += y;
      }

      button = new TileButton(this, selectedTileID, gui.numberOfButtons(), getTileRectangle(parts), parts);
      
      return button;
  }

  public GUIbutton createTileButton(int tileID)
  {
    GUIbutton button;
    Rectangle tileRectangle = new Rectangle(0, tileID * (16*3 + 2), 16 * 3, 16 * 3);
    button = new TileEditorButton(this, tileID, tileRectangle, Color.blue);

    return button;
  }

  public Rectangle getTileRectangle(Rectangle[] parts)
  {
    Rectangle tileRectangle = new Rectangle();
    tileRectangle.x = tileRectangle.y = 1000;

    if(parts.length > 1)
        {
          for(int j = 0; j < parts.length; j++)
          {
              if(parts[j].x + parts[j].w > tileRectangle.w)
                tileRectangle.w = parts[j].x + parts[j].w;
              else if(parts[j].x < tileRectangle.x)
                tileRectangle.x = parts[j].x;

              if(parts[j].y + parts[j].h > tileRectangle.h)
                tileRectangle.h = parts[j].y + parts[j].h;
              else if(parts[j].y < tileRectangle.y)
                tileRectangle.y = parts[j].y;
          }

          tileRectangle.w -= tileRectangle.x;
          tileRectangle.h -= tileRectangle.y;
      }
      else
      {
        tileRectangle.equalsTo(parts[0]);
      }

      return tileRectangle;
  }


   public Graphics2D getGraphics()
   {
    return graphics;
   }

   public RenderAll getRenderer()
   {
    return renderer;
   }

   public Tiles getTiles()
   {
    return tiles;
   }

   public void resetTiles()
   {
    tiles = new Tiles(new File("tiles.txt"), colors);
   }

   public Colors getColors()
   {
    return colors;
   }

   public int getSelectedTile()
   {
    return selectedTile;
   }

   public int getSelectedTileButton()
   {
    return selectedTileButton;
   }

   public int getSelectedColor()
   {
    return selectedColor;
   }

   public void setSelectedTile(int selectedTile)
   {
    this.selectedTile = selectedTile;
   }

   public void setSelectedTileButton(int selectedTileButton)
   {
    this.selectedTileButton = selectedTileButton;
   }

   public void setSelectedColor(int selectedColor)
   {
    this.selectedColor = selectedColor;
   }

   public KeyboardListener getKeyListener()
   {
    return keyListener;
   }

   public MouseEventListener getMouseListener()
   {
    return mouseListener;
   }

   public GUI getGUI()
   {
    return gui;
   }
}
