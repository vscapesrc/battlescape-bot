package com.bsbot.debugpaint;

import java.awt.Color;
import java.awt.Graphics;

import com.bsbot.launcher.BSLoader;

public class DebugPosition extends DebugPaint {

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(BSLoader.getMethods().getMyPlayer().getLocation().toString(), 10, 10);

	}

}
