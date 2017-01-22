package tiles;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import entities.Entity;
import gui.ConfirmedActionWindow;
import gui.GameWindow;
import gui.TileMenu;


@SuppressWarnings("serial")
public class Tile extends JButton implements ActionListener, MouseListener
{
	protected int xPos;
	protected int yPos;
	protected ImageIcon image;
	protected ImageIcon cursor;
	protected ImageIcon cursorSaved; //So that the tile doesn't have to keep on finding the icon
	protected ImageIcon moveSelected; //Shows which tiles player can move to
	protected ImageIcon rangeSelected; //Show which tiles player can attack
	protected Entity character;
	protected Entity additionalCharacter; //In the event another entity wants to join with the entity already present on this tile
	protected Structure building;
	protected boolean isPassable;
	protected boolean canMove; //Indicate whether or not to highlight the tile using the move or range png
	protected boolean inRange;
	protected boolean listening; //Used when waiting for a click to move an entity or for an attack
	protected String type;
	protected int defBonus;
	protected int movePoints; //Indicate how many move points it takes to cross this tile
	
	public Tile(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
		this.cursorSaved = new ImageIcon("graphics/tiles/cursor.png");
		this.moveSelected = new ImageIcon("graphics/tiles/move.png");
		this.rangeSelected = new ImageIcon("graphics/tiles/range.png");
		this.listening = false;
		this.setBorderPainted(false);
		this.addActionListener(this);
		this.addMouseListener(this);
		this.movePoints = 1; //Default to prevent errors when movePoints is not specified
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		System.out.printf("%s at (%d, %d) was clicked\n", type, xPos, yPos);
		
		try
		{
			GameWindow.tileMenu.dispose();
			
			//If the user is in the middle of moving a player
			if (GameWindow.confirmedActionWindow != null)
			{
				GameWindow.confirmedActionWindow.btnCancel.doClick();
			}
			else
			{
				GameWindow.confirmedActionWindow.dispose();
			}
		}
		catch(NullPointerException ex)
		{	
		}
		
		if (listening)
		{
			Entity selected = GameWindow.cancelActionWindow.focusedEntity;
			GameWindow.cancelActionWindow.dispose();
			
			if (canMove)
			{
				int oldX = selected.getXPos();
				int oldY = selected.getYPos();
				
				selected.setXPos(this.xPos);
				selected.setYPos(this.yPos);
				selected.removeRanged();
				
				GameWindow.confirmedActionWindow = new ConfirmedActionWindow(selected, oldX, oldY);
			}
			else if (inRange)
			{
				selected.attack(this.character);
				selected.removeRanged();
				selected.setWaited(true);
			}
		}
		else
		{
			GameWindow.tileMenu = new TileMenu(this);
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			GameWindow.tileMenu.setLocation(x, y);
		}	
	}
	
	public Entity getAdditionalEntity()
	{
		return this.additionalCharacter;
	}
	
	public int getDefBonus()
	{
		return this.defBonus;
	}
	
	public Entity getEntity()
	{
		return this.character;
	}
	
	public ImageIcon getImage()
	{
		return this.image;
	}

	public int getMovePoints()
	{
		return this.movePoints;
	}

	public Structure getStructure()
	{
		return this.building;
	}

	public int getXPos()
	{
		return this.xPos;
	}

	public int getYPos()
	{
		return this.yPos;
	}

	public boolean isCanMove()
	{
		return this.canMove;
	}
	
	public boolean isInRange()
	{
		return this.inRange;
	}
	
	public boolean isPassable()
	{
		return this.isPassable;
	}
	
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		cursor = cursorSaved;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		cursor = null;
	}

	@Override
	public void mousePressed(MouseEvent e){}
	
	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (this.character != null)
		{
			g.drawImage(this.character.getIcon().getImage(), 0, 0, 50, 50, null);
			
			if (this.character.getHealth() != 10)
			{
				g.drawImage(this.character.getUnitCount().getImage(), 0, 0, 50, 50, null);
			}
		}
		
		if (canMove)
		{
			g.drawImage(this.moveSelected.getImage(), 0, 0, 50, 50, null);
		}
		else if (inRange)
		{
			g.drawImage(this.rangeSelected.getImage(), 0, 0, 50, 50, null);
		}
		
		if (cursor != null)
		{
			g.drawImage(cursor.getImage(), 0, 0, 50, 50, null);
		}
	}
	
	public void setAdditionalEntity(Entity character)
	{
		this.additionalCharacter = character;	
	}
	
	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public void setEntity(Entity character)
	{
		this.character = character;
	}
	
	public void setInRange(boolean inRange)
	{
		this.inRange = inRange;
	}
	
	public void setListening(boolean listening)
	{
		this.listening = listening;
	}

	public void setStructure(Structure building)
	{
		this.building = building;
	}


	public String toString()
	{
		return type;
	}
}
