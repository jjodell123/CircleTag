import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {

	private int x,y;
	private String name;
	public boolean it;
	
	public Enemy(String name,String x,String y,String it)
	{
		this.name=name;
		this.x=Integer.parseInt(x);
		this.y=Integer.parseInt(y);
		this.it=Boolean.parseBoolean(it);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void moveY(int y)
	{
		
		this.y+=y;
	}
	
	public void moveX(int x)
	{
		System.out.println(this.x);
		this.x+=x;
	}
	
	public void update(String x,String y,String it)
	{
		this.x=Integer.parseInt(x);
		this.y=Integer.parseInt(y);
		this.it=Boolean.parseBoolean(it);
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.blue);
		if(it)
			g.setColor(Color.red);
		g.drawString(name, x, y);
		g.fillOval(x, y, 25, 25);
	}
}
