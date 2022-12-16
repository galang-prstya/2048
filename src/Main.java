import javax.swing.*;

import model.Game;

public class Main {

	public static void main(String[] args){
		JFrame window = new JFrame("2048");
		Game game = new Game();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		game.start();
                game.startGameThread();
	}
}