package entities;

import gui.Team;

import java.util.Random;

import javax.swing.ImageIcon;


public class EntityArcher extends Entity
{
	private final static int MOVE_POINTS = 4;
	private final static int ATTACK_RANGE = 5;
	
	public EntityArcher(Team team, int x, int y)
	{
		super(team, x, y, MOVE_POINTS, ATTACK_RANGE);
		this.type = "Archer";
		this.canCapture = true;
		this.loadIcons();
		this.updateStats();
	}

	public void updateStats()
	{
		this.attackPoints = 14 * this.getHealth();
		this.defensePoints = 2 * this.getHealth();
	}
}
