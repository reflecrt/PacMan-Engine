public interface GameObjects
{
	//Call every time.
	public void render(RenderAll renderer);

	//Call at 60 FPS rate.
	public void update(PacMan game);

	//Call whenever mouse is clicked on canvas.
	//Return true to stop checking other clicks.
	public boolean handleMouseClick(Rectangle rect);
}