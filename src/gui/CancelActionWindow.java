package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Entity;

public class CancelActionWindow extends JFrame
{
	private JButton cancelButton = new JButton("Cancel");
	public Entity focusedEntity; //Used so that a listening button knows which entity to move
	
	public CancelActionWindow(final Entity entity, String option)
	{
		final JFrame self = this; //Used so that the cancel button can close the window
		focusedEntity = entity;
		
		if (option.equals("attack"))
		{
			this.setTitle("Attack");
			this.setIconImage(new ImageIcon("graphics/tiles/range.png").getImage());
		}
		else if (option.equals("move"))
		{
			this.setTitle("Move");
			this.setIconImage(new ImageIcon("graphics/tiles/move.png").getImage());
		}
		
		this.setMinimumSize(new Dimension(200, 100));
		this.setResizable(false);
		this.setLocation(Main.gameWindow.getX(), Main.gameWindow.getY());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		
		this.setLayout(new GridLayout(1, 1));
		this.add(cancelButton);
		
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				focusedEntity.removeRanged();
				self.dispose();
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		    	focusedEntity.removeRanged();
		    }
		});
		
		this.pack();
		this.setVisible(true);
	}
}
