package model;

import view.LoginPanel;

import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Random;
import static model.ColorPallette.*;

public class Board extends Entity{

	public static Board b;
	public static final int LEFT = 0,
			RIGHT = 1,
			UP = 2,
			DOWN = 3,
			ROWS = 4,
			COLS = 4,
			startingTiles = 2,
			SPACING = 10;
	
	private long elapsedMS,
		startTime;
	
	private boolean dead,
		won,
		hasStarted;
	
	private Tile[][] board;
	private ScoreManager scores;
	private Leaderboards lBoard;
	private Level level;
//	private Player player;
	private BufferedImage gameBoard;

	public static Board getInstance(){
		if (b == null) {
			b = new Board(0,0);
		}
		return b;
	}
	public Board(int x, int y) {
		level = Level.getInstance();
		board = new Tile[ROWS][COLS];
		getWIDTH();
		getHEIGHT();
		super.x = x;
		super.y = y;
		gameBoard = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		createBoardImage();

		lBoard = Leaderboards.getInstance();
		lBoard.loadScores();
		scores = new ScoreManager(this);
		scores.loadGame();
//		scores.setBestTime(lBoard.getFastestTime());
		scores.setCurrentTopScore(lBoard.getHighScore());
		if(scores.newGame()){
			start();
			scores.saveGame();
		}
		else{
			for(int i = 0; i < scores.getBoard().length; i++){
				if(scores.getBoard()[i] == 0) continue;
				spawn(i / ROWS, i % COLS, scores.getBoard()[i]);
			}
			dead = checkDead();
			won = checkWon();
		}
	}

	public void reset(){
		won = false;
		dead = false;
		hasStarted = false;
		startTime = System.nanoTime();
		elapsedMS = 0;
		board = new Tile[ROWS][COLS];
		start();
		scores.saveGame();
	}

	private void start() {
		for (int i = 0; i < startingTiles; i++) {
			spawnRandom();
		}
	}
	
	private void spawn(int row, int col, int value) {
		board[row][col] = new Tile(value, getTileX(col), getTileY(row));
	}
	private void spawnRandom() {
		Random random = new Random();
		boolean notValid = true;

		while (notValid) {
//			int location = random.nextInt(4);
//			int row = location;
//			int col = location;
			int location = random.nextInt(16);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			if (current == null) {
				int value=0;
				if(level.getLevel()==1) {
					value = random.nextInt(2) < 1 ? 16 : 8;
				}else if(level.getLevel()==2) {
					value = random.nextInt(2) < 1 ? 8 : 4;
				} else if(level.getLevel()==3) {
					value = random.nextInt(2) < 1 ? 4 : 2;
				}
				Tile tile = new Tile(value, getTileX(col), getTileY(row));
				board[row][col] = tile;
				notValid = false;
			}
		}
	}

	private void createBoardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(lightblue);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = SPACING + SPACING * col + Tile.getInstance().getWIDTH() * col;
				int y = SPACING + SPACING * row + Tile.getInstance().getHEIGHT() * row;
				g.fillRoundRect(x, y, Tile.getInstance().getWIDTH(), Tile.getInstance().getHEIGHT(), Tile.RADIUS_WIDTH, Tile.RADIUS_HEIGHT);
			}
		}
	}

	public void update() {
		if (!won && !dead) {
			if (hasStarted) {
				elapsedMS = (System.nanoTime() - startTime) / 1000000;
//				scores.setTime(elapsedMS);
			}
			else {
				startTime = System.nanoTime();
			}
		}
		checkKeys();
		if (scores.getCurrentScore() > scores.getCurrentTopScore()) {
			scores.setCurrentTopScore(scores.getCurrentScore());
		}
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null) continue;
				current.update();
				resetPosition(current, row, col);
				if (current.getValue() == 2048) setWon(true);

//				if(level.getLevel()==1 && count2048>=1) {
//					setWon(true);
//				}else if(level.getLevel()==2 && count2048>=2) {
//					setWon(true);
//				} else if(level.getLevel()==3 && count2048>=3) {
//					setWon(true);
//				}
			}
		}
		scores.saveGame();
	}

	public void render(Graphics2D g) {
		BufferedImage finalBoard = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.drawImage(gameBoard, 0, 0, null);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null) continue;
				current.render(g2d);
			}
		}
		g.drawImage(finalBoard, x, y, null);
		g2d.dispose();
	}

	private void resetPosition(Tile tile, int row, int col) {
		if (tile == null) return;

		int x = getTileX(col);
		int y = getTileY(row);

		int distX = tile.getX() - x;
		int distY = tile.getY() - y;

		if (Math.abs(distX) < Tile.SLIDE_SPEED) tile.setX(tile.getX() - distX);
		if (Math.abs(distY) < Tile.SLIDE_SPEED) tile.setY(tile.getY() - distY);

		if (distX < 0) tile.setX(tile.getX() + Tile.SLIDE_SPEED);
		if (distY < 0) tile.setY(tile.getY() + Tile.SLIDE_SPEED);
		if (distX > 0) tile.setX(tile.getX() - Tile.SLIDE_SPEED);
		if (distY > 0) tile.setY(tile.getY() - Tile.SLIDE_SPEED);
	}

	public int getTileX(int col) {
		return SPACING + col * Tile.getInstance().getWIDTH() + col * SPACING;
	}

	public int getTileY(int row) {
		return SPACING + row * Tile.getInstance().getHEIGHT() + row * SPACING;
	}

	private boolean checkOutOfBounds(int direction, int row, int col) {
		if (direction == LEFT) {
			return col < 0;
		}
		else if (direction == RIGHT) {
			return col > COLS - 1;
		}
		else if (direction == UP) {
			return row < 0;
		}
		else if (direction == DOWN) {
			return row > ROWS - 1;
		}
		return false;
	}

	private boolean move(int row, int col, int horizontalDirection, int verticalDirection, int direction) {
		boolean canMove = false;
		Tile current = board[row][col];
		if (current == null) return false;
		boolean move = true;
		int newCol = col,newRow = row;
		while (move) {
			newCol += horizontalDirection;
			newRow += verticalDirection;
			if (checkOutOfBounds(direction, newRow, newCol)) break;
			if (board[newRow][newCol] == null) {
				board[newRow][newCol] = current;
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
			}
			else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
				board[newRow][newCol].setCanCombine(false);
				board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setCombineAnimation(true);
			} else move = false;
		}
		return canMove;
	}
	public void moveTiles(int direction) {
		boolean canMove = false;
		int horizontalDirection = 0;
		int verticalDirection = 0;

		if (direction == LEFT) {
			horizontalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove)
						canMove = move(row, col, horizontalDirection, verticalDirection, direction);
					else move(row, col, horizontalDirection, verticalDirection, direction);
				}
			}
		}
		else if (direction == RIGHT) {
			horizontalDirection = 1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = COLS - 1; col >= 0; col--) {
					if (!canMove)
						canMove = move(row, col, horizontalDirection, verticalDirection, direction);
					else move(row, col, horizontalDirection, verticalDirection, direction);
				}
			}
		}
		else if (direction == UP) {
			verticalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove)
						canMove = move(row, col, horizontalDirection, verticalDirection, direction);
					else move(row, col, horizontalDirection, verticalDirection, direction);
				}
			}
		}
		else if (direction == DOWN) {
			verticalDirection = 1;
			for (int row = ROWS - 1; row >= 0; row--) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove)
						canMove = move(row, col, horizontalDirection, verticalDirection, direction);
					else move(row, col, horizontalDirection, verticalDirection, direction);
				}
			}
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null) continue;
				current.setCanCombine(true);
			}
		}

		if (canMove) {
			spawnRandom();
			setDead(checkDead());
		}
	}

	public boolean isWon() {
		return won;
	}
	public boolean isDead() {
		return dead;
	}

	private boolean checkDead() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null) return false;
				boolean canCombine = checkSurroundingTiles(row, col, board[row][col]);
				if (canCombine) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkWon() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if(board[row][col] == null) continue;
				if(board[row][col].getValue() >= 2048 ) {

					return true;
				}
			}
		}
		return false;
	}

	private boolean checkSurroundingTiles(int row, int col, Tile tile) {
		if (row > 0) {
			Tile check = board[row - 1][col];
			if (check == null) return true;
			if (tile.getValue() == check.getValue()) return true;
		}
		if (row < ROWS - 1) {
			Tile check = board[row + 1][col];
			if (check == null) return true;
			if (tile.getValue() == check.getValue()) return true;
		}
		if (col > 0) {
			Tile check = board[row][col - 1];
			if (check == null) return true;
			if (tile.getValue() == check.getValue()) return true;
		}
		if (col < COLS - 1) {
			Tile check = board[row][col + 1];
			if (check == null) return true;
			if (tile.getValue() == check.getValue()) return true;
		}
		return false;
	}

	private void checkKeys() {
		if (!Keys.pressed[KeyEvent.VK_LEFT] && Keys.prev[KeyEvent.VK_LEFT]) {
			moveTiles(LEFT);
			if (!hasStarted) hasStarted = !dead;
		}
		if (!Keys.pressed[KeyEvent.VK_RIGHT] && Keys.prev[KeyEvent.VK_RIGHT]) {
			moveTiles(RIGHT);
			if (!hasStarted) hasStarted = !dead;
		}
		if (!Keys.pressed[KeyEvent.VK_UP] && Keys.prev[KeyEvent.VK_UP]) {
			moveTiles(UP);
			if (!hasStarted) hasStarted = !dead;
		}
		if (!Keys.pressed[KeyEvent.VK_DOWN] && Keys.prev[KeyEvent.VK_DOWN]) {
			moveTiles(DOWN);
			if (!hasStarted) hasStarted = !dead;
		}
	}

	public int getHighestTileValue(){
		int value = 2;
		for(int row = 0; row < ROWS; row++){
			for(int col = 0; col < COLS; col++){
				if(board[row][col] == null) continue;
				if(board[row][col].getValue() > value) value = board[row][col].getValue();
			}
		}
		return value;
	}

	public void setWon(boolean won) {
		if(!isWon() && won && !dead){
//			if(scores.getTime()<scores.getBestTime()) {
//				scores.setBestTime(scores.getTime());
//			}
//			lBoard.addTime(scores.getTime());
			try {
				lBoard.addLeaderboards(this);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		this.won = won;
	}
	public void setDead(boolean dead) {
		if(!isDead() && dead){
			lBoard.addTile(getHighestTileValue());
			lBoard.addScore(scores.getCurrentScore());
			try {
				lBoard.addLeaderboards(this);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		this.dead = dead;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

        @Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		super.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		super.y = y;
	}

	public void setHEIGHT(int HEIGHT) {
		super.HEIGHT = HEIGHT;
	}
	public int getHEIGHT() {
		setHEIGHT((ROWS + 1) * SPACING + ROWS * Tile.getInstance().getWIDTH());
		return HEIGHT;
	}

	public void setWIDTH(int WIDTH) {
		super.WIDTH = WIDTH;
	}
        
        @Override
	public int getWIDTH() {
		setWIDTH((COLS + 1) * SPACING + COLS * Tile.getInstance().getWIDTH());
		return WIDTH;
	}

	public ScoreManager getScores(){
		return scores;
	}
//	public Player getPlayer(){
//		return player;
//	}
}