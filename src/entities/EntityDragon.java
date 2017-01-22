package entities;

import gui.Main;
import gui.Team;
import tiles.Tile;

public class EntityDragon extends Entity
{
	private static final int MOVE_POINTS = 5;
	private static final int ATTACK_RANGE = 2;
	
	public EntityDragon(Team team, int x, int y) 
	{
		super(team, x, y, MOVE_POINTS, ATTACK_RANGE);
		this.type = "Dragon";
		this.canCapture = false;
		this.loadIcons();
		this.updateStats();
	}
	
	/**
	 * RECURSION
	 * Checks whether a tile or not can be traversed on by the entity and then checks
	 * each tile beside it if there are move points remaining
	 * @param x
	 * @param y
	 * @param movePoints
	 */
	protected void moveCheck(int x, int y, int movePoints)
	{
		Tile currentTile = Main.map[y][x];
		Entity currentEntity = currentTile.getEntity();
		
		//Subtracts by the amount of move points required to cross
		movePoints -= currentTile.getMovePoints(); 
		
		//If you cannot move anymore due to running out of move points or encountering the enemy
		if (movePoints >= -1 && 
		   (currentEntity == null || currentEntity.getTeam() == this.team))
		{
			//If the tile is empty or if the tile contains an entity of the same type with less than full health
			if (currentEntity == null ||
			   (currentEntity.toString() == this.type &&
			    currentEntity.getTeam() == this.team &&
			    currentEntity.getHealth() < 10))
			{
				currentTile.setListening(true);
				currentTile.setCanMove(true);	
				rangedList.add(currentTile);	
			}
				
			try
			{
				moveCheck(x, y + 1, movePoints); //Up
			}
			catch (IndexOutOfBoundsException e){}
			
			try
			{
				moveCheck(x, y - 1, movePoints); //Down
			}
			catch (IndexOutOfBoundsException e){}
			
			try
			{
				moveCheck(x - 1, y, movePoints); //Left
			}
			catch (IndexOutOfBoundsException e){}
			
			try
			{
				moveCheck(x + 1, y, movePoints); //Right
			}
			catch (IndexOutOfBoundsException e){}	
		}
	}
	
	public void updateStats()
	{
		this.attackPoints = 5 * this.getHealth();
		this.defensePoints = 3 * this.getHealth();
	}
}
