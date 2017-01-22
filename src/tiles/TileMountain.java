package tiles;

import javax.swing.ImageIcon;

public class TileMountain extends Tile
{
	public TileMountain(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/mountain.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Mountain";
		this.defBonus = 5;
		this.movePoints = 3;
	}

	public String toString()
	{
		return type;
	}
}