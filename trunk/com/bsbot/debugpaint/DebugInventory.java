package com.bsbot.debugpaint;

import java.awt.Graphics;

import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSItem;

public class DebugInventory extends DebugPaint {

	@Override
	public void paint(Graphics g) {
		for(RSItem i : BSLoader.getMethods().inventory.getItems()){
			if(i != null){
				g.drawString(""+i.getId(), i.getScreenLocation().x, i.getScreenLocation().y);
			}
		}

	}

}
