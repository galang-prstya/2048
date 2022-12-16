package view;

import model.Keys;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiPanel {

	private ArrayList<GuiButton> buttons;
	private ArrayList<GuiTextField> tField;

	public GuiPanel(){
		buttons = new ArrayList<>();
		tField = new ArrayList<>();
	}

	public void update(){
		for(int i=0; i<buttons.size(); i++){
			buttons.get(i).update();
		}
	}
	public void render(Graphics2D g){
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).render(g);
		}
		for(int i = 0; i < tField.size(); i++){
			tField.get(i).render(g);
		}
	}

	public void addButton(GuiButton button){
		buttons.add(button);
	}

	public void addTextField(GuiTextField tf){
		tField.add(tf);
	}

	public void removeButton(GuiButton button){
		buttons.remove(button);
	}
	public void removeTextField(GuiTextField tf){
		tField.remove(tf);
	}

	public void mouseClicked(MouseEvent e){
		for(int i = 0; i < tField.size(); i++){
			tField.get(i).mouseClicked(e);
		}
	}

	public void mousePressed(MouseEvent e){
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e){
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).mouseReleased(e);
		}
	}

	public void mouseDragged(MouseEvent e){
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e){
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).mouseMoved(e);
		}
	}
}
