package gui;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class LevelSelectPanel extends JPanel
{
	private JPanel switchButtons = new JPanel();
	private JPanel mapList = new JPanel();
	private JButton[] options;
	private JList mapChoices = new JList();
	private JScrollPane mapListScroll = new JScrollPane();
	private JButton mainMenuButton = new JButton("Main Menu");
	
	ActionListener playMap = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent button)
		{
			JButton pressed = (JButton) button.getSource();
			
			if (!Main.loadMap(pressed.getText()))
			{
				JOptionPane.showMessageDialog(null, "Could not load map file, try a different map.", "LOAD ERROR", JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				GameWindow.startGame();
				GameWindow.teamInfo = new TeamWindow(Main.redTeam);
				GameWindow.switcher.show(GameWindow.mainPanel, "Game");
				System.out.println("Game switch done!");
			}
		}
	};
	
	public LevelSelectPanel()
	{	
		File[] textFilesFound = findTextFiles();
		ArrayList<File> playableMaps = new ArrayList<>();
		
		for (File txt : textFilesFound)
		{
			if (isValidMap(txt))
			{
				playableMaps.add(txt);
			}
		}
		
		options = new JButton[playableMaps.size()];
		
		this.setLayout(new BorderLayout());
		mapList.setLayout(new GridLayout(playableMaps.size(), 1, 0, 15));
		this.add(mapListScroll, BorderLayout.CENTER);
		
		mapListScroll.setViewportView(mapList);
		
		for (int i = 0, len = playableMaps.size(); i < len; i++)
		{
			String mapName = playableMaps.get(i).toString().substring(5);
			int nameLen = mapName.length();
			options[i] = new JButton();
			options[i].setText(mapName.substring(0, nameLen - 4));
			options[i].addActionListener(playMap);
			
			mapList.add(options[i]);
		}
		
		switchButtons.setLayout(new GridLayout(1, 2));
		
		switchButtons.add(mainMenuButton);
		
		this.add(switchButtons, BorderLayout.SOUTH);
		
		mainMenuButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GameWindow.switcher.show(GameWindow.mainPanel, "Main Menu");
			}
		});
		
		
		BufferedImage img = null;
		
		try
		{
			img = createMiniMap(new File("maps/justin.txt"));
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		try
		{
			ImageIO.write(img, "png", new File("graphics/map icons/test.png"));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isValidMap(File map)
	{
		int row = 0;
		int col = 0;
		boolean valid = true;
		Scanner input = null;
		
		try
		{
			input = new Scanner(new FileReader(map));
		}
		catch (FileNotFoundException e)
		{
			valid = false;
		}
		
		while (row < 16 && valid)
		{
			String line = input.nextLine().trim();
			
			if (line.length() != 24)
			{
				valid = false;
			}
			
			while (col < 24 && valid)
			{
				String symbol = line.substring(col, col + 1);

				if (!symbol.equals("#") && !symbol.equals("~") && !symbol.equals("!") && 
						!symbol.equals("*") && !symbol.equals("&") && !symbol.equals("^") &&
						!symbol.equals("|") && !symbol.equals("-") && !symbol.equals("%") &&
						!symbol.equals("1") && !symbol.equals("2") && !symbol.equals("3") &&
						!symbol.equals("4") && !symbol.equals("5") && !symbol.equals("6") &&
						!symbol.equals("7") && !symbol.equals("8"))
					{
						valid = false;
					}
				
					col++;
			}
			
			row++;
			col = 0;
		}
		
		return valid;
	}
	
	public File[] findTextFiles()
	{
		File dir = new File("maps");
		
		File[] maps = dir.listFiles(new FilenameFilter()
						{
							@Override
							public boolean accept(File dir, String filename)
							{
								return filename.endsWith(".txt");
							}
						});
						
		return maps;
	}
	
	public BufferedImage createMiniMap(File map) throws FileNotFoundException
	{
		BufferedImage minimap = new BufferedImage(24, 16, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) minimap.getRaster().getDataBuffer()).getData();
		
		Scanner input = new Scanner(new FileReader(map));
		
		for (int y = 0; y < 16; y++)
		{
			String line = input.next();
			
			for (int x = 0; x < 24; x++)
			{
				String symbol = line.substring(x, x + 1);
				
				if (symbol.equals("#")) //Grass
				{
					pixels[x + y * 24] = 0x4EEF1B;
				}
				else if (symbol.equals("~")) //Water
				{
					pixels[x + y * 24] = 0x0078CE;
				}
				else if (symbol.equals("!")) //Stone
				{
					pixels[x + y * 24] = 0x808080;
				}
				else if (symbol.equals("*")) //Dirt
				{
					pixels[x + y * 24] = 0x77421C;
				}
				else if (symbol.equals("&")) //Sand
				{
					pixels[x + y * 24] = 0xE5C849;
				}
				else if (symbol.equals("^")) //Mountains
				{
					pixels[x + y * 24] = 0xD3D3D3;
				}
				else if (symbol.equals("|") || symbol.equals("-")) //Bridges
				{
					pixels[x + y * 24] = 0xB54B00;
				}
				else if (symbol.equals("%")) //Forest
				{
					pixels[x + y * 24] = 0x009B42;
				}
				else if (symbol.equals("1") || symbol.equals("4") || symbol.equals("7")) //Blue Structures
				{
					pixels[x + y * 24] = 0x00D3D3;
				}
				else if (symbol.equals("2") || symbol.equals("5") || symbol.equals("8")) //Red Structures
				{
					pixels[x + y * 24] = 0xFF0000;
				}
				else if (symbol.equals("3")) //Barracks
				{
					pixels[x + y * 24] = 0x7F0000;
				}
				else if (symbol.equals("6")) //Town
				{
					pixels[x + y * 24] = 0x0078CE;
				}
			}
		}
		
		minimap = resizeImage(minimap, minimap.getType(), 8);
		return minimap;
	}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int ratio) 
	{
        BufferedImage resizedImage = new BufferedImage(originalImage.getWidth() * ratio, originalImage.getHeight() * ratio, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, originalImage.getWidth() * ratio, originalImage.getHeight() * ratio, null);
        g.dispose();

        return resizedImage;
    }
}
