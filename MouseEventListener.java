import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener
{
	public boolean[] keys = new boolean[120];
	private MouseEvent event;
	private PacMan game;
    private int[] XY = new int[2]; 
    private boolean isDragging = false;
    
    public MouseEventListener(PacMan game)
    {
    	this.game = game;
    }

    public void mouseClicked(MouseEvent event){}

    public void mousePressed(MouseEvent event)
    {
        if(event.getButton() == MouseEvent.BUTTON1)
        {
            XY[0] = event.getX();
            XY[1] = event.getY();
            game.leftClick(event.getX(), event.getY());
        }

        int keyCode = event.getButton();

        if(keyCode < keys.length)
            keys[keyCode] = true;
    }

    public void mouseDragged(MouseEvent event)
    { 
        isDragging = false;

        if(leftClick())
        {
            isDragging = true;
            XY[0] = event.getX();
            XY[1] = event.getY();
        }
    }
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mouseMoved(MouseEvent event){}

    public void mouseReleased(MouseEvent event)
    {
        isDragging = false;
    	int keyCode = event.getButton();

    	if(keyCode < keys.length)
    		keys[keyCode] = false;

    	this.event = event;
    }

    public boolean leftClick()
    {
    	return(keys[MouseEvent.BUTTON1]);
    }

    public MouseEvent getEvent()
    {
    	return event;
    }

    public int getX()
    {
        return XY[0];
    }

    public int getY()
    {
        return XY[1];
    }

    public boolean isDragging()
    {
        return isDragging;
    }
}