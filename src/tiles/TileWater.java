package tiles;

import javax.swing.ImageIcon;

public class TileWater extends Tile 
{
	public TileWater(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/water.png");
		this.setIcon(image);
		this.isPassable = false;
		this.type = "Water";
		this.defBonus = 0;
		this.movePoints = 1; //Only flying entities can move through
	}
	
	public String toString()
	{
		return type;
	}
}
