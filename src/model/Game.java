package model;

import javax.swing.*;

import view.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static model.ColorPallette.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = Board.getInstance().getWIDTH()+40,
			HEIGHT = 580;
        
        public static Sound sound = new Sound();
        private Thread gameThread = new Thread(this);
	private boolean running;
	private Thread game;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private GuiScreen screen;
        private int FPS = 60;
	
	public Game() {

		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel());
		screen.add("Leaderboards", new LeaderboardsPanel());
		screen.add("Level", new LevelPanel());
		screen.add("Login", new LoginPanel());
		screen.setCurrentPanel("Menu");
	}
        
        public void startGameThread() {
            playMusic(0);
            gameThread.start(); 
        }
        
        public void playMusic(int i) {
            sound.setFile(i);
            sound.play();
            sound.loop();
        }

        public void stopMusic() {
            sound.stop();
        }
        
        public void playSE(int i) {
            sound.setFile(i);
            sound.play();
        }

	private void update() {
		screen.update();
		Keys.update();
	}

	private void render() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		screen.render(g);
		g.dispose();

		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}

	@Override
	public void run() {
            double drawInterval = 1000000000/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

            while(running) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;
                
                if(delta >= 1) {
                    update(); 
                    render();
                    delta--;
                }
            }

	}

	public void start() {
		running = true;
                game = new Thread(this);
		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		Keys.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keyReleased(e);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		screen.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
	}
}
