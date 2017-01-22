package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tiles.StructureBarracks;

public class BarracksWindow extends JFrame
{
	JPanel buyList;
	
	public BarracksWindow(StructureBarracks tile)
	{
		buyList = new BarracksPanel(tile);
		this.setTitle("Buy Menu");
		this.setIconImage(new ImageIcon("graphics/tiles/barracks_neutral.png").getImage());
		this.add(buyList);
		this.setLocation(Main.gameWindow.getX(), Main.gameWindow.getY());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);
	}
}
