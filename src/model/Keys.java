package model;

import view.GuiTextField;

import java.awt.event.KeyEvent;

public class Keys {

	public static boolean[] pressed = new boolean[222],
			prev = new boolean[222];
	public static String type="";

//	public static Keys getInstance(){
//		if(keys == null){
//			keys = new Keys();
//		}
//		return keys;
//	}
	public static void update(){
		for(int i = 0; i < 4; i++){
			if(i == 0) prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
			if(i == 1) prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
			if(i == 2) prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
			if(i == 3) prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
		}
	}

	public static void keyPressed(KeyEvent e){
		pressed[e.getKeyCode()] = true;
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && GuiTextField.clicked){
			if(type.length()>=1){
				type = type.substring(0, type.length() - 1);
			}
		}
	}

	public static void keyReleased(KeyEvent e){
		pressed[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && GuiTextField.clicked){
			if(type.length()>=1){
				type = type.substring(0, type.length() - 1);
			}
		}
	}

	public static String keyTyped(KeyEvent e){
		if(GuiTextField.clicked){
			type+=String.valueOf(e.getKeyChar());
		}
		return type;
	}
	
}
