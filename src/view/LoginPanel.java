package view;

import model.Game;
import model.Player;
import model.Utils;

//import java.awt.*;
import java.sql.SQLException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static model.ColorPallette.*;
import static model.FontSet.*;

public class LoginPanel extends GuiPanel{

	private final String title = "Login Player";
	private final int buttonWidth = 100,
			buttonHeight = 50,
			buttonSpacing = 20,
			textFieldHeight = 50,
			textFieldWidth = Game.WIDTH/2 + 100;
	private boolean cekPlayer, addSuccess, loginPressed, signUpPressed;

	private GuiButton loginButton, signUpButton, backButton;
	private GuiTextField tfName;

	public LoginPanel(){
		super();
		signUpButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, Game.HEIGHT-250, buttonWidth, buttonHeight);
		loginButton = new GuiButton(signUpButton.getX(), signUpButton.getY() - buttonSpacing - buttonHeight, buttonWidth, buttonHeight);
		tfName = new GuiTextField(Game.WIDTH / 2 - textFieldWidth / 2, loginButton.getY()-2*buttonSpacing-buttonHeight, textFieldWidth, textFieldHeight);

		tfName.setPlaceholderText("Name");
		loginButton.setText("Login");
		signUpButton.setText("Sign Up");

		addButton(loginButton);
		addButton(signUpButton);
		addTextField(tfName);

		backButton = new GuiButton(Game.WIDTH / 2 - 400 / 2, 520, 400, 40);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Back to Main Menu");
		addButton(backButton);
		loginButton.addActionListener(e -> {
			if(tfName.getText()!=null){
				loginPressed=true;
				signUpPressed=false;
				if(Player.getInstance().getPlayerByName(tfName.getText()) != null){
					cekPlayer=true;
					Player.getInstance().getPlayerByName(tfName.getText());
				} else{
					cekPlayer=false;
				}
			}
		});
		signUpButton.addActionListener(e -> {
			if(tfName.getText()!=null){
				signUpPressed=true;
				loginPressed=false;
				if(Player.getInstance().getPlayerByName(tfName.getText()) == null){
					try {
						Player.getInstance().addPlayer(tfName.getText());
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					addSuccess=true;
					Player.getInstance().getPlayerByName(tfName.getText());
				} else{
					addSuccess=false;
				}
			}
		});
	}

	private void drawSignUpCheck(Graphics2D g){
		String cekText;
		if(!addSuccess){
			g.setColor(t1024);
			cekText="Player Sudah Terdaftar";
		} else{
			g.setColor(lightblue);
			cekText="Berhasil Mendaftar dan Login sebagai "+ Player.getInstance().getName();
		}
		g.setFont(warningFont);
		g.drawString(cekText, tfName.getX(), tfName.getY()+textFieldHeight+Utils.getHeight(cekText,warningFont,g)+5);
	}

	private void drawLoginCheck(Graphics2D g){
		String cekText;
		if(!cekPlayer){
			g.setColor(t1024);
			cekText="Gagal Login, Tekan Tombol SignUp";
		} else{
			g.setColor(lightblue);
			cekText="Berhasil Login sebagai " + Player.getInstance().getName();
		}
		g.setFont(warningFont);
		g.drawString(cekText, tfName.getX(), tfName.getY()+textFieldHeight+Utils.getHeight(cekText,warningFont,g)+5);
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(blue);
		g.drawString(title, Game.WIDTH / 2 - Utils.getWidth(title, leaderboardTitle, g) / 2, Game.HEIGHT/2-Utils.getHeight(title, leaderboardTitle, g)-120);
		if(loginPressed){
			drawLoginCheck(g);
		}
		if (signUpPressed){
			drawSignUpCheck(g);
		}
	}
}
