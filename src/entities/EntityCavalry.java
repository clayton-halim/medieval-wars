package entities;

import gui.Team;

public class EntityCavalry extends Entity
{
	private final static int MOVE_POINTS = 5;
	private final static int ATTACK_RANGE = 1;

	public EntityCavalry(Team team, int x, int y)
	{
		super(team, x, y, MOVE_POINTS, ATTACK_RANGE);
		this.type = "Cavalry";
		this.canCapture = true;
		this.loadIcons();
		this.updateStats();
	}

	public void updateStats()
	{
		this.attackPoints = 4 * this.getHealth();
		this.defensePoints = 3 * this.getHealth();
	}
}
