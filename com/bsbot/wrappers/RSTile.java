package com.bsbot.wrappers;

public class RSTile {
	
	int x, y;
	
	public RSTile(int xa, int ya){
		this.x = xa;
		this.y=ya;
	}
	
	public String toString(){
		return new String("X: " +x + " Y: " +y);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	

}
