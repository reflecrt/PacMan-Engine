public class Rectangle
{
	public int x, y, w, h;
	public double distance;

	public Rectangle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle()
	{
		this(0, 0, 0, 0);
	}

	public boolean intersects(Rectangle otherRectangle)
	{
		if(x > otherRectangle.x + otherRectangle.w || otherRectangle.x > x + w)
			return false;
			
		if(y > otherRectangle.y + otherRectangle.h || otherRectangle.y > y + h)
			return false;

		return true;
	}

	public void equalsTo(Rectangle otherRectangle)
	{
        x = otherRectangle.x;
		y = otherRectangle.y;
		w = otherRectangle.w;
		h = otherRectangle.h;
	}

	public boolean equals(Rectangle otherRectangle)
	{
		if(x != otherRectangle.x)
			return false;
		if(y != otherRectangle.y)
			return false;
		if(w != otherRectangle.w)
			return false;
		if(h != otherRectangle.h)
			return false;

		return true;
	}
}
