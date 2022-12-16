package helper;

import java.sql.*;

public class Koneksi {
    private static final Connection CONN = connect();

    static Connection connect() {
        Connection conn = null;
        String url = "jdbc:sqlite:database/2048.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection enstablished");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static ResultSet executeQuery(String query) { //select
        ResultSet res = null;
        try {
            Statement stt = CONN.createStatement();
            res = stt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int executeUpdate(String query) {
        try {
            Statement stt = CONN.createStatement();
            return stt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
