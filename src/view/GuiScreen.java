package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class GuiScreen {

	private static GuiScreen screen;
	private HashMap<String, GuiPanel> panels;
	private String currentPanel = "";

	private GuiScreen() {
		panels = new HashMap<>();
	}

	public static GuiScreen getInstance() {
		if (screen == null) {
			screen = new GuiScreen();
		}
		return screen;
	}

	public void update() {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).update();
		}
	}

	public void render(Graphics2D g) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).render(g);
		}
	}

	public void add(String panelName, GuiPanel panel) {
		panels.put(panelName, panel);
	}

	public void setCurrentPanel(String panelName) {
		currentPanel = panelName;
	}

	public void mouseClicked(MouseEvent e) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).mouseClicked(e);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).mouseReleased(e);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).mouseMoved(e);
		}
	}
}
