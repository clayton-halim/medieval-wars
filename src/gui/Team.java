package gui;

import java.util.LinkedList;

import tiles.Structure;
import tiles.StructureTown;
import tiles.Tile;
import entities.Entity;

public class Team
{
	private String name;
	private int funds;
	private String colour;
	public Tile[][] map;
	public LinkedList<Tile> buildings = new LinkedList<>();
	public LinkedList<Entity> troops = new LinkedList<>();

	public Team(String name, String colour, int funds)
	{
		this.name = name;
		this.colour = colour;
		this.funds = funds;
		this.map = new Main().map;
	}
		
	/**
	 * RECURSION
	 * Collects all the funds from each structure owned by the team
	 * @param index
	 * @return total amount of funds from each structure
	 */
	private int countFunds(int index)
	{
		int total = 0;
		
		if (index == 0)
		{
			total = ((Structure) buildings.get(index)).getWorth();
		}
		else
		{
			total = total + ((Structure) buildings.get(index)).getWorth() + countFunds(index - 1);
		}
		
		return total;
	}
			
	public boolean hasHQ()
	{
		boolean exists = false;
		
		int i = 0;
		
		while (i < buildings.size() && !exists)
		{
			if (buildings.get(i).toString().equalsIgnoreCase("HQ"))
			{
				exists = true;
			}
			else
			{
				i++;
			}
		}
		
		return exists;
	}

	public int getFunds()
	{
		return this.funds;
	}

	public void setFunds(int funds)
	{
		this.funds = funds;
	}

	public String getColour()
	{
		return this.colour;
	}
	
	/**
	 * Does all the necessary actions when the team's turn starts.
	 * Collects funds and updates all the troops to be able to have actions again
	 */
	public void updateTeam()
	{
		for (Entity character : troops)
		{
			character.setWaited(false);
			character.updateIcon();
			
			if (character.getCurrentTile() != null && 
				character.getCurrentTile() instanceof StructureTown)
			{
				((StructureTown) character.getCurrentTile()).restoreHealth();
			}
		}
		
		this.funds += countFunds(buildings.size() - 1);
		GameWindow.teamInfo.updateStats(this);
	}

	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	public String toString()
	{
		return this.name;
	}
}
