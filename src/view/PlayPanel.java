package view;

import java.awt.*;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import model.Board;
import model.Game;
import model.Level;
import model.ScoreManager;
import model.Utils;
import static model.ColorPallette.*;
import static model.FontSet.*;

public class PlayPanel extends GuiPanel {

	private Board board;
	private BufferedImage info;
	private ScoreManager scores;
	private Level lvl;

	private GuiButton tryAgain,mainMenu;
	private int spacing = 20,largeButtonWidth = 160, buttonHeight = 50;
	private boolean added;

	public PlayPanel() {
		lvl = Level.getInstance();
		System.out.println();
		board = new Board(Game.WIDTH / 2 - Board.getInstance().getWIDTH() / 2, Game.HEIGHT - Board.getInstance().getHEIGHT() - 20);
		scores = board.getScores();
		info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

		mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, Game.HEIGHT-150, largeButtonWidth, buttonHeight);
		tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeight, largeButtonWidth, buttonHeight);

		tryAgain.setText("Try Again");
		mainMenu.setText("Main Menu");

		tryAgain.addActionListener(e -> {
			board.getScores().reset();
			board.reset();

			removeButton(tryAgain);
			removeButton(mainMenu);

			added = false;
		});

		mainMenu.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
	}

	private void drawGui(Graphics2D g) {

		Graphics2D g2d = (Graphics2D) info.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
		g2d.setColor(lightblue);
		g2d.setFont(infoFont);
		g2d.drawString("Point: " + scores.getCurrentScore(), 30, 40);

		g2d.setColor(t2048);
		g2d.drawString("Best: " + scores.getCurrentTopScore(),  350, 40);

		
		int x = (lvl.getLevel()==2) ? 
				(Game.WIDTH - Utils.getWidth(lvl.setDifficutly(), infoFont, g2d))/2-12 :
					(Game.WIDTH - Utils.getWidth(lvl.setDifficutly(), infoFont, g2d))/2-27;
		
		RoundRectangle2D box = new RoundRectangle2D.Double(x,37,100,30,20,20);
		g2d.setColor(lightblue);
		g2d.fill(box);
		g2d.setColor(blue);
		g2d.drawString(lvl.setDifficutly(), (Game.WIDTH - Utils.getWidth(lvl.setDifficutly(), infoFont, g2d))/2, 60);
//		g2d.dispose();
		g.drawImage(info, 0, 0, null);
	}
	public void drawGameOver(Graphics2D g) {
		String text;
		g.setColor(transparentBG);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.setColor(red);
		text = "You Lose!";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameTitle), 150);

		g.setColor(blue);
		g.setFont(infoFont);
		text = "Your Point: " + scores.getCurrentScore();
		g.drawString(text,Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)-50);

	}

	public void drawWin(Graphics2D g) {
		String text;
		g.setColor(transparentBG);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.setColor(lightblue);
		text = "You Win!";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameTitle), 150);

		g.setColor(blue);
		g.setFont(infoFont);
		text = "Your Point: " + scores.getCurrentScore();
		g.drawString(text,Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)-50);

	}

	public Board getBoard() {
		return board;
	}

	@Override
	public void update() {
		board.update();
	}
        
	@Override
	public void render(Graphics2D g) {
		drawGui(g);
		board.render(g);
		if (board.isDead() && !board.isWon()) {
			tryAgain.setText("Try Again");
			if (!added) {
				added = true;
				addButton(mainMenu);
				addButton(tryAgain);
			}
			drawGameOver(g);
		} else if(board.isDead() && board.isWon()){
			tryAgain.setText("Play Again");
			if (!added) {
				added = true;
				addButton(mainMenu);
				addButton(tryAgain);
			}
			drawWin(g);
		}
		super.render(g);
	}
}