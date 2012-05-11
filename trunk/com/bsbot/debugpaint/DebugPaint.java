package com.bsbot.debugpaint;

import java.awt.Graphics;

import com.bsbot.api.Methods;
import com.bsbot.launcher.BSLoader;

public abstract class DebugPaint {
	
	Methods paintMethods = BSLoader.getMethods();

	public abstract void paint(Graphics g);
	
	private boolean isEnabled = false;
	
	public void setEnabled(boolean which){
		isEnabled = which;
	}
	
	public boolean isEnabled(){
		return isEnabled;
	}
	
}
