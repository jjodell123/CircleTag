import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Client extends JFrame implements Runnable{
	
	private Socket socket;
	private GameHandler game;
	private Thread t=new Thread(this);
	private static PrintWriter out;
	private BufferedReader in;
	private static String name;
	private static ArrayList<String> recognized=new ArrayList<String>();
	
	public Client(String hostIp, String hostPort)
	{
		this.setSize(300,200);
		this.setLocationRelativeTo(null);
		this.setTitle("Client");
		this.setLayout(new GridLayout(0,1));
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new GridLayout(0,1));
		mainPane.setBackground(Color.BLACK);
		JLabel label1 = new JLabel("Attempting Connection at "+hostIp);
		label1.setForeground(Color.WHITE);
		mainPane.add(label1);
		JLabel label2 = new JLabel("on port "+hostPort);
		label2.setForeground(Color.WHITE);
		mainPane.add(label2);

		this.add(mainPane);

		this.setVisible(true);
		
		try
		{
			Thread.sleep(300);
			socket = new Socket(hostIp, Integer.parseInt(hostPort));
		}
		catch(Exception e/*IO Exception applic...*/)
		{
			JLabel error = new JLabel("Connection failed");
			error.setForeground(Color.RED);
			mainPane.add(error);
		}
		if(socket!=null)
		{
			JLabel label3 = new JLabel("Connection established!");
			label3.setForeground(Color.GREEN);
			mainPane.add(label3);
			name=JOptionPane.showInputDialog(JOptionPane.INFORMATION_MESSAGE,"Enter player name");
			if(name==null)
				name="s";
			t.start();
		}
	}

	public void run() {
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			//JOptionPane.showMessageDialog(null,name);
			out.println(name);
			
			while(true)
			{
				name=in.readLine();
				if(name!=null && (name.indexOf(":")<0))
				{
					//System.out.println(why)
				//JOptionPane.showMessageDialog(null,name);
				break;
				}
			}
			game=new GameHandler(name);
			while(true)
			{
				
				if(game.getPlayScreen())
				{
					String line=in.readLine();
					if(line==null)
						return;
					if(line.equals("NO")||line.equals("YES"))
					{
						game.setIt();
						//return;
					}
					else if(line.equals("STOP")||line.equals("START"))
					{
						game.setStop();
					}
					else
					{
					boolean test=false;
					if(line.indexOf(":")<0)
						System.out.println(line);
					String tempName=line.substring(0,line.indexOf(":"));
					if(line==null)
						return;
					for(int i=0;i<recognized.size();i++)
					{
						if(recognized.get(i).equals(tempName))
							test=true;
					}
					if(test && !name.equals(tempName))
					{
						line=line.substring(line.indexOf(":")+1);
						String tempX=(line.substring(0,line.indexOf(",")));
						line=line.substring(line.indexOf(",")+1);
						String tempY=(line.substring(0,line.indexOf(",")));
						line=line.substring(line.indexOf(",")+1);
						String itt=line;
						game.updateEnemy(tempName, tempX, tempY,itt);
					}
					else if(!test && !name.equals(tempName))
					{
						recognized.add(tempName);
						line=line.substring(line.indexOf(":")+1);
						String tempX=(line.substring(0,line.indexOf(",")));
						line=line.substring(line.indexOf(",")+1);
						String tempY=(line.substring(0,line.indexOf(",")));
						line=line.substring(line.indexOf(",")+1);
						String itt=line;
						game.addEnemy(tempName, tempX, tempY,itt);
					}		
				}
				}
			}
			//
			
			
		}
		catch(IOException e)
		{       
		}
		finally
		{




			try 
			{
				socket.close();
			} 
			catch (IOException e)
			{
			}
		}   
		
	}
	
	public static void update(int x,int y)
	{
		//System.out.println(name+":"+x+","+y);
		out.println(name+":"+x+","+y);
	}

}
