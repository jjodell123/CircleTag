import java.awt.Graphics2D;
import java.util.ArrayList;

public class Map {

	
	private ArrayList<Wall> w=new ArrayList<Wall>();
	
	public Map()
	{
		//borders
		w.add(new Wall(9,38,25,450));
		w.add(new Wall(9,541 ,25,450));
		w.add(new Wall(34,38,425,25));
		w.add(new Wall(541,38,450,25));
		w.add(new Wall(966,38,25,450));
		w.add(new Wall(966,541 ,25,450));
		w.add(new Wall(34,966,425,25));
		w.add(new Wall(541,966,450,25));
		
		//layers count outside to inside
		
		//first layer 
		w.add(new Wall(109,192,25,650));
		w.add(new Wall(200,138,600,25));
		w.add(new Wall(866,192,25,650));
		w.add(new Wall(200,866,600,25));
		
		//second layer (innerward sticking walls)
		w.add(new Wall(200,163,25,100));
		w.add(new Wall(775,163,25,100));
		w.add(new Wall(200,766,25,100));
		w.add(new Wall(775,766,25,100));
		
		//third layer(polygon)
		w.add(new Wall(300,238,400,25));
		w.add(new Wall(200,339,200,25));
		w.add(new Wall(600,339,200,25));
		
		w.add(new Wall(200,364,25,300));
		w.add(new Wall(775,364,25,300));

		
		w.add(new Wall(300,766,400,25));
		w.add(new Wall(200,664,200,25));
		w.add(new Wall(600,664,200,25));
		
		
		//fourth layer (equals shape)
		w.add(new Wall(300,438,400,25));
		w.add(new Wall(300,566,400,25));
		
		//fourth layer (connects third and fourth)
		w.add(new Wall(500,263,25,175));
		w.add(new Wall(500,591,25,175));

	}
	
	public void moveY(int y)
	{
		for(int i=0;i<w.size();i++)
			w.get(i).moveY(y);
	}
	
	public void moveX(int x)
	{
		for(int i=0;i<w.size();i++)
			w.get(i).moveX(x);
	}
	
	public ArrayList<Wall> getWalls()
	{
		return w;
	}
	
	public void draw(Graphics2D g)
	{
		for(int i=0;i<w.size();i++)
			w.get(i).draw(g);
	}
}
