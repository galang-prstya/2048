package view;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import model.Utils;
import static model.ColorPallette.*;
import static model.FontSet.*;

public class GuiButton {

	private String currentState = "released",
			text = "";
	private final RoundRectangle2D clickBox;
	private final ArrayList<ActionListener> actionListeners= new ArrayList<ActionListener>();

	public GuiButton(int x, int y, int width, int height){
		clickBox = new RoundRectangle2D.Double(x, y, width, height, 30,30);
	}

	public void update(){ }
	
	public void render(Graphics2D g){
		if(currentState.equals("released")) Utils.setRectangle(g,blue, clickBox);
		else if(currentState.equals("pressed")) Utils.setRectangle(g, purple, clickBox);
		else Utils.setRectangle(g, red, clickBox);
		Utils.setFont(g, buttonFont, tFont8);
		g.drawString(text, getX() + getWidth() / 2  - Utils.getWidth(text, buttonFont, g) / 2, getY() + getHeight() / 2  + Utils.getHeight(text, buttonFont, g) / 2);
	}
	
	public void addActionListener(ActionListener listener){
		actionListeners.add(listener);
	}
	
	public void mousePressed(MouseEvent e) {
		if(clickBox.contains(e.getPoint()))
			currentState = "pressed";
	}

	public void mouseReleased(MouseEvent e) {
		if(clickBox.contains(e.getPoint()))
			for(int i = 0; i < actionListeners.size(); i++){
				actionListeners.get(i).actionPerformed(null);
			}
		currentState = "released";
	}

	public void mouseDragged(MouseEvent e) {
		currentState = clickBox.contains(e.getPoint()) ? "pressed" : "released";
	}

	public void mouseMoved(MouseEvent e) {
		currentState = clickBox.contains(e.getPoint()) ? "hover" : "released";
	}

	public int getX(){
		return (int) clickBox.getX();
	}
	public RoundRectangle2D getClickBox(){
		return clickBox;
	}

	public int getY(){
		return (int) clickBox.getY();
	}
	
	public int getWidth(){
		return (int) clickBox.getWidth();
	}
	
	public int getHeight(){
		return (int) clickBox.getHeight();
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}
	public void setActivated(Graphics2D g){
		Utils.setRectangle(g, white, clickBox);
		Utils.setRectangle(g, t2048, clickBox);
		Utils.setFont(g, buttonFont, blue);
		g.drawString(text, getX() + getWidth() / 2  - Utils.getWidth(getText(), buttonFont, g) / 2, getY() + getHeight() / 2  + Utils.getHeight(getText(), buttonFont, g) / 2);
	}

}
