package entities;

import gui.Main;
import gui.Team;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import tiles.Tile;

public class Entity
{
	protected ImageIcon currentIcon; // Icon to be displayed on the grid
	protected ImageIcon regularIcon; // Used to indicate that the entity can still do something
	protected ImageIcon waitedIcon; // Used to indicate that the entity cannot do anything until the next round
	protected ImageIcon unitCount;
	protected int health;
	protected int xPos;
	protected int yPos;
	protected int attackPoints;
	protected int defensePoints;
	protected Tile currentTile;
	protected Team team;
	protected String type;
	protected boolean waited; // If true, entity cannot do anything until it is their turn again
	protected boolean canCapture; //Indicates whether or not the entity has the ability to capture structures
	protected LinkedList<Tile> rangedList = new LinkedList<>(); 
	// Lists all the tiles where the entity can attack or move
	
	protected final int MOVE_POINTS;
	protected final int ATTACK_RANGE;

	public Entity(int x, int y, int movePoints, int attackRange)
	{
		this.xPos = x;
		this.yPos = y;
		this.currentTile = this.team.map[y][x];
		this.waited = false;
		this.MOVE_POINTS = movePoints;
		this.ATTACK_RANGE = attackRange;
	}

	public Entity(Team team, int x, int y, int movePoints, int attackRange)
	{
		this.health = 10;
		this.team = team;
		this.xPos = x;
		this.yPos = y;
		this.currentTile = this.team.map[y][x];
		this.waited = true;
		this.MOVE_POINTS = movePoints;
		this.ATTACK_RANGE = attackRange;
	}

	/**
	 * Decreases the health of a specified entity and the current entity from a counter attack
	 * @param enemy - another entity
	 */
	public void attack(Entity enemy)
	{
		Random test = new Random();
		int bonusDmg = test.nextInt(2);
		int counterDmg = test.nextInt(2) + 2;
		
		//Attacking the enemy
		int damage = (this.getAttackPoints() + bonusDmg - enemy.getDefensePoints());
		
		if (damage > 0)
		{
			enemy.setHealth(enemy.getHealth() - damage);
		}
		
		//Counter attack if fighting a direct unit
		if (!(enemy instanceof EntityArcher))
		{
			damage = (enemy.getAttackPoints() - counterDmg - this.getDefensePoints());
			
			if (damage > 0)
			{
				this.setHealth(this.health - damage);
			}
		}
	}
	
	/**
	 * Checks to see if there is another entity in range to attack
	 * @return - true or false whether or not there is an entity in range
	 */
	public boolean canAttack()
	{
		boolean canAttack = false;
		int horizontal = 0;
		Tile currentTile = null;
		
		int max = this.ATTACK_RANGE;
		
		while (max >= 0 && !canAttack)
		{
			int vertical = max;
			
			while (vertical >= 0 && !canAttack)
			{
				horizontal = max - vertical;
				
				try
				{
					// Checks top right of character
					currentTile = Main.map[yPos + vertical][xPos + horizontal];
							
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						canAttack = true;
					}
				}
				catch (IndexOutOfBoundsException e)
				{
					// Expected to happen at times when the character is at the
					// edge of the map
				}
	
				try
				{
					// Checks top left of character
					currentTile = Main.map[yPos + vertical][xPos - horizontal];
					
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						canAttack = true;
					}
				}
				catch (IndexOutOfBoundsException e){}
	
				try
				{
					// Checks bottom right of character
					currentTile = Main.map[yPos - vertical][xPos + horizontal];
		
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						canAttack = true;
					}
				}
				catch (IndexOutOfBoundsException e){}
	
