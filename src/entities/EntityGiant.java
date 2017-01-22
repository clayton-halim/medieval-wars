package entities;

import gui.Team;

import java.util.Random;
import javax.swing.ImageIcon;

public class EntityGiant extends Entity
{
	private static final int MOVE_POINTS = 4;
	private static final int ATTACK_RANGE = 2;
	

	public EntityGiant(Team team, int x, int y)
	{
		super(team, x, y, MOVE_POINTS, ATTACK_RANGE);
		this.type = "Giant";
		this.canCapture = false;
		this.loadIcons();
		this.updateStats();
	}

	@Override
	public void setAttackPoints()
	{

		this.attackPoints = 50;
	}

	@Override
	public void setDefensePoints()
	{

		this.defensePoints = 30;
	}

	@Override
	public void attack(Entity enemy)
	{
		Random ran = new Random();

		int chance = ran.nextInt(6);

		int damage = (this.getAttackPoints() - enemy.getDefensePoints());

		// 1 in 5 chance for 20% damage boost

		if (chance == 5)
		{
			damage = damage + (2 / 10 * damage);
		}

		// So you don't heal the enemy

		if (damage > 0)
		{
			enemy.setHealth(enemy.getHealth() - damage);
		}
	}
}
