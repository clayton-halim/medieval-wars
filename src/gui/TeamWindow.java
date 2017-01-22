package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamWindow extends JFrame
{
	private JPanel mainPanel = new JPanel();
	private JPanel top = new JPanel();
	private JPanel bottom = new JPanel();
	private JLabel teamName = new JLabel();
	private JLabel teamFunds = new JLabel();
	private JLabel dayCount = new JLabel();
	private JButton endTurnButton = new JButton();
	
	public TeamWindow(Team team)
	{
		this.setMinimumSize(new Dimension(300, 150));
		this.setLocation(Main.gameWindow.getX() + Main.gameWindow.getHeight() - 300,
				         Main.gameWindow.getY() + Main.gameWindow.getHeight() - 150);
		
		updateStats(team);
		
		mainPanel.setLayout(new GridLayout(2, 1));
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
		
		mainPanel.add(top);
		mainPanel.add(bottom);
		
		top.add(teamName);
		top.add(teamFunds);
		bottom.add(dayCount);
		bottom.add(endTurnButton);
		
		endTurnButton.setText("End Turn");
		endTurnButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.newTurn();
			}
		});
		
		this.setAlwaysOnTop(true);
		this.updateStats(Main.turn);
		this.add(mainPanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void updateStats(Team team)
	{
		this.setTitle(team.getColour() + "'s Turn");
		
		if (team.getColour().equalsIgnoreCase("red"))
		 {
			 this.setIconImage(new ImageIcon("graphics/characters/cavalry_red.png").getImage());
		 }
		 else if (team.getColour().equalsIgnoreCase("blue"))
		 {
			 this.setIconImage(new ImageIcon("graphics/characters/cavalry_blue.png").getImage());
		 }
		
		Font nameF = new Font("Calibri", Font.BOLD, 24);
		Font fundF = new Font("Calibri", Font.ITALIC, 24);
		Font dayF = new Font("Impact", Font.PLAIN, 16);
		
		teamName.setText(team.toString());
		teamName.setFont(nameF);
		teamFunds.setText(String.valueOf("G. " + team.getFunds()));
		teamFunds.setFont(fundF);
		dayCount.setText("Day: " + String.valueOf(Main.dayCount));
		dayCount.setFont(dayF);
	}
}