				try
				{
					// Checks bottom left of character
					currentTile = Main.map[yPos - vertical][xPos - horizontal];
	
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						canAttack = true;
					}
				}
				catch (IndexOutOfBoundsException e){}
				
				vertical--;
			}
			
			max--;
		}
		
		return canAttack;
	}
	
	public boolean canCapture()
	{
		return this.canCapture;
	}

	/**
	 * Finds all possible entities in its range that the entity can attack 
	 */
	public void findAttackRange()
	{
		int horizontal = 0;
		Tile currentTile = null;

		for (int max = this.ATTACK_RANGE; max >= 0 ; max--)
		{
			for (int vertical = max; vertical >= 0 ; vertical--)
			{
				horizontal = max - vertical;
			
				try
				{
					// Checks top right of character
					currentTile = Main.map[yPos + vertical][xPos + horizontal];
							
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						currentTile.setListening(true);
						currentTile.setInRange(true);
						rangedList.add(currentTile);
					}
				}
				catch (IndexOutOfBoundsException e)
				{
					// Expected to happen at times when the character is at the
					// edge of the map
				}
	
				try
				{
					// Checks top left of character
					currentTile = Main.map[yPos + vertical][xPos - horizontal];
					
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						currentTile.setListening(true);
						currentTile.setInRange(true);
						rangedList.add(currentTile);
					}
				}
				catch (IndexOutOfBoundsException e){}
	
				try
				{
					// Checks bottom right of character
					currentTile = Main.map[yPos - vertical][xPos + horizontal];
		
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						currentTile.setListening(true);
						currentTile.setInRange(true);
						rangedList.add(currentTile);
					}
				}
				catch (IndexOutOfBoundsException e){}
	
				try
				{
					// Checks bottom left of character
					currentTile = Main.map[yPos - vertical][xPos - horizontal];
	
					if (currentTile != null && currentTile.getEntity() != null && currentTile.getEntity().getTeam() != this.team)
					{
						currentTile.setListening(true);
						currentTile.setInRange(true);
						rangedList.add(currentTile);
					}
				}
				catch (IndexOutOfBoundsException e){}
			}
		}
	}
	
	/**
	 * Finds all possible tiles that the entity can move and selects them to display
	 */
	public void findMoveRange()
	{
		try
		{
			moveCheck(this.xPos, this.yPos + 1, this.MOVE_POINTS - 1); //Up
		}
		catch (IndexOutOfBoundsException e){}
		
		try
		{
			moveCheck(this.xPos, this.yPos - 1, this.MOVE_POINTS - 1); //Down
		}
		catch (IndexOutOfBoundsException e){}
		
		try
		{
			moveCheck(this.xPos - 1, this.yPos, this.MOVE_POINTS - 1); //Left
		}
		catch (IndexOutOfBoundsException e){}
		
		try
		{
			moveCheck(this.xPos + 1, this.yPos, this.MOVE_POINTS - 1); //Right
		}
		catch (IndexOutOfBoundsException e){}
		
		if (rangedList.contains(this.currentTile))
		{
			currentTile.setListening(false);
			currentTile.setCanMove(false);
			rangedList.remove(this.currentTile);
		}
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
		
		//If you cannot move anymore due to running out of move points, encountering the
		//enemy or encountering an impassable tile, then the method stops checking
		if (movePoints >= -1 && currentTile.isPassable() && 
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
	
	public int getAttackPoints()
	{
		return attackPoints;
	}

	public Tile getCurrentTile()
	{
		return currentTile;
	}

	public int getDefensePoints()
	{
		return defensePoints;
	}

	public int getHealth()
	{
		return health;
	}

	public ImageIcon getIcon()
	{
		return this.currentIcon;
	}

	public Team getTeam()
	{
		return team;
	}

	public ImageIcon getUnitCount()
	{
		return this.unitCount;
	}

	public int getXPos()
	{
		return this.xPos;
	}

	public int getYPos()
	{
		return this.yPos;
	}

	public boolean isWaited()
	{
		return this.waited;
	}

	public void joinTroops()
	{
		Entity toJoin = currentTile.getAdditionalEntity();
		
		if (this.health + toJoin.getHealth() > 10)
		{
			this.health = 10;
		}
		else
		{
			this.health += toJoin.getHealth();
		}
		
		team.troops.remove(toJoin);
		this.currentTile.setAdditionalEntity(null);
	}

	/**
	 * Only used once to grab the icons for the entity
	 */
	protected void loadIcons()
	{
		this.regularIcon = new ImageIcon("graphics/characters/"
				+ type.toLowerCase() + "_" + team.getColour().toLowerCase()
				+ ".png");
		this.waitedIcon = new ImageIcon("graphics/characters/"
				+ type.toLowerCase() + "_" + team.getColour().toLowerCase()
				+ "_waited.png");

		this.updateIcon();
	}
	
	/**
	 * Deselects all the tiles on the entity's ranged list for both moving and attacking 
	 */
	public void removeRanged()
	{
		for (int i = 0, len = rangedList.size(); i < len; i++)
		{ 
			rangedList.getFirst().setListening(false);
			rangedList.getFirst().setCanMove(false);
			rangedList.getFirst().setInRange(false);
			rangedList.remove();
		}
	}

	public void setAttackPoints()
	{
		this.attackPoints = 2 * this.health;
	}

	public void setCurrentTile(Tile tile)
	{
		this.currentTile = tile;
	}

	public void setDefensePoints()
	{
		this.defensePoints = 2 * this.health;
	}

	/**
	 * Changes the health of the entity, if health is below 0, the entity dies
	 * 
	 * @param newHealth
	 */
	public void setHealth(int newHealth)
	{
		this.health = newHealth;

		if (this.health == 1)
		{
			unitCount = new ImageIcon("graphics/characters/1.png");
		}
		else if (this.health == 2)
		{
			unitCount = new ImageIcon("graphics/characters/2.png");
		}
		else if (this.health == 3)
		{
			unitCount = new ImageIcon("graphics/characters/3.png");
		}
		else if (this.health == 4)
		{
			unitCount = new ImageIcon("graphics/characters/4.png");
		}
		else if (this.health == 5)
		{
			unitCount = new ImageIcon("graphics/characters/5.png");
		}
		else if (this.health == 6)
		{
			unitCount = new ImageIcon("graphics/characters/6.png");
		}
		else if (this.health == 7)
		{
			unitCount = new ImageIcon("graphics/characters/7.png");
		}
		else if (this.health == 8)
		{
			unitCount = new ImageIcon("graphics/characters/8.png");
		}
		else if (this.health == 9)
		{
			unitCount = new ImageIcon("graphics/characters/9.png");
		}
		else
		{
			unitCount = null;
		}

		if (this.health <= 0)
		{
			currentTile.setEntity(null);
			team.troops.remove(this);
		}
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	/**
	 * Sets the entity state to waiting mode, the entity will not be able to do anything
	 * until its team's next turn
	 * @param waited
	 */
	public void setWaited(boolean waited)
	{
		this.waited = waited;
		this.updateIcon();
	}

	/**
	 * Changes the x coordinate of the entity
	 * @param newXPos
	 */
	public void setXPos(int newXPos)
	{
		this.currentTile.setEntity(null);
		this.xPos = newXPos;
		
		//In the event that the entity might want to joining with another entity, don't overwrite the currentTile yet
		if (Main.map[this.yPos][newXPos].getEntity() != null)
		{
			Main.map[this.yPos][this.xPos].setAdditionalEntity(this);		
		}
		else
		{
			Main.map[this.yPos][this.xPos].setEntity(this);	
		}
		
		this.currentTile = Main.map[this.yPos][this.xPos];
		this.updateStats();
	}

	/**
	 * Changes the y coordinate of the entity
	 * @param newYPos
	 */
	public void setYPos(int newYPos)
	{
		this.currentTile.setEntity(null);
		this.yPos = newYPos;
		
		//In the event that the entity might want to joining with another entity, don't overwrite the currentTile yet
		if (Main.map[newYPos][this.xPos].getEntity() != null)
		{
			Main.map[this.yPos][this.xPos].setAdditionalEntity(this);
		}
		else
		{
			Main.map[this.yPos][this.xPos].setEntity(this);
		}
		
		this.currentTile = Main.map[this.yPos][this.xPos];
		this.updateStats();
	}

	public String toString()
	{
		return this.type;
	}

	/**
	 * Changes the icon of the entity representing its current state
	 */
	public void updateIcon()
	{
		if (waited)
		{
			this.currentIcon = this.waitedIcon;
		}
		else
		{
			this.currentIcon = this.regularIcon;
		}
	}

	protected void updateStats()
	{
		// Will be overridden by child classes
		this.attackPoints = this.health;
		this.defensePoints = this.health;
	}
}
