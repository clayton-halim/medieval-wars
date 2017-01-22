package tiles;

import gui.Team;

import javax.swing.ImageIcon;


public class StructureTown extends Structure
{
	public StructureTown(Team owner, int x, int y)
	{
		super(owner, x, y);
		
		if (owner.getColour().equalsIgnoreCase("Red"))
		{
			this.image = new ImageIcon("graphics/tiles/town_red.png");
		}
		else if (owner.getColour().equalsIgnoreCase("Blue"))
		{
			this.image = new ImageIcon("graphics/tiles/town_blue.png");
		}
		
		this.setIcon(image);
		this.owner = owner;
		this.worth = 75;
		this.defBonus = 4;
		this.type = "Town";
		this.isPassable = true;
	}
	
	public StructureTown(int x, int y)
	{
		super(x, y);
		this.image = new ImageIcon("graphics/tiles/town_neutral.png");
		this.setIcon(image);
		this.worth = 75;
		this.defBonus = 4;
		this.type = "Town";
		this.isPassable = true;
	}
	
	/**
	 * Called when the entity is at a town and the team's turn starts.
	 * Heals the entity by 2 points but does not go over 10.
	 * The team must pay G. 50 to heal the entity
	 */
	public void restoreHealth()
	{
		if (this.owner != null && this.character.getTeam() == this.owner)
		{
			int health = this.character.getHealth();
			
			if (this.owner.getFunds() >= 50)
			{
				if (health < 9)
				{
					this.character.setHealth(health + 2);
				}
				else if (health == 9)
				{
					this.character.setHealth(health + 1);
				}
			}	
		}	
	}
}
