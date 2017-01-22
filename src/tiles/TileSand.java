package tiles;

import javax.swing.ImageIcon;

public class TileSand extends Tile
{
	public TileSand(int x, int y)
	{
		super(x, y);
		image = new ImageIcon("graphics/tiles/sand.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Sand";
		this.defBonus = 0;
		this.movePoints = 2;
	}
}
