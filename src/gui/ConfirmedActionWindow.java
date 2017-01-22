package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tiles.Structure;
import entities.Entity;

public class ConfirmedActionWindow extends JFrame
{
	private JPanel contentPane;
	public JButton btnCancel = new JButton("Cancel");

	/**
	 * Create the frame.
	 */
	public ConfirmedActionWindow(final Entity entity, final int oldXPos, final int oldYPos)
	{
		final JFrame self = this; //So JButton can close this window
		
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 150, 300);
		this.setMaximumSize(new Dimension(150, 25));
		this.setLocation(Main.gameWindow.getX(), Main.gameWindow.getY());
		
		JButton btnMove = new JButton("Move");
		JButton btnAttack = new JButton("Attack");
		JButton btnCapture = new JButton("Capture");
		JButton btnJoin = new JButton("Join");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 15));
		
		this.setMinimumSize(new Dimension(150, 50));
		this.setMaximumSize(new Dimension(150, 50));
		contentPane.add(btnCancel);
		
		//If there is an entity of the same type and on the same team that has a lower health, the troops can join together
		if (entity.getCurrentTile().getAdditionalEntity() != null)
		{
			contentPane.add(btnJoin);		
		}
		else
		{
			contentPane.add(btnMove);
		}
		
		//If there is an enemy in the enitity's attack range
		if (entity.canAttack())
		{
			this.setMinimumSize(new Dimension(150, 75));
			this.setMaximumSize(new Dimension(150, 75));
			contentPane.add(btnAttack);
		}
		
		//If the entity has the ability to capture and if there's a structure to capture
		if (entity.canCapture() &&
				entity.getCurrentTile() instanceof Structure &&
			   ((Structure) entity.getCurrentTile()).getOwner() != entity.getTeam())
		{
				this.setMinimumSize(new Dimension(150, 75));
				this.setMaximumSize(new Dimension(150, 80));
				contentPane.add(btnCapture);
		}
		
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
					entity.getCurrentTile().setAdditionalEntity(null);
					entity.setXPos(oldXPos);
					entity.setYPos(oldYPos);
					self.dispose();		
					GameWindow.confirmedActionWindow = null;
			}
		});
		
		btnMove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				entity.setWaited(true);
				self.dispose();
				GameWindow.confirmedActionWindow = null;
			}
		});
		
		btnAttack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				entity.findAttackRange();
				GameWindow.tileMenu.dispose();
				GameWindow.cancelActionWindow = new CancelActionWindow(entity, "attack");
				entity.setWaited(true);
				self.dispose();
				GameWindow.confirmedActionWindow = null;
			}
		});
		
		btnCapture.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				((Structure) entity.getCurrentTile()).capture(entity);
				entity.setWaited(true);
				self.dispose();
				GameWindow.confirmedActionWindow = null;
			}
		});
		
		btnJoin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				entity.joinTroops();
				entity.setWaited(true);
				self.dispose();
				GameWindow.confirmedActionWindow = null;
			}
		});
		
		this.setVisible(true);
	}
}
