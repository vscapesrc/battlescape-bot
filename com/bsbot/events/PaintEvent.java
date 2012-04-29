package com.bsbot.events;

import java.awt.Graphics;

import scripts.Script;

import com.bsbot.launcher.BSLoader;

public class PaintEvent {
	
	public void paintHack(Graphics g) {
		try{
		if(BSLoader.getRunningScript() != null){
			BSLoader.getRunningScript().paint(g);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
