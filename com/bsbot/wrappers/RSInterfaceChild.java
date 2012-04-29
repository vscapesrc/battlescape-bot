package com.bsbot.wrappers;

import java.awt.Point;
import java.awt.Rectangle;

import com.bsbot.hooks.GameInterface;
import com.bsbot.hooks.ItemDef;
import com.bsbot.input.Mouse;

public class RSInterfaceChild {
	
	private GameInterface[] parent;
	
	private Mouse m = new Mouse();
	
	private GameInterface accessor;
	
	private int parentId;
	
	public RSInterfaceChild(final GameInterface[] theParent, GameInterface access, int parentId){
		parent = theParent;
		accessor = access;
		this.parentId = parentId;
	}
	
	public int getScreenX(){
		return accessor.getX();
	}
	
	public int getScreenY(){
		return accessor.getY();
	}
	
	public GameInterface getAccessor(){
		return accessor;
	}
	

	
	
	
	public RSInterface getParent(){
		
		return new RSInterface(parent, parentId);
	}
	
	public GameInterface getHook(){
		return accessor;
	}
	
	public int getId(){
		return accessor.getId();
	}
	
	public Rectangle getArea() {
		return new Rectangle(accessor.getX(), accessor.getY(), accessor.getWidth(), accessor.getHeight());
	}
	
	public String getText(){
		return accessor.getText();
	}
	
	public boolean doClick(boolean leftClick) {

		Rectangle rect = getArea();
		if (rect.x == -1 || rect.y == -1 || rect.width == -1 || rect.height == -1) {
			return false;
		}
		
		int x = accessor.getX()+5;
		System.out.println("x:" + x);
		Point p = new Point(x, accessor.getY()); ///getArea().x+getRandomPoint().x, getArea().y+getRandomPoint().y);
		System.out.println("x: "  +x + " y: " +accessor.getY() + " id:" + accessor.getId());
		System.out.println(p.toString());
		m.moveMouse(p);
		try{
			Thread.sleep(400);
		}catch(Exception e){
			
		}
		m.clickMouse(p, true);
	///	
		return true;
	}
	
	public Point getRandomPoint(){
	    int randomX = nextInt(0, getArea().width);
	    int randomY = nextInt(0, getArea().height);
	 
	    return new Point(randomX, randomY);
	 
	}
	
	
	
	  double getRandomInteger (int min, int max) {
		    return min + Math.floor(Math.random()*(max + 1 - min));
		  }

    public static int nextInt(int min, int max)
    {
        return (int)(Math.random() * (double)(max - min)) + min;
    }
	
	
	
	

}
