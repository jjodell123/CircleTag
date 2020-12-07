import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall {
	
	private int x,y,l,h;
	private Rectangle r;
	
	public Wall(int x,int y,int l,int h)
	{
		this.x=x;
		this.y=y;
		this.l=l;
		this.h=h;
		r=new Rectangle(x,y,l,h);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getL()
	{
		return l;
	}
	
	public int getH()
	{
		return h;
	}

	public void moveY(int y)
	{
		
		this.y+=y;
	}
	
	public void moveX(int x)
	{
	//	System.out.println(this.x);
		this.x+=x;
	}	
	
	public Rectangle getRect()
	{
		return r;
	}
	
	
	public void draw(Graphics2D g)
	{
		//System.out.println(x-pX+" "+x+" "+pX);
		g.setColor(new Color(153,0,0));
		g.fillRect(x,y,l,h);
		g.setColor(Color.white);
			for(int i=0;i<h;i+=25)
			{
				for(int ii=0;ii<l;ii+=25)
				{
					g.drawLine(x+ii+13, y+i, x+ii+13, y+i+13);
					g.drawLine(x+ii, y+i, x+ii+24, y+i);
					g.drawLine(x+ii, y+i+13, x+ii+24, y+i+13);
					g.drawLine(x+ii, y+i+13, x+ii, y+i+24);
					//g.drawLine(x+ii+24, y+i+13, x+ii+24, y+i+24);
				}
			}
		
		
	}

}
