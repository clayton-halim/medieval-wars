package tiles;

import javax.swing.ImageIcon;

public class TileDirt extends Tile
{
	public TileDirt(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/dirt.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Dirt";
		this.defBonus = 0;
		this.movePoints = 1;
	}

	public String toString()
	{
		return type;
	}
}