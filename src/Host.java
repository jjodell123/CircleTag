import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Host extends JFrame implements Runnable{
	
	ServerSocket socket;
	
	public Host(String ip, String port)
	{
			this.setSize(300,200);
	        this.setLocationRelativeTo(null);
	        this.setTitle("Server");
	        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

	        JPanel mainPane = new JPanel();
	        mainPane.setLayout(new GridLayout(0,1));
	        mainPane.setBackground(Color.BLACK);
	        
	        JLabel mainJLabel1 = new JLabel("Server Hosted at "+ip+", access port: "+port);
	        mainJLabel1.setForeground(Color.WHITE);
	        mainPane.add(mainJLabel1);
	        
	        JLabel mainJLabel2 = new JLabel("Attempting Connection Establishment...");
	        mainJLabel2.setForeground(Color.WHITE);
	        mainPane.add(mainJLabel2);

	        this.add(mainPane);
	        this.setVisible(true);

	        try
	        {
	            socket = new ServerSocket(Integer.parseInt(port));
	        }
	        catch(Exception e)
	        {
	            JLabel youDoneFuckedUp = new JLabel(e.getMessage());
	            youDoneFuckedUp.setForeground(Color.RED);
	            mainPane.add(youDoneFuckedUp);
	        }
	        if(socket!=null)
	        {
	            JLabel mainJLabel3 = new JLabel("Connection Established!");

	            mainJLabel3.setForeground(Color.GREEN);

	            mainPane.add(mainJLabel3);

	            this.setVisible(false);
	            
	            new Server(socket);

	                

	        }

		
	}

	@Override
	public void run() {
		System.out.println("Is Running");
	}
}
