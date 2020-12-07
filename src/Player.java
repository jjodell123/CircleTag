import java.awt.Rectangle;

public class Player {
	
	private String name;
	private int x,y;
	private boolean it=false;
	private Rectangle r;
	
	public Player(String name, int x,int y)
	{
		this.name=name;
		this.x=x;
		this.y=y;
		r=new Rectangle(this.x,this.y,25,25);
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean getIt()
	{
		return it;
	}
	
	public void setIt()
	{
		it=!it;
	}
	
	public Rectangle getRect()
	{
		return r;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setRect()
	{
		r.setLocation(x, y);
	}
	
	public void setX(String x)
	{
		this.x=Integer.parseInt(x);
	}
	
	public void setY(String y)
	{
		this.y=Integer.parseInt(y);
	}
	
	public String toString()
	{
		return ""+name+":"+x+","+y+","+it;
	}
	
	
}
