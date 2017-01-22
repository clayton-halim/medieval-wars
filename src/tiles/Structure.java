package tiles;

import javax.swing.ImageIcon;

import entities.Entity;
import gui.Team;

@SuppressWarnings("serial")
public class Structure extends Tile
{
	protected Team owner;
	protected int captureStrength;
	protected int worth;

	public Structure(int x, int y)
	{
		super(x, y);
		this.isPassable = true;
		this.captureStrength = 20;
	}

	public Structure(Team owner, int x, int y)
	{
		super(x, y);
		this.isPassable = true;
		this.owner = owner;
		this.captureStrength = 20;
	}

	/**
	 * Changes the owner of the structure to the another team
	 * 
	 * @param character
	 *            - a character on the map that is at the structure's location
	 */
	public void capture(Entity character)
	{
		this.captureStrength -= character.getHealth();

		if (captureStrength <= 0)
		{
			if (this.owner != null)
			{
				this.owner.buildings.remove(character.getCurrentTile());
			}
	
			this.owner = character.getTeam();
			this.character.getTeam().buildings.add(character.getCurrentTile());
			this.switchColour();
			this.resetCaptureStrength();
		}
	}

	public int getCaptureStrength()
	{
		return this.captureStrength;
	}

	public Team getOwner()
	{
		return this.owner;
	}

	public int getWorth()
	{
		return this.worth;
	}

	/**
	 * Checks whether or not a capture is taking place, if not then reset the
	 * capture strength
	 */
	public void resetCaptureStrength()
	{
		if (this.character == null || this.captureStrength < 20)
		{
			this.captureStrength = 20;
		}
	}

	protected void switchColour()
	{

		this.image = new ImageIcon("graphics/tiles/" + this.type.toLowerCase()
									+ "_" + owner.getColour().toLowerCase() + ".png");
		this.setIcon(image);
	}
}
