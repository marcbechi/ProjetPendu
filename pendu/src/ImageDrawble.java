import java.awt.*;

class ImageDrawble implements IDrawable 
{
	Image image;
	DessinPendu panel;
	Point pos;
	int width, height;	
	public ImageDrawble(Image image,Point pos,int width,int height, DessinPendu panel ) 
	{
		this.pos=pos;
		this.height=height;
		this.width=width;
		this.image=image;
		this.panel=panel;
	}

	public void draw(Graphics g) 
	{
		g.drawImage(image,pos.x,pos.y,width,height,panel);
	}
}