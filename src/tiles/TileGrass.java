package tiles;

import javax.swing.ImageIcon;

public class TileGrass extends Tile
{
	public TileGrass(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/grass.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Grass";
		this.defBonus = 1;
		this.movePoints = 1;
	}

	public String toString()
	{
		return type;
	}
}
