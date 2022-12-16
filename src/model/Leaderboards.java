package model;

import helper.Koneksi;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Leaderboards {

	private String filePath,fileName,name;
	
	private static Leaderboards lBoard;
	private ArrayList<Integer> topScores,topTiles;
	private ArrayList<String> topScoresName,topTilesName, topTimesName;
	private ArrayList<Long> topTimes;
	private int time, tiles, score, level, allScore;

	private Leaderboards(int level, int score, int tiles, String name, int time){
		this.time=time;
		this.tiles=tiles;
		this.score=score;
		this.name=name;
		this.level=level;
	}
//	private Leaderboards(int level, int allScore, String name){
//		this.allScore=allScore;
//		this.name=name;
//		this.level=level;
//	}
        
        private Leaderboards(int level, int allScore, String name, int time){
		this.allScore=allScore;
		this.name=name;
		this.level=level;
                this.time=time;
	}
        
	private Leaderboards(){
		topScores = new ArrayList<>();
		topTiles = new ArrayList<>();
		topTimes = new ArrayList<>();
		topScoresName = new ArrayList<>();
		topTilesName = new ArrayList<>();
		topTimesName = new ArrayList<>();
		fileName = "Scores";
		try {
			filePath = new File("").getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addLeaderboards(Board board) throws SQLException {
		long time=0;
//		if(board.isWon()){
//			time = board.getScores().getTime();
//		}
		Koneksi.executeUpdate(String.format("insert into leaderboards (time, tiles, score, player_id, level) values" +
				"(%d, %d, %d, %d, %d)",
				time,
				board.getHighestTileValue(),
				board.getScores().getCurrentScore(),
				Player.getInstance().getId(),
				Level.getInstance().getLevel()));
	}
	public static ArrayList<Leaderboards> getAllLeaderboard() {
		ArrayList<Leaderboards> allLeaderboard = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.score, " +
				"leaderboards.tiles, " +
				"leaderboards.time, " +
				"player.name " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("score"),
						res.getInt("tiles"),
						res.getString("name"),
                                                res.getInt("time"));
				allLeaderboard.add(leaderboard);
			}
			return allLeaderboard;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

//	public static ArrayList<Leaderboards> getAllTimeLeaderboards() {
//		ArrayList<Leaderboards> allTimeLeaderboards = new ArrayList<>();
//		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
//				"leaderboards.time, " +
//				"player.name " +
//				"FROM leaderboards " +
//				"JOIN player ON leaderboards.player_id=player.id ORDER BY leaderboards.time");
//		try {
//			while (res.next()) {
//				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
//						res.getInt("time"),
//						res.getString("name"));
//				allTimeLeaderboards.add(leaderboard);
//			}
//			return allTimeLeaderboards;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	public static ArrayList<Leaderboards> getAllTilesLeaderboards() {
		ArrayList<Leaderboards> allTilesLeaderboards = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.tiles, " +
				"player.name, " +
                                "leaderboards.time " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id ORDER BY leaderboards.tiles DESC");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("tiles"),
						res.getString("name"),
                                                res.getInt("time")
                                );
				allTilesLeaderboards.add(leaderboard);
			}
			return allTilesLeaderboards;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ArrayList<Leaderboards> getAllScoreLeaderboards() {
		ArrayList<Leaderboards> allScoreLeaderboards = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.score, " +
				"player.name, " +
                                "leaderboards.time " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id ORDER BY leaderboards.score DESC");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("score"),
						res.getString("name"),
                                                res.getInt("time")
                                );
				allScoreLeaderboards.add(leaderboard);
			}
			return allScoreLeaderboards;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Leaderboards getInstance(){
		if(lBoard == null){
			lBoard = new Leaderboards();
		}
		return lBoard;
	}

	public void loadScores() {
		try {
//			ArrayList<Leaderboards> leaderboards = getAllLeaderboard();
			ArrayList<Leaderboards> scoreLeaderboard = getAllScoreLeaderboards();
			ArrayList<Leaderboards> tilesLeaderboard = getAllTilesLeaderboards();
//			ArrayList<Leaderboards> timeLeaderboard = getAllTimeLeaderboards();

			topScores.clear();
			topTiles.clear();
			topTimes.clear();
			topTilesName.clear();
			topScoresName.clear();
//			topTimesName.clear();

//			for(Leaderboards lb:leaderboards){
//				topScores.add(lb.getScore());
//				topScoresName.add(String.format("%d [%s] [%s]", lb.getScore(),lb.getName(),setDifficulty(lb.getLevel())));
//				topTiles.add(lb.getTiles());
//				topTilesName.add(String.format("%d [%s] [%s]", lb.getTiles(),lb.getName(),setDifficulty(lb.getLevel())));
//				if(lb.getTime()==0) continue;
//				topTimesName.add(String.format(" [%s] [%s]", lb.getName(), setDifficulty(lb.getLevel())));
//				topTimes.add((long) lb.getTime());
//			}

			for(Leaderboards lb:scoreLeaderboard){
				topScores.add(lb.getAllScore());
				topScoresName.add(String.format("%d [%s] [%s]", lb.getAllScore(),lb.getName(),setDifficulty(lb.getLevel())));
                                topTimes.add((long) lb.getTime());
                        }
			for(Leaderboards lb:tilesLeaderboard){
				topTiles.add(lb.getAllScore());
				topTilesName.add(String.format("%d [%s] [%s]", lb.getAllScore(),lb.getName(),setDifficulty(lb.getLevel()), lb.getTime()));
                                topTimes.add((long) lb.getAllScore());
                        }       
//			for(Leaderboards lb:timeLeaderboard){
//				if(lb.getAllScore()==0) continue;
//				topTimesName.add(String.format(" [%s] [%s]", lb.getName(), setDifficulty(lb.getLevel())));
//				topTimes.add((long) lb.getAllScore());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String setDifficulty(int level){
		if(level==1) {
			return "Easy";
		} else if(level==2) {
			return "Medium";
		} else if(level==3) {
			return "Hard";
		}
		return "Unidentified";
	}
//	public void saveScores() {
//		addLeaderboards(board);
//		FileWriter output;
//		try {
//			File f = new File(filePath, fileName);
//			output = new FileWriter(f);
//			BufferedWriter writer = new BufferedWriter(output);
//
//			writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4) + "-" + topScores.get(5));
//			writer.newLine();
//			writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4) + "-" + topTiles.get(5));
//			writer.newLine();
//			writer.write(topTimes.get(0) + "-" + topTimes.get(1) + "-" + topTimes.get(2) + "-" + topTimes.get(3) + "-" + topTimes.get(4) + "-" + topTimes.get(5));
//			writer.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}
	public void addScore(int score){
		for(int i = 0; i < topScores.size(); i++){
			if(score >= topScores.get(i)){
				topScores.add(i, score);
				topScores.remove(topScores.size() - 1);
				break;
			}
		}
	}

	public void addTile(int tileValue){
		for(int i = 0; i < topTiles.size(); i++){
			if(tileValue >= topTiles.get(i)){
				topTiles.add(i, tileValue);
				topTiles.remove(topTiles.size() - 1);
				break;
			}
		}
	}

	public void addTime(long millis){
		for(int i = 0; i < topTimes.size(); i++){
			if(topTimes.get(i)==0 && millis >= topTimes.get(i)){
				topTimes.add(i, millis);
				topTimes.remove(topTimes.size() - 1);
				break;
			} else if(topTimes.get(i)>=0 && millis <= topTimes.get(i)){
				topTimes.add(i, millis);
				topTimes.remove(topTimes.size() - 1);
				break;
			}
		}
	}
	public ArrayList<String> getTopScores() {
		return topScoresName;
	}

	public ArrayList<String> getTopTiles() {
		return topTilesName;
	}

	public ArrayList<String> getTopTimesName() {return topTimesName; }
//	public ArrayList<Integer> getTopScores() {
//		return topScores;
//	}
//
//	public ArrayList<Integer> getTopTiles() {
//		return topTiles;
//	}
//
	public ArrayList<Long> getTopTimes() {return topTimes; }
	public int getHighScore(){
		if (topScores.size()>0){
			return topScores.get(0);
		}
		return 0;
	}
	
	public long getFastestTime(){
		if (topTimes.size()>0){
			return topTimes.get(0);
		}
		return 0;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}

	public int getAllScore() {
		return allScore;
	}

	public int getTiles() {
		return tiles;
	}

	public int getTime() {
		return time;
	}
}