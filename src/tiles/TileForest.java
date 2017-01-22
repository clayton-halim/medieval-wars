package tiles;

import javax.swing.ImageIcon;

public class TileForest extends Tile
{
	public TileForest(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/forest.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Forest";
		this.defBonus = 3;
		this.movePoints = 2;
	}

	public String toString()
	{
		return type;
	}
}