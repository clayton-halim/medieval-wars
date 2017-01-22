package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel
{
	private JPanel choices = new JPanel();
	private JButton newGameButton = new JButton("New Game");
	private JButton helpButton = new JButton("Help");
	private JButton exitButton = new JButton("Exit");
	
	public MainMenuPanel()
	{
		this.setLayout(new BorderLayout());
		choices.setLayout(new GridLayout(3, 1, 0, 50));
		
		newGameButton.setAlignmentX(CENTER_ALIGNMENT);
		helpButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		choices.add(newGameButton);
		choices.add(helpButton);
		choices.add(exitButton);
		choices.setMaximumSize(new Dimension(300, 400));
		
		this.setMaximumSize(new Dimension(300, 400));
		this.add(choices, BorderLayout.CENTER);
		
		newGameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GameWindow.switcher.show(GameWindow.mainPanel, "Level Select");
			}
		});
		
		helpButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("Help window should open");
			}
		});
		
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
	}
}
