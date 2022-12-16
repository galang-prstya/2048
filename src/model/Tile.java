package model;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class Tile extends Entity{

	public static Tile t;
	public static final int
			SLIDE_SPEED = 30,
			RADIUS_WIDTH = 15,
			RADIUS_HEIGHT =15;

	private int value;
	private double scale = 0.1,
			scaleCombine = 1.2;
	
	private boolean beginningAnimation = true,
			canCombine = true,
			combineAnimation = false;
	
	private BufferedImage tileImage,
			beginningImage,
			combineImage;
	private Color background,
			text;
	private Font font;

	public static Tile getInstance(){
		if (t == null) {
			t = new Tile(0,0,0);
		}
		return t;
	}
	public Tile(int value, int x, int y) {
		getWIDTH();
		getHEIGHT();
		super.x = x;
		super.y = y;
		this.value = value;
		tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		beginningImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		combineImage = new BufferedImage(WIDTH * 2, HEIGHT * 2, BufferedImage.TYPE_INT_ARGB);
		drawImage();
	}

	public void update() {
		if (beginningAnimation) {
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scale * WIDTH / 2, HEIGHT / 2 - scale * HEIGHT / 2);
			transform.scale(scale, scale);
			Graphics2D g2d = (Graphics2D) beginningImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scale += 0.1;
			g2d.dispose();
			if(scale >= 1) beginningAnimation = false;
		}
		else if(combineAnimation){
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scaleCombine * WIDTH / 2, HEIGHT / 2 - scaleCombine * HEIGHT / 2);
			transform.scale(scaleCombine, scaleCombine);
			Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scaleCombine -= 0.08;
			g2d.dispose();
			if(scaleCombine <= 1) combineAnimation = false;
		}
	}
	
	public void render(Graphics2D g){
		if(beginningAnimation) g.drawImage(beginningImage, x, y, null);
		else if(combineAnimation)
			g.drawImage(combineImage, (int)(x + WIDTH / 2 - scaleCombine * WIDTH / 2), (int)(y + HEIGHT / 2 - scaleCombine * HEIGHT / 2), null);
		else g.drawImage(tileImage, x, y, null);
	}
	
	private void drawImage() {
		Graphics2D g = (Graphics2D) tileImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		switch (value){
			case 2:
				setTileColor(t2,tFont24);
				break;
			case 4:
				setTileColor(t4,tFont24);
				break;
			case 8:
				setTileColor(t8,tFont8);
				break;
			case 16:
				setTileColor(t16,tFont8);
				break;
			case 32:
				setTileColor(t32,tFont8);
			case 64:
				setTileColor(t64,tFont8);
				break;
			case 128:
				setTileColor(t128,tFont8);
				break;
			case 256:
				setTileColor(t256,tFont8);
				break;
			case 512:
				setTileColor(t512,tFont8);
				break;
			case 1024:
				setTileColor(t1024,tFont8);
				break;
			case 2048:
				setTileColor(t2048,tFont8);
				break;
			default:
				setTileColor(red,tFont8);
				break;
		}
		g.setColor(background);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, RADIUS_WIDTH, RADIUS_HEIGHT);

		g.setColor(text);
		font = (value <= 64) ? tileFont : fontType;
		g.setFont(font);

		int drawX = WIDTH / 2 - Utils.getWidth("" + value, font, g) / 2;
		int drawY = HEIGHT / 2 + Utils.getHeight("" + value, font, g) / 2;
		g.drawString("" + value, drawX, drawY);
		g.dispose();
	}
	public void setTileColor(Color b, Color t) {
		background=b;
		text=t;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		drawImage();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		super.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		super.y = y;
	}

	public void setHEIGHT(int HEIGHT) {
		super.HEIGHT = HEIGHT;
	}
	public int getHEIGHT() {
		setHEIGHT(100);
		return HEIGHT;
	}

	public void setWIDTH(int WIDTH) {
		super.WIDTH = WIDTH;
	}
        @Override
	public int getWIDTH() {
		setWIDTH(100);
		return WIDTH;
	}

	public void setCombineAnimation(boolean combineAnimation){
		this.combineAnimation = combineAnimation;
		if(combineAnimation) scaleCombine = 1.2;
	}

	public boolean canCombine() {
		return canCombine;
	}

	public void setCanCombine(boolean canCombine) {
		this.canCombine = canCombine;
	}
}
