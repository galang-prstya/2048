package model;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {

	public static Image scaleImage(Image image, String path, int divWidth, int divHeight) {
		BufferedImage iBuf;
		try {
			iBuf = ImageIO.read(new File(path));
			image = iBuf.getScaledInstance(iBuf.getWidth()/divWidth, iBuf.getHeight()/divHeight, Image.SCALE_DEFAULT);
			image = new ImageIcon(image).getImage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return image;
	}
	public static ArrayList<String> convertToStrings(ArrayList<Integer> data){
		ArrayList<String> val = new ArrayList<>();
		for(Integer n : data) val.add(n.toString());
		return val;
	}
	
	public static int getWidth(String text, Font font, Graphics2D g) {
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		return (int) bounds.getWidth();
	}

	public static int getHeight(String text, Font font, Graphics2D g) {
		g.setFont(font);
		if(text.length() == 0) return 0;
		TextLayout tl = new TextLayout(text, font, g.getFontRenderContext());
		return (int) tl.getBounds().getHeight();
	}
	
//	public static String formatTime(long millis) {
//		String formattedTime = "";
//
//		int hours = (int) (millis / 3600000);
//		if (hours >= 1) {
//			millis -= hours * 3600000L;
//			formattedTime += (hours < 10) ? "0" + hours + ":" :  hours + ":";
//		} else{
//			formattedTime += "00:";
//		}
//
//		int minutes = (int) (millis / 60000);
//		if (minutes >= 1) {
//			millis -= minutes * 60000L;
//			formattedTime += (minutes < 10) ? "0" + minutes + ":" :  minutes + ":";
//		} else formattedTime += "00:";
//
//		int seconds = (int) millis / 1000;
//		if (seconds >= 1) {
//			formattedTime += (seconds < 10) ? "0" + seconds :  seconds;
//		} else formattedTime += "00";
//
//		return formattedTime;
//	}
	
	public static void setRectangle(Graphics2D g, Color color, Shape shape) {
		g.setColor(color);
		g.fill(shape);
	}
	public static void setRectangleStroke(Graphics2D g, Color color, int x, int y, int width, int height, Color strokeColor) {
		Stroke basicStroke = new BasicStroke(1f);
		g.setColor(strokeColor);
		g.setStroke(basicStroke);
		g.setColor(color);
		g.drawRoundRect(x,y,width,height,30,30);
//		g.fill(shape);
	}
	public static void setFont(Graphics2D g, Font font, Color color) {
		g.setFont(font);
		g.setColor(color);
	}


	public static int centerX(int panelWidth, String text, Graphics2D g, Font font){
		return panelWidth / 2 - Utils.getWidth(text, font, g) / 2;
	}
	public static int centerY(int panelWidth, String text, Graphics2D g, Font font){
		return panelWidth / 2 - Utils.getHeight(text, font, g) / 2;
	}
}
