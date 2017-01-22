package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import tiles.Tile;

public class GamePanel extends JPanel
{
	Tile[][] map = Main.map;
	
	public GamePanel()
	{
		this.setLayout(new GridLayout(16, 24));
		this.setMinimumSize(new Dimension(1200, 800));
		this.setPreferredSize(new Dimension(1200, 800));

		for (int r = 0; r < 16; r++)
		{
			for (int c = 0; c < 24; c++)
			{
				this.add(map[r][c]);
			}
		}
	}
}
