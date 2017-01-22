package entities;

import gui.Team;

import java.util.Random;

import javax.swing.ImageIcon;


public class EntityKnight extends Entity
{
	private static final int MOVE_POINTS = 3;
	private static final int ATTACK_RANGE = 1;

	public EntityKnight(Team team, int x, int y)
	{
		super(team, x, y, MOVE_POINTS, ATTACK_RANGE);
		this.type = "Knight";
		this.canCapture = true;
		this.loadIcons();
		this.updateStats();
	}

	@Override
	public void setAttackPoints()
	{
		this.attackPoints = 3 * this.health;
	}

	@Override
	public void attack(Entity enemy)
	{
		Random ran = new Random();
		int chance = ran.nextInt(4);
		int damage = (this.getAttackPoints() - enemy.getDefensePoints());

		if (chance == 3)
		{
			damage = damage + (1 / 10 * damage);
		}

		if (damage > 0)
		{
			enemy.setHealth(enemy.getHealth() - damage);
		}
	}
	
	@Override
	protected void updateStats()
	{
		this.attackPoints = 3 * this.health;
		this.defensePoints = 2 * this.health;
	}
}
