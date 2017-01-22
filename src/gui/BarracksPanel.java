package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import tiles.StructureBarracks;

public class BarracksPanel extends JPanel
{
	private static final long serialVersionUID = -3377410985468127613L;
	
	private JLabel[] names = new JLabel[5];
	private JLabel[] prices = new JLabel[5];
	private ImageIcon[] entityIcons = new ImageIcon[5];
	private final JRadioButton[] choices = new JRadioButton[5];
	private ButtonGroup buyList = new ButtonGroup();
	private JButton buyButton = new JButton("Buy");
	
	private JPanel choicePanel = new JPanel(new GridLayout(choices.length, 3));
	
	public BarracksPanel(final StructureBarracks tile)
	{
		String path = "graphics/characters/";
		
		this.setPreferredSize(new Dimension(300, 400));
		this.setLayout(new BorderLayout());
		
		String teamColour = Main.turn.getColour().toLowerCase();
		
		entityIcons[0] = new ImageIcon(path + "archer_" + teamColour + ".png");
		entityIcons[1] = new ImageIcon(path + "knight_" + teamColour + ".png");
		entityIcons[2] = new ImageIcon(path + "dragon_" + teamColour + ".png");
		entityIcons[3] = new ImageIcon(path + "giant_" + teamColour + ".png");
		entityIcons[4] = new ImageIcon(path + "cavalry_" + teamColour + ".png");

		names[0] = new JLabel("Archer", entityIcons[0], JLabel.LEADING);
		names[1] = new JLabel("Knight", entityIcons[1], JLabel.LEADING);
		names[2] = new JLabel("Dragon", entityIcons[2], JLabel.LEADING);
		names[3] = new JLabel("Giant", entityIcons[3], JLabel.LEADING);
		names[4] = new JLabel("Cavalry", entityIcons[4], JLabel.LEADING);
		
		prices[0] = new JLabel(String.valueOf(tile.getCharWorth("Archer")));	
		prices[1] = new JLabel(String.valueOf(tile.getCharWorth("Knight")));  
		prices[2] = new JLabel(String.valueOf(tile.getCharWorth("Dragon")));  
		prices[3] = new JLabel(String.valueOf(tile.getCharWorth("Giant"))); 	
		prices[4] = new JLabel(String.valueOf(tile.getCharWorth("Cavalry")));	
		
		for (int i = 0; i < choices.length; i++)
		{
			choices[i] = new JRadioButton();
			choices[i].setSize(10, 10);
			
			buyList.add(choices[i]);
			
			choicePanel.add(names[i]);
			choicePanel.add(prices[i]);
			choicePanel.add(choices[i]);
		}

		choices[0].setSelected(true);
		
		this.add(choicePanel, BorderLayout.CENTER);
		this.add(buyButton, BorderLayout.SOUTH);

		buyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = 0;
				boolean found = false;
				
				while (index < choices.length && !found)
				{
					if (choices[index].isSelected())
					{
						found = true;
					}
					else
					{
						index++;
					}
				}
				
				boolean bought = false;
				
				if (index == 0)
				{
					System.out.println("Archer was bought");
					bought = tile.buyTroops("Archer");
				}
				else if (index == 1)
				{
					System.out.println("Knight was bought");
					bought = tile.buyTroops("Knight");
				}
				else if (index == 2)
				{
					System.out.println("Dragon was bought");
					bought = tile.buyTroops("Dragon");
				}
				else if (index == 3)
				{
					System.out.println("Giant was bought");
					bought = tile.buyTroops("Giant");
				}
				else if (index == 4)
				{
					System.out.println("Cavalry was bought");
					bought = tile.buyTroops("Cavalry");
				}
				
				if (bought)
				{
					GameWindow.barracksWindow.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Not enough funds.", "Cannot Buy", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
	}
}
