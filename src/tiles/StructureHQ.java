package tiles;

import gui.Team;

import javax.swing.ImageIcon;


public class StructureHQ extends Structure 
{
	public StructureHQ(Team owner, int x, int y)
	{
		super(owner, x, y);
		
		if (owner.getColour().equalsIgnoreCase("Red"))
		{
			this.image = new ImageIcon("graphics/tiles/hq_red.png");
		}
		else if (owner.getColour().equalsIgnoreCase("Blue"))
		{
			this.image = new ImageIcon("graphics/tiles/hq_blue.png");
		}
		
		this.setIcon(image);
		this.owner = owner;
		this.worth = 100;
		this.defBonus = 5;
		this.type = "HQ";
		this.isPassable = true;
	}
}
