package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tiles.*;
import entities.Entity;

public class TileMenu extends JFrame
{
	private JPanel list = new JPanel();
	private JPanel actions = new JPanel();
	
	private JLabel tileCapt = new JLabel();
	
	
	public TileMenu(final Tile tile)
	{
		final Entity character = tile.getEntity();
		
		new Main();
		GameWindow temp = Main.gameWindow;
		
		//Window Settings
		this.setTitle("Action List");
		this.setIconImage(tile.getImage().getImage());
		this.setMinimumSize(new Dimension(125, 175));
		this.setResizable(false);
		this.setLocation(temp.getX(), temp.getY());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Panel Settings
		
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		
		
	    JLabel tileName = new JLabel(tile.toString());
		JLabel tileIcon = new JLabel(tile.getImage());
		JLabel tileDef = new JLabel("Def: " + String.valueOf(tile.getDefBonus()));
		JLabel tileMove = new JLabel("Move: " + String.valueOf(tile.getMovePoints()));
		
		tileName.setAlignmentX(CENTER_ALIGNMENT);		
		tileIcon.setAlignmentX(CENTER_ALIGNMENT);
		tileDef.setAlignmentX(CENTER_ALIGNMENT);
		tileMove.setAlignmentX(CENTER_ALIGNMENT);
		
		tileIcon.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		list.add(Box.createVerticalStrut(15));
		list.add(tileName);
		list.add(Box.createVerticalStrut(2));
		list.add(tileIcon);
		list.add(Box.createVerticalStrut(3));
		list.add(tileDef);
		list.add(Box.createVerticalStrut(3));
		list.add(tileMove);
		
		if (tile instanceof Structure)
		{
			tileCapt.setText("Capt: " + String.valueOf(((Structure) tile).getCaptureStrength()));
			tileCapt.setToolTipText("When this reaches 0, the structure belongs to the capturing team.");
			tileCapt.setAlignmentX(CENTER_ALIGNMENT);
			
			list.add(Box.createVerticalStrut(3));
			list.add(tileCapt);
		}
		
		if (character != null)
		{
			this.setMinimumSize(new Dimension(150, 300));
			
			JLabel charName = new JLabel(tile.getEntity().toString());
			JLabel charIcon = new JLabel(tile.getEntity().getIcon());
		    JLabel charHealth = new JLabel("Health: " + String.valueOf(tile.getEntity().getHealth()));
			JLabel charMove = new JLabel("Move: " + String.valueOf(tile.getMovePoints()));
			
			charName.setAlignmentX(CENTER_ALIGNMENT);
			charIcon.setAlignmentX(CENTER_ALIGNMENT);
			charHealth.setAlignmentX(CENTER_ALIGNMENT);
			charMove.setAlignmentX(CENTER_ALIGNMENT);
			
			charIcon.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			
			actions.setLayout(new GridLayout(2, 1));
			
			list.add(Box.createVerticalStrut(15));
			list.add(charName);
			list.add(Box.createVerticalStrut(2));
			list.add(charIcon);
			list.add(Box.createVerticalStrut(3));
			list.add(charHealth);
			list.add(Box.createVerticalStrut(3));
			list.add(charMove);
			list.add(Box.createVerticalStrut(15));
			
			if (character != null && !character.isWaited() && character.getTeam() == Main.turn)
			{
				this.setMinimumSize(new Dimension(150, 350));
				
				JButton waitButton = new JButton("Wait");
				JButton moveButton = new JButton("Move");
				JButton attackButton = new JButton("Attack");
				JButton captureButton = new JButton("Capture");
				
				list.add(actions);
				actions.add(waitButton);
				actions.add(moveButton);
				
				if  (character.canAttack())
				{
					actions.setLayout(new GridLayout(3, 1));
					actions.add(attackButton);
				}
				
				//If the tile is a structure and not owned by the player's team
				if (character.canCapture() &&
					tile instanceof Structure &&
				   ((Structure) tile).getOwner() != character.getTeam())
				{
					if (character.canAttack())
					{
						actions.setLayout(new GridLayout(4, 1));
					}
					else
					{
						actions.setLayout(new GridLayout(3, 1));
					}
					
					actions.add(captureButton);
				}
				
				waitButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						character.setWaited(true);
						GameWindow.tileMenu.dispose();
					}   
				});
				
				moveButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						character.findMoveRange();
						GameWindow.tileMenu.dispose();
						GameWindow.cancelActionWindow = new CancelActionWindow(tile.getEntity(), "move");
					}   
				});
				
				attackButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						character.findAttackRange();
						GameWindow.tileMenu.dispose();
						GameWindow.cancelActionWindow = new CancelActionWindow(tile.getEntity(), "attack");
					}   
				});
				
				captureButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						GameWindow.tileMenu.dispose();
						((Structure) character.getCurrentTile()).capture(character);
						character.setWaited(true);
					}   
				});
			}
		}
		
		//Other
		this.add(list);
		this.pack();
		this.setVisible(true);
	}
}
