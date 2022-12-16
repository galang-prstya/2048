package view;

import java.awt.*;

import model.Game;
import model.Utils;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class MainMenuPanel extends GuiPanel {

	private Image logo;
	private int buttonWidth = 180;
	
	public MainMenuPanel() {
		super(); 
		logo = Utils.scaleImage(logo, "/Users/user/OneDrive/Documents/NetBeansProjects/2048/src/logo-2048.png", 6, 6);
		GuiButton playerButton = new GuiButton(Game.WIDTH / 3, 320, buttonWidth, 40);
		playerButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Login"));
		playerButton.setText("Login");
		addButton(playerButton);
                
                GuiButton playButton = new GuiButton(Game.WIDTH / 3, 380, buttonWidth, 40);
		playButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Play"));
		playButton.setText("Play");
		addButton(playButton);
		
		GuiButton scoresButton = new GuiButton(Game.WIDTH / 3, 440, buttonWidth, 40);
		scoresButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Leaderboards"));
		scoresButton.setText("Leaderboards");
		addButton(scoresButton);
		
		GuiButton levelButton = new GuiButton (Game.WIDTH / 3, 500, buttonWidth, 40);    //(50, 420, buttonWidth, 60);
		levelButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Level"));
		levelButton.setText("Set Level");
		addButton(levelButton);
		
		
	}

	public void render(Graphics2D g){
		super.render(g);
		int logoWidth = logo.getWidth(null);
		g.drawImage(logo, Game.WIDTH / 2 - logoWidth / 2, 60, null);
	}
}
