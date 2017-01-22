package gui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.UIManager;

import tiles.*;
import entities.*;

public class Main
{
	public static Tile[][] map = new Tile[16][24];
	public static Team redTeam = new Team("Red Team", "Red", 1000);
	public static Team blueTeam = new Team("Blue Team", "Blue", 1000);
	public static Team turn;
	public static int dayCount = 1;
	public static GameWindow gameWindow;
	
	static EntityKnight redTest = new EntityKnight(redTeam, 2, 2);
	static EntityKnight blueTest = new EntityKnight(blueTeam, 1, 1);

	public static void main(String[] args)
	{
		try 
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
		catch (Exception e) 
        {
            e.printStackTrace();
        }
		
		turn = redTeam;
		
		gameWindow = new GameWindow();
		
		//TODO Add a way to pick a team name
	}
	
	public static void firstTurn()
	{
		
	}
	
	public static void newTurn()
	{
		turn.updateTeam();

		//Close open windows except for team info and the game so that players cannot interact with
		//menus for the other team
		if (GameWindow.barracksWindow != null) 
		{
			GameWindow.barracksWindow.dispose();
		}
		
		if (GameWindow.tileMenu != null)
		{
			GameWindow.tileMenu.dispose();
		}
		
		if (GameWindow.confirmedActionWindow != null)
		{
			System.out.print("Canceled!");
			GameWindow.confirmedActionWindow.btnCancel.doClick();
		}
		
		if (GameWindow.cancelActionWindow != null)
		{
			GameWindow.cancelActionWindow.dispose();
		}
			
		if (turn == redTeam)
		{
			System.out.println("It's blue team's turn now!");
			turn = blueTeam;
		}
		else
		{
			System.out.println("It's red team's turn now!");
			turn = redTeam;
			dayCount++; //Day doesn't increase until both teams have gone
		}
	
		GameWindow.teamInfo.updateStats(turn);
		
		Team winner = checkWinner();
		
		if (winner != null)
		{
			System.out.println("Winner: " + winner.toString());
			System.exit(0);
		}
	}
	
	/**
	 * Checks if a team has won
	 * @return - the winning team, if there is no winner, null is returned
	 */
	public static Team checkWinner()
	{
		Team winner = null;
		
		//Check if there are any winners, if so, end the game
		if (!Main.redTeam.hasHQ())
		{
			winner = blueTeam;
		}
		else if (!Main.blueTeam.hasHQ())
		{
			winner = Main.redTeam;
		}
		
		return winner;
	}
	
	/**
	 * Loads a map to play on
	 * @param mapFile - file name of map
	 * @return - true or false if the map could be loaded
	 */
	public static boolean loadMap(String mapFile)
	{
		boolean couldLoad = false;
		
		Scanner mapData = null;

		try
		{
			mapData = new Scanner(new FileReader("maps/" + mapFile + ".txt"));
			
			int x = 0;
			int y = 0;

			while (mapData.hasNext())
			{
				String currentLine = mapData.next();

				for (int i = 0, len = currentLine.length(); i < len; i++)
				{
					String currentTile = currentLine.substring(i, i + 1);
					
					try
					{
						map[y][x] = findTile(currentTile, x, y);
						System.out.print(map[y][x].toString() + " Success! ");
					}
					catch(NullPointerException e)
					{
						System.out.print(map[y][x].toString() + " Fail !");
					}
										
					if (x == len - 1)
					{
						System.out.println();
						y++;
						x = 0;
					}
					else
					{
						x++;
					}
				}
			}
			
			couldLoad = true;
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		
		return couldLoad;
	}
	
	/**
	 * Used for loading the map, returns the appropriate tile that a symbol represents
	 * @param symbol
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return The tile corresponding with the symbol
	 */
	public static Tile findTile(String symbol, int x, int y)
	{
		Tile toReturn = null;
		
		if (symbol.equals("#"))
		{
			toReturn = new TileGrass(x, y);
		}
		else if (symbol.equals("~"))
		{
			toReturn = new TileWater(x, y);
		}
		else if (symbol.equals("!"))
		{
			toReturn = new TileStone(x, y);
		}
		else if (symbol.equals("*"))
		{
			toReturn = new TileDirt(x, y);
		}
		else if (symbol.equals("&"))
		{
			toReturn = new TileSand(x, y);
		}
		else if (symbol.equals("^"))
		{
			toReturn = new TileMountain(x, y);
		}
		else if (symbol.equals("|"))
		{
			toReturn = new TileBridge("up/down", x, y);
		}
		else if (symbol.equals("-"))
		{
			toReturn = new TileBridge("left/right", x, y);
		}
		else if (symbol.equals("%"))
		{
			toReturn = new TileForest(x, y);
		}
		else if (symbol.equals("1"))
		{
			toReturn = new StructureBarracks(blueTeam, x, y);
			blueTeam.buildings.add(toReturn);
		}
		else if (symbol.equals("2"))
		{
			toReturn = new StructureBarracks(redTeam, x, y);
			redTeam.buildings.add(toReturn);
		}
		else if (symbol.equals("3"))
		{
			toReturn = new StructureBarracks(x, y);
		}
		else if (symbol.equals("4"))
		{
			toReturn = new StructureTown(blueTeam, x, y);
			blueTeam.buildings.add(toReturn);
		}
		else if (symbol.equals("5"))
		{
			toReturn = new StructureTown(redTeam, x, y);
			redTeam.buildings.add(toReturn);
		}
		else if (symbol.equals("6"))
		{
			toReturn = new StructureTown(x, y);
		}
		else if (symbol.equals("7"))
		{
			toReturn = new StructureHQ(blueTeam, x, y);
			blueTeam.buildings.add(toReturn);
		}
		else if (symbol.equals("8"))
		{
			toReturn = new StructureHQ(redTeam, x, y);
			redTeam.buildings.add(toReturn);
		}
		
		return toReturn;
	}
}

