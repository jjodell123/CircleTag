import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server extends JFrame{
	
	private ServerSocket s;
	private Allocator al;
	private JPanel mainPane;
	private JTextArea text = new JTextArea();
	private ArrayList<Player> players=new ArrayList<Player>();
	private ArrayList<PrintWriter> writers=new ArrayList<PrintWriter>();
	private boolean d=false;


	public Server(ServerSocket s)
	{
		this.s = s;
		this.setLayout(new GridLayout(0,1));
		this.setSize(500,300);
		this.setLocationRelativeTo(null);
		this.setTitle("Server Console");
		mainPane = new JPanel();
		mainPane.setLayout(new GridLayout(0,1));
		mainPane.setBackground(Color.BLACK);
		this.add(mainPane);
		this.setVisible(true);
		al = new Allocator(s);
		al.start();
	}
	
	private class Allocator extends Thread
	{
		
		ServerSocket socket;
		ArrayList<Handler> handlers = new ArrayList<Handler>();

		
		public Allocator(ServerSocket s)
		{
			socket=s;
		}

		public void run()
		{	
			try
			{
				while(true)
				{
					System.out.println("RUNNING");
					text.setBackground(Color.BLACK);
					text.setForeground(Color.GREEN);
					try
					{
						Socket s=socket.accept();
						text.setBackground(Color.BLACK);
						text.setForeground(Color.GREEN);
						text.append(s+"\n");
						mainPane.add(text);
						handlers.add(new Handler(s));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			finally
			{
				try
				{
					socket.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}	
			}
		}
	}
	
	private class Handler extends Thread
	{
		
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		private String name;
		private int index;
		private int cooldown=100;
		
		public Handler(Socket s)
		{
			System.out.println("handler added");
			this.socket=s;
			start();
		}

		public void run()
		{
			
			try
			{
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				index=writers.size();
				out=new PrintWriter(socket.getOutputStream(),true);
				
				while(true)
				{
					name=in.readLine();
					if(name==null)
						return;
					synchronized (players)
					{
						int count=0;
						for(int i=0;i<players.size();i++)
						{
							if(name.length()<=players.get(i).getName().length())
							{
								if(players.get(i).getName().substring(0,name.length()).equals(name))
									count++;
							}
						}
						if(count!=0)
							name=name+"("+count+")";
					//JOptionPane.showMessageDialog(null,name);
						writers.add(out);
						writers.get(index).println(name);
						players.add(new Player(name,50,50));
						if(players.size()==1)
						{
							writers.get(0).println("YES");
							players.get(0).setIt();
						}
						text.append(name+" has joined"+"\n");				
						break;
					}
				}
				while(true)
				{
					Player current=null;
					int cI=-1;
					boolean test=false;
					String line=in.readLine();
					if(line==null)
						return;
					String tempName=line.substring(0,line.indexOf(":"));
					for(int i=0;i<players.size();i++)
					{
						if(players.get(i).getName().equals(tempName))
						{
							current=players.get(i);
							cI=i;
						}
						
					}
					if(current.getIt())
						test=true;

					String temp=line.substring(0,line.indexOf(":"));
					synchronized(players)
					{
						for(int i=0;i<players.size();i++)
						{
							if(players.get(i).getName().equals(temp))
							{	
								line=line.substring(line.indexOf(":")+1);
								players.get(i).setX(line.substring(0,line.indexOf(",")));
								line=line.substring(line.indexOf(",")+1);
								players.get(i).setY(line);
								players.get(i).setRect();
							}
						}
						if(cooldown<100)
						{
							cooldown++;
							if(cooldown==100)
							{
								d=false;
								for(int i=0;i<players.size();i++)
								{
									if(players.get(i).getIt())
									{
										writers.get(i).println("START");
									}
								}
							}
						}
						if(!d && test && cooldown==100)
						{
							for(int i=0;i<players.size();i++)
							{
								if(cI!=i && players.get(cI).getRect().intersects(players.get(i).getRect()))
								{
									players.get(cI).setIt();
									players.get(i).setIt();
									cooldown=0;
									d=true;
									writers.get(cI).println("NO");
									writers.get(i).println("YES");
									writers.get(i).println("STOP");
									
								}
							}
						}
						for(int i=0;i<writers.size();i++)
						{
							for(int x=0;x<players.size();x++)
							{
								if(!players.get(x).getName().equals(tempName))
									writers.get(i).println(players.get(x));
							}
						}
					}
					
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					socket.close();
				}
				catch(IOException e)
				{
					
				}
			}
		}
		

	}
}
