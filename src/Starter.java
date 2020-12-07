import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Starter extends JFrame{
	
	public Starter()
	{
		this.setSize(400,250);
		
		this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setTitle("Server Starter");

        this.setResizable(false);

        this.setLayout(new GridLayout(3,1));
        
        JPanel top=new JPanel();
        top.setBackground(new Color(0,0,0));
        top.setLayout(new FlowLayout());
        
        String local_ip="";
        try
        {
            local_ip=Inet4Address.getLocalHost().getHostAddress();
        }
        catch(Exception e)
        {
            e.printStackTrace(); 
        }
        
        final String holder=local_ip;
        
        JLabel showIp=new JLabel("Circle Tag");
        showIp.setFont(new Font("Serif", Font.PLAIN, 35));
        showIp.setForeground(new Color(255,255,255));
        top.add(showIp);
        
        this.add(top);
        
        JPanel middle = new JPanel();
        
      // middle.setBounds(0,50,400,150);
        middle.setBackground(new Color(255,255,255));
        middle.setLayout(new GridLayout(1,2));
        
        JPanel mLeft=new JPanel();
        mLeft.setLayout(new GridLayout(2,1));
        
        middle.add(mLeft);
        
        JPanel mt=new JPanel();
        JPanel mb=new JPanel();
        
        JLabel setPort=new JLabel("Select Port:");
        JTextField setPBox=new JTextField("",17);
        
        mt.setBackground(Color.white);
        mb.setBackground(Color.white);

        mt.add(setPort);
        mb.add(setPBox);
        
        mLeft.add(mt);
        mLeft.add(mb);
        
        JPanel bottom=new JPanel();
        
        bottom.setBackground(new Color(255,255,255));
        bottom.setLayout(new GridLayout(1,2));
        
        JPanel bLeft=new JPanel();
        bLeft.setLayout(new GridLayout(2,1));
        bottom.add(bLeft);
        
        JPanel bt=new JPanel();
        JPanel bb=new JPanel();
        
        JLabel selectIP=new JLabel("Game's IP:");
        JTextField sIPBox=new JTextField(""+local_ip,11);
        
        bt.setBackground(Color.white);
        bt.add(selectIP);
        bt.add(sIPBox);
        
        JLabel selectPort=new JLabel("Game's Port:");
        JTextField sPBox=new JTextField("",10);
        
        bb.setBackground(Color.white);
        bb.add(selectPort);
        bb.add(sPBox);
     //   bt.add
        
        bLeft.add(bt);
        bLeft.add(bb);
        
        JButton tb=new JButton("Start Game");
        
        JButton lb=new JButton("Join Game");

        tb.setPreferredSize(new Dimension(230,60));
        tb.setContentAreaFilled(false);
        tb.setFocusable(false);
        
        tb.addActionListener(new ActionListener()
        		{
        			public void actionPerformed(ActionEvent e)
        			{
        				new Thread(new Host(holder,setPBox.getText()));
        				tb.setEnabled(false);
        				setPBox.setEnabled(false);
        			}
        		});
        
        lb.setPreferredSize(new Dimension(230,60));
        lb.setContentAreaFilled(false);
        lb.setFocusable(false);
        
        lb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new Thread(new Client(sIPBox.getText(),sPBox.getText()));
				lb.setEnabled(false);
				sIPBox.setEnabled(false);
				sPBox.setEnabled(false);
			}
		});
               
        middle.add(tb);
        bottom.add(lb);
        
        this.add(middle);
        this.add(bottom);
        
        this.setVisible(true);
	}

	
	public static void main(String[]arg)
	{
		Starter s=new Starter();
	}
}
