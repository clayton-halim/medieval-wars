package tiles;

import javax.swing.ImageIcon;

public class TileBridge extends Tile
{	
	public TileBridge(String direction, int x, int y)
	{
		super(x, y);
		
		if (direction.equalsIgnoreCase("up/down"))
		{
			image = new ImageIcon("graphics/tiles/bridge_up_down.png");
		}
		else if (direction.equalsIgnoreCase("left/right"))
		{
			image = new ImageIcon("graphics/tiles/bridge_left_right.png");
		}
		
		this.setIcon(image);
		this.isPassable = true;
		this.type = "Bridge";
		this.defBonus = 2;
		this.movePoints = 1;
	}

	public String toString()
	{
		return type;
	}
}