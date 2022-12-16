package model;

import java.io.*;

public class ScoreManager {

	private int currentScore,
		currentTopScore;
	
//	private long time,
//		startingTime,
//		bestTime;
	
	private String filePath,
		temp;
	
	private boolean newGame;
	
	private int[] board = new int[16];

	private Board gBoard;

	public ScoreManager(Board gBoard) {
		try {
			filePath = new File("").getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		temp = "2048.tmp";

		this.gBoard = gBoard;
	}

	public void reset() {
		File f = new File(filePath, temp);
		if (f.isFile()) f.delete();
		
		newGame = true;
//		startingTime = 0;
		currentScore = 0;
//		time = 0;
	}

	private void createFile() {
		FileWriter output;
		newGame = true;
		
		try {
			File f = new File(filePath, temp);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + 0);
			writer.newLine();
			writer.write("" + 0);
			writer.newLine();
			writer.write("" + 0);
			writer.newLine();
			writer.write("" + 0);
			writer.newLine();
			for (int row = 0; row < Board.ROWS; row++) {
				for (int col = 0; col < Board.COLS; col++) {
					if(row == Board.ROWS - 1 && col == Board.COLS - 1){
						writer.write("" + 0);
					}
					else{
						writer.write(0 + "-");
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveGame() {
		FileWriter output;
		if (newGame) newGame = false;

		try {
			File f = new File(filePath, temp);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + currentScore);
			writer.newLine();
			writer.write("" + currentTopScore);
			writer.newLine();
//			writer.write("" + time);
//			writer.newLine();
//			writer.write("" + bestTime);
//			writer.newLine();
			for (int row = 0; row < Board.ROWS; row++) {
				for (int col = 0; col < Board.COLS; col++) {
					board[row * Board.COLS + col] = (gBoard.getBoard()[row][col] != null)
							? gBoard.getBoard()[row][col].getValue() : 0;
					if (row == Board.ROWS - 1 && col == Board.COLS - 1)
						writer.write("" + board[row * Board.COLS + col]);
					else writer.write(board[row * Board.COLS + col] + "-");
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGame() {
		try {
			File f = new File(filePath, temp);
			if (!f.isFile()) createFile();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			currentScore = Integer.parseInt(reader.readLine());
			currentTopScore = Integer.parseInt(reader.readLine());
//			time = Long.parseLong(reader.readLine());
//			startingTime = time;
//			bestTime = Long.parseLong(reader.readLine());

			String[] board = reader.readLine().split("-");
			for (int i = 0; i < board.length; i++) {
				this.board[i] = Integer.parseInt(board[i]);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCurrentScore() {
		currentScore=0;
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(gBoard.getBoard()[i][j]==null) continue;
				currentScore+=gBoard.getBoard()[i][j].getValue();
			}
		}
		return currentScore;
	}

	public int getCurrentTopScore() {
		return currentTopScore;
	}

	public void setCurrentTopScore(int currentTopScore) {
		this.currentTopScore = currentTopScore;
	}

//	public long getTime() {
//		return time;
//	}
//
//	public void setTime(long time) {
//		this.time = time + startingTime;
//	}
//
//	public long getBestTime() {
//		return bestTime;
//	}
//
//	public void setBestTime(long bestTime) {
//		this.bestTime = bestTime;
//	}

	public boolean newGame() {
		return newGame;
	}

	public int[] getBoard() {
		return board;
	}
}