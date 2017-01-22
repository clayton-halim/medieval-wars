package tiles;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import entities.*;
import gui.BarracksWindow;
import gui.ConfirmedActionWindow;
import gui.GameWindow;
import gui.Main;
import gui.Team;
import gui.TileMenu;

public class StructureBarracks extends Structure
{
	public StructureBarracks(Team owner, int x, int y)
	{
		super(owner, x, y);

		if (owner.getColour().equalsIgnoreCase("Red"))
		{
			this.image = new ImageIcon("graphics/tiles/barracks_red.png");
		}
		else if (owner.getColour().equalsIgnoreCase("Blue"))
		{
			this.image = new ImageIcon("graphics/tiles/barracks_blue.png");
		}

		this.setIcon(image);
		this.owner = owner;
		this.worth = 25;
		this.defBonus = 5;
		this.type = "Barracks";
		this.isPassable = true;
		this.movePoints = 1;
	}

	public StructureBarracks(int x, int y)
	{
		super(x, y);
		this.image = new ImageIcon("graphics/tiles/barracks_neutral.png");
		this.setIcon(image);
		this.worth = 25;
		this.defBonus = 5;
		this.type = "Barracks";
		this.isPassable = true;
		this.movePoints = 1;
	}

	/**
	 * Buys troops for a team and places it on the map
	 * @param type - type of troop
	 * @return true or false, whether or not the purchase was successful
	 */
	public boolean buyTroops(String type)
	{
		Entity troops = null;
		boolean canBuy = false;

		if (type.equalsIgnoreCase("Knight") && owner.getFunds() >= getCharWorth(type))
		{
			troops = new EntityKnight(this.owner, this.xPos, this.yPos);
			canBuy = true;
		}
		else if (type.equalsIgnoreCase("Archer") && owner.getFunds() >= getCharWorth(type))
		{
			troops = new EntityArcher(this.owner, this.xPos, this.yPos);
			canBuy = true;
		}
		else if (type.equalsIgnoreCase("Cavalry") && owner.getFunds() >= getCharWorth(type))
		{
			troops = new EntityCavalry(this.owner, this.xPos, this.yPos);
			canBuy = true;
		}
		else if (type.equalsIgnoreCase("Giant") && owner.getFunds() >= getCharWorth(type))
		{
			troops = new EntityGiant(this.owner, this.xPos, this.yPos);
			canBuy = true;
		}
		else if (type.equalsIgnoreCase("Dragon") && owner.getFunds() >= getCharWorth(type))
		{
			troops = new EntityDragon(this.owner, this.xPos, this.yPos);
			canBuy = true;
		}
		
		if (canBuy)
		{
			this.character = troops;
			owner.troops.add(troops);
			owner.setFunds(owner.getFunds() - getCharWorth(troops.toString()));
			GameWindow.teamInfo.updateStats(owner);
		}
		
		return canBuy;
	}
	
	/**
	 * Returns how much it costs to purchase a troop
	 * @param type - type of troop
	 * @return the troops worth
	 */
	public int getCharWorth(String type)
	{
		int worth = 0;
		
		if (type.equalsIgnoreCase("Archer"))
		{
			worth = 400;
		}
		else if (type.equalsIgnoreCase("Cavalry"))
		{
			worth = 600;
		}
		else if (type.equalsIgnoreCase("Dragon"))
		{
			worth = 1400;
		}
		else if (type.equalsIgnoreCase("Giant"))
		{
			worth = 1200;
		}
		else if (type.equalsIgnoreCase("Knight"))
		{
			worth = 200;
		}
		
		return worth;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		System.out.printf("%s at (%d, %d) was clicked\n", type, xPos, yPos);
		
		try
		{
			GameWindow.tileMenu.dispose();
			GameWindow.barracksWindow.dispose();
			
			//If the user is in the middle of moving a player
			if (GameWindow.confirmedActionWindow != null)
			{
				GameWindow.confirmedActionWindow.btnCancel.doClick();
			}
			else
			{
				GameWindow.confirmedActionWindow.dispose();
			}
		}
		catch(NullPointerException ex)
		{	
		}
		
		if (listening)
		{
			Entity selected = GameWindow.cancelActionWindow.focusedEntity;
			GameWindow.cancelActionWindow.dispose();
			
			if (canMove)
			{
				int oldX = selected.getXPos();
				int oldY = selected.getYPos();
				
				selected.setXPos(this.xPos);
				selected.setYPos(this.yPos);
				selected.removeRanged();
				
				GameWindow.confirmedActionWindow = new ConfirmedActionWindow(selected, oldX, oldY);
			}
			else if (inRange)
			{
				selected.attack(this.character);
				selected.removeRanged();
			}
		}
		else
		{
			if (this.character != null || this.owner == null || Main.turn != owner)
			{
				GameWindow.tileMenu = new TileMenu(this);
			}
			else 
			{
				GameWindow.barracksWindow = new BarracksWindow(this);
			}
		}
	}
}
