package view;

import java.awt.*;

import static model.ColorPallette.*;
import static model.FontSet.*;
import model.Game;
import model.Level;
import model.Utils;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class LevelPanel extends GuiPanel{

	private Level lvl;
	private final String title = "Choose Level";
	private final int buttonWidth = 100,
			backButtonWidth = 220,
			buttonSpacing = 20,
			buttonY = 260,
			buttonHeight = 50;
	private String currentState = "easy";
	private GuiButton hardButton, backButton, mediumButton, easyButton;
	
	public LevelPanel(){
		super();
		lvl = Level.getInstance();

		mediumButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, buttonY, buttonWidth, buttonHeight);
		mediumButton.addActionListener(e ->{
			currentState = "medium";
		});
		mediumButton.setText("Medium");
		addButton(mediumButton);
		
		easyButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 - mediumButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
		easyButton.addActionListener(e ->{
			currentState = "easy";
		});
		easyButton.setText("Easy");
		addButton(easyButton);
		
		hardButton = new GuiButton(Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing, buttonY, buttonWidth, buttonHeight);
		hardButton.addActionListener(e ->{
			currentState = "hard";
		});
		hardButton.setText("Hard");
		addButton(hardButton);
		
		backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Set Level");
		addButton(backButton);
	}
	
	private void drawLevel(Graphics2D g){
		if(currentState.equals("medium")){
			mediumButton.setActivated(g);
			lvl.setLevel(2);
		}
		else if(currentState.equals("easy")){
			easyButton.setActivated(g);
			lvl.setLevel(1);
		}
		else {
			hardButton.setActivated(g);
			lvl.setLevel(3);
		}
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(blue);
		g.drawString(title, Game.WIDTH / 2 - Utils.getWidth(title, leaderboardTitle, g) / 2, Game.HEIGHT/2-Utils.getHeight(title, leaderboardTitle, g)-30);
		drawLevel(g);
	}
}
