package com.bsbot.debugpaint;

import java.awt.Color;
import java.awt.Graphics;

import com.bsbot.launcher.BSLoader;

public class DebugMouse extends DebugPaint {

	@Override
	public void paint(Graphics g) {
		if (BSLoader.getLoader() != null && BSLoader.getLoader().mousePos != null) {
			int x = (int) BSLoader.getLoader().mousePos.getX();
			int y = (int) BSLoader.getLoader().mousePos.getY();
			g.setColor(Color.RED);
			g.translate(8, 2);
			g.drawLine((x - 8), (y - 8), x + 8, y + 8);
			g.drawLine((x - 8), (y + 8), x + 8, y - 8);
			g.drawString("Mouse X: " + x + " Y: " + y, 10, 20);
			g.translate(-8, -2);
		}
		
	}

}
