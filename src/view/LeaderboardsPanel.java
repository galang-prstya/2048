package view;

import java.awt.*;
import java.util.ArrayList;

import static model.ColorPallette.*;
import static model.FontSet.*;
import model.Game;
import model.Leaderboards;
import model.Utils;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class LeaderboardsPanel extends GuiPanel{

	private Leaderboards lBoard;
	private final String title = "Leaderboards";
	private final int buttonWidth = 100,
			backButtonWidth = 220,
			buttonSpacing = 20,
			buttonY = 120,
			buttonHeight = 50,
			leaderboardsX = 80,
			leaderboardsY = buttonY + buttonHeight + 50;
	private String currentState = "score";
	private GuiButton timeButton, backButton, scoreButton, tileButton;
	
	public LeaderboardsPanel(){
		super();
		lBoard = Leaderboards.getInstance();
		Leaderboards.getInstance().loadScores();

		tileButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 500, buttonY, buttonWidth, buttonHeight);
		tileButton.addActionListener(e -> currentState = "tile");
		tileButton.setText("Tiles");
		addButton(tileButton);
		
		scoreButton = new GuiButton (Game.WIDTH / 2 - buttonWidth / 500 - tileButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
		scoreButton.addActionListener(e -> currentState = "score");
		scoreButton.setText("Scores");
		addButton(scoreButton);
		
//		timeButton = new GuiButton(Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing, buttonY, buttonWidth, buttonHeight);
//		timeButton.addActionListener(e -> currentState = "time");
//		timeButton.setText("Times");
//		addButton(timeButton);
		
		backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Back");
		addButton(backButton);
	}
	
	private void drawLeaderboards(Graphics2D g){
		ArrayList<String> strings = new ArrayList<>();
		if(currentState.equals("score")){
			scoreButton.setActivated(g);
			strings = Leaderboards.getInstance().getTopScores();
		}
		else if(currentState.equals("tile")){
			tileButton.setActivated(g);
			strings = Leaderboards.getInstance().getTopTiles();
		}
//		else {
//			timeButton.setActivated(g);
//			if(Leaderboards.getInstance().size()>0){
//				for (int i=0; i<6; i++){
//					strings.add(Utils.formatTime(Leaderboards.getInstance().getTopTimes().get(i)) + Leaderboards.getInstance().getTopTimesName().get(i));
//				}
//			}
//		}

		g.setColor(blue);
		g.setFont(scorFont);

		String leaderboard;
		if(strings.size()>=0) {
			int loop = (strings.size()<11) ? strings.size() : 10;
			for (int i = 0; i < loop; i++) {
				leaderboard = String.format("%d. %s", i + 1, strings.get(i));
				g.drawString(leaderboard, leaderboardsX, leaderboardsY + i * 30);
			}
		} else{
			leaderboard = "Belum Ada Highscore";
			g.drawString(leaderboard, leaderboardsX, leaderboardsY);
		}
	}

	@Override
	public void render(Graphics2D g){
		Leaderboards.getInstance().loadScores();
		super.render(g);
		g.setColor(blue);
		g.drawString(title, Utils.centerX(Game.WIDTH, title, g, leaderboardTitle), Utils.getHeight(title, leaderboardTitle, g) + 40);
		drawLeaderboards(g);
	}
}
