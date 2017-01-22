package gui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tiles.Tile;

public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static TileMenu tileMenu; //Window for giving information about a tile and an entity if one is present
	public static BarracksWindow barracksWindow; //Window for selling troops
	public static CancelActionWindow cancelActionWindow; //Window for canceling the move or attack function
	public static ConfirmedActionWindow confirmedActionWindow; //Window for extra actions after moving or canceling the move
	public static TeamWindow teamInfo; //Window to display the team's info and the current day
	public static CardLayout switcher = new CardLayout();
	public static JPanel mainPanel = new JPanel();
	public static JPanel startPanel = new MainMenuPanel();
	public static JPanel levelPanel = new LevelSelectPanel();
	public static JPanel gamePanel;
	protected Tile[][] map;

	public GameWindow()
	{
		map = Main.map;

		this.setTitle("Medieval Wars");
		ImageIcon frameIcon = new ImageIcon("graphics/characters/dragon_red.png");
		this.setIconImage(frameIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusable(true);

		Background updater = new Background(this);
		updater.start();
		
		mainPanel.setLayout(switcher);
		mainPanel.add(startPanel, "Main Menu");
		mainPanel.add(levelPanel, "Level Select");
		mainPanel.setMinimumSize(new Dimension(1200, 800));
		mainPanel.setPreferredSize(new Dimension(1200, 800));
		
		switcher.show(mainPanel, "Main Menu");
		
		this.add(mainPanel);
		
		this.pack();
		this.setVisible(true);

		// TRIAL
	}
	
	public static void startGame()
	{
		gamePanel = new GamePanel();
		mainPanel.add(gamePanel, "Game");
	}
}

class Background extends Thread
{
	private boolean running;
	private GameWindow window;

	public Background(GameWindow window)
	{
		this.window = window;
	}

	public void run()
	{
		running = true;

		while (running)
		{
			// Update graphics
			window.repaint();
			
			try
			{
				Thread.sleep(33);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the thread is on or off
	 * 
	 * @return true or false
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Turns the thread back on
	 */
	public void turnOn()
	{
		this.running = true;
	}

	/**
	 * Turns the thread off
	 */
	public void turnOff()
	{
		this.running = false;
	}

}
