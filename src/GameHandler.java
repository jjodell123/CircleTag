import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameHandler extends JFrame implements Runnable, KeyListener{

	Thread t=new Thread (this);
	Container con=getContentPane();
	private String name;
	private boolean playScreen=true;
	private boolean up=false,left=false,right=false,down=false;
	private int x,y;
	private int speed=10;
	private ArrayList<Enemy> e=new ArrayList<Enemy>();
	private Rectangle r,lR,rR,uR,dR;
	private Map map;
	private int wallHit=-1;
	private boolean it=false,stop=false;
	
	public GameHandler(String name)
	{
		addKeyListener(this);
		this.setSize(1000,1000);
		con.setLayout(new FlowLayout());
		this.name=name;
		map=new Map();
		x=y=75;
		r=new Rectangle(x,y,25,25);
		lR=new Rectangle(x-speed,y,speed,25);
		rR=new Rectangle(x+25,y,speed,25);
		uR=new Rectangle(x,y-speed,25,speed);
		dR=new Rectangle(x,y+25,25,speed);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		t.start();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				t.sleep(60);
				if(!stop)
					sendKeys();
				swap();
				Client.update(x, y);
				repaint();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setRects()
	{
		r.setLocation(x,y);
		lR.setLocation(x-speed,y);
		rR.setLocation(x+25,y);
		uR.setLocation(x,y-speed);
		dR.setLocation(x,y+25);

	}
	
	public void swap()
	{
		if(x<0 || x>975)
		{
			x=Math.abs(x-965);
			setRects();
		}
		else if(y<0 || y>975)
		{
			y=Math.abs(y-965);
			setRects();
		}
	}
	
	public void sendKeys()
	{
		if(left)
		{
			x-=speed;
			setRects();
			if(testSides(true))
			{
				Wall w=map.getWalls().get(wallHit);
				x=w.getX()+w.getL();
				setRects();
			}
			//System.out.println(testSides(true));
			//moveObjects(false);
		}
		if(right)
		{
			x+=speed;
			setRects();
			if(testSides(true))
			{
				x=map.getWalls().get(wallHit).getX()-25;
				setRects();
			}
			//moveObjects(false);
		}
		if(up)
		{
			y-=speed;
			setRects();
			if(testSides(false))
			{
				Wall w=map.getWalls().get(wallHit);
				y=w.getY()+w.getH();
				setRects();
			}
			//moveObjects(true);
		}
		if(down)
		{
			y+=speed;
			setRects();
			if(testSides(false))
			{
				y=map.getWalls().get(wallHit).getY()-25;
				setRects();
			}
			//moveObjects(true);
		}
	}
	
	public boolean testSides(boolean hor)
	{
		boolean stop=false;
		wallHit=-1;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for(int i=0;i<map.getWalls().size();i++)
		{
			if((hor==true && map.getWalls().get(i).getRect().intersects(dR) && map.getWalls().get(i).getRect().intersects(uR)) ||
				(hor==false && map.getWalls().get(i).getRect().intersects(lR) && map.getWalls().get(i).getRect().intersects(rR)))
			{
				stop=true;
				wallHit=i;
			}
			else if(hor==true && (map.getWalls().get(i).getRect().intersects(dR) && map.getWalls().get(i).getRect().intersects(lR)) ||
				(map.getWalls().get(i).getRect().intersects(dR) && map.getWalls().get(i).getRect().intersects(rR)) ||
				(map.getWalls().get(i).getRect().intersects(uR) && map.getWalls().get(i).getRect().intersects(lR)) ||
				(map.getWalls().get(i).getRect().intersects(uR) && map.getWalls().get(i).getRect().intersects(rR)))
			{
				stop=true;
				wallHit=i;
			}
			else if(hor==false && (map.getWalls().get(i).getRect().intersects(dR) && map.getWalls().get(i).getRect().intersects(lR)) ||
					(map.getWalls().get(i).getRect().intersects(dR) && map.getWalls().get(i).getRect().intersects(rR)) ||
					(map.getWalls().get(i).getRect().intersects(uR) && map.getWalls().get(i).getRect().intersects(lR)) ||
					(map.getWalls().get(i).getRect().intersects(uR) && map.getWalls().get(i).getRect().intersects(rR)))
				{
					stop=true;
					wallHit=i;
				}
		}
		return stop;
	}
	
	public void moveObjects(boolean vert)
	{
	}
	
	public boolean getPlayScreen()
	{
		return playScreen;
	}
	
	public void paint(Graphics gr)
	{
		Image i=createImage(getSize().width, getSize().height);
		Graphics2D g = (Graphics2D)i.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
		for(Enemy ee:e)
			ee.draw(g);
		g.setColor(Color.blue);
		if(it)
			g.setColor(Color.red);
		g.fillOval(x,y, 25, 25);
		map.draw(g);
		g.dispose();
		gr.drawImage(i, 0, 0, this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent j) {
		if(j.getKeyCode()==87)
		{
			up=true;
		}
		//down
		if(j.getKeyCode()==83)
		{
			
			down=true;
		}
		//left
		if(j.getKeyCode()==65)
		{   
			left=true;

		}
		//right
		if(j.getKeyCode()==68)
		{
			right=true;

		}
		
	}

	@Override
	public void keyReleased(KeyEvent j) {
		if(j.getKeyCode()==87)
		{
			up=false;
		}
		//down
		if(j.getKeyCode()==83)
		{
			
			down=false;
		}
		//left
		if(j.getKeyCode()==65)
		{   
			left=false;

		}
		//right
		if(j.getKeyCode()==68)
		{
			right=false;

		}
		
	}
	
	public void addEnemy(String n,String x,String y,String it)
	{
		e.add(new Enemy(n,x,y,it));
	}
	
	public void updateEnemy(String n,String x,String y,String it)
	{
		for(int i=0;i<e.size();i++)
		{
			if(n.equals(e.get(i).getName()))
				e.get(i).update(x, y,it);
		}
	}
	
	public void setIt()
	{
		it=!it;
	}
	
	public void setStop()
	{
		stop=!stop;
	}
}
