package com.bsbot.debugpaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSNPC;

public class DebugNpcs extends DebugPaint{

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.YELLOW);
		for(RSNPC npc : BSLoader.getMethods().getAllNpcs()){
			if(npc != null && npc.getScreenLocation() != null && !npc.getScreenLocation().equals(new Point(-1, -1)) && npc.isOnScreen()){
				Point p = npc.getScreenLocation();
				g.drawString(Integer.toString(npc.getId()), p.x, p.y);
			}
		}
		g.setColor(Color.WHITE);
		
	}

}
