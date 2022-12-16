package view;

import model.Keys;
import model.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static model.ColorPallette.*;
import static model.FontSet.buttonFont;

public class GuiTextField{

	private String currentState="notClicked",
			text = "", placeholderText, type;
	private RoundRectangle2D textBox;
	public static boolean clicked = false;

	public GuiTextField(int x, int y, int width, int height){
		textBox = new RoundRectangle2D.Double(x, y, width, height, 30,30);
	}

	public void update(){ }

	public void render(Graphics2D g){
		type = Keys.type;
		if(clicked && type != null){
			setText(type);
		}
		int height = getHeight(),width = getWidth(),x = getX(),y = getY();
		if(currentState.equals("clicked")) Utils.setRectangleStroke(g, t128, x,y,width,height, t64);
		else Utils.setRectangle(g, tFColor, textBox);
		if (getText().length()==0) {
			Utils.setFont(g, buttonFont, tFPlaceholder);
			g.drawString(placeholderText, getX()+20, getY() + getHeight() / 2  + Utils.getHeight(placeholderText, buttonFont, g) / 2);
		}else{
				Utils.setFont(g, buttonFont, tFFont);
				g.drawString(text, getX()+20, getY() + getHeight() / 2  + Utils.getHeight(text, buttonFont, g) / 2);
		}
	}

	public void mouseClicked(MouseEvent e){
		if(textBox.contains(e.getPoint())){
			clicked=true;
			currentState = "clicked";
		} else{
			clicked=false;
			currentState = "notClicked";
		}
	}

	public int getX(){
		return (int) textBox.getX();
	}
	public int getY(){
		return (int) textBox.getY();
	}

	public int getWidth(){
		return (int) textBox.getWidth();
	}

	public int getHeight(){
		return (int) textBox.getHeight();
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	public void setPlaceholderText(String placeholderText) {
		this.placeholderText = placeholderText;
	}

}
