package com.bsbot.debugpaint;

import java.awt.Graphics;

import com.bsbot.api.Calculations;
import com.bsbot.hooks.GameObject;
import com.bsbot.wrappers.RSObject;

public class DebugObjects extends DebugPaint{

	@Override
	public void paint(Graphics g) {
		for(RSObject go : paintMethods.objects.getAll()){
			if(go != null){
				if(Calculations.pointOnGameScreen(go.getScreenLocation())){
					g.drawString(""+go.getId(), (int)go.getScreenLocation().getX(), (int)go.getScreenLocation().getY());
				}
			}
		}
		
	}

}
