import java.awt.*;

public  class StringDrawble implements IDrawable 
{
	Point point;
	String str;
	Color color;

	public StringDrawble(Color color,String str,Point point) 
	{
		this.color=color;
		this.str=str;
		this.point=point;
	}

	public void draw(Graphics g) 
	{
		g.setColor(color);
		g.drawString(str,(int)point.getX(),(int)point.getY());

	}
}
