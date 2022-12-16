package model;
import helper.Koneksi;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {
    public static Player player;
    private int id;
    private String name;

    public static Player getInstance(){
        if(player==null){
            player = new Player("Guest", 4);
        }
        return player;
    }

    public Player(String name, int id){
        this.name=name;
        this.id=id;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setId(int id) { 
        this.id = id; 
    }
    
    public int getId() { 
        return id; 
    }

    public void addPlayer(String name) throws SQLException {
        Koneksi.executeUpdate("insert into player (name) values ('" + name + "')");
    }

    public Player getPlayerByName(String name) {
        ResultSet res = Koneksi.executeQuery("select * from player where name='" + name + "';");
        try {
            if (res.next()) {
                player = new Player(res.getString("name"),res.getInt("id"));
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
