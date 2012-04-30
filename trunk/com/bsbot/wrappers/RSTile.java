package com.bsbot.wrappers;

import com.bsbot.launcher.BSLoader;

public class RSTile {
	
	int x, y, plane;
	
	public RSTile(int xa, int ya){
		this.x = xa;
		this.y=ya;
		this.plane=BSLoader.getClient().getPlane();
	}
	public RSTile(int xa, int ya, int plane){
		this.x = xa;
		this.y=ya;
		this.plane=plane;
	}
	
	
	public String toString(){
		return new String("X: " +x + " Y: " +y);
	}
	
	public int getPlane(){
		return plane;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	

}
