package tiles;

import javax.swing.ImageIcon;

public class TileStone extends Tile 
{
	public TileStone(int x, int y) 
	{
		super(x, y);
		this.image = new ImageIcon("graphics/tiles/stone.png");
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Stone";
		this.defBonus = 1;
	}

	public String toString() 
	{
		return type;
	}
}
