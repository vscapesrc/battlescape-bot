package com.bsbot.wrappers;

import java.awt.Point;

import com.bsbot.api.Calculations;
import com.bsbot.api.Menu;
import com.bsbot.hooks.GameObject;
import com.bsbot.hooks.ObjectDef;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;


public class RSObject {
	private int id;
	private RSTile location;
	private com.bsbot.hooks.GameObject accessor;
	Mouse m = new Mouse();
	private ObjectDef objDef = null;

	public RSObject(int theId, RSTile loc, GameObject theAccessor) {
		this.id = theId;
		this.location = loc;
		this.accessor = theAccessor;
		this.objDef = BSLoader.getClient().getForIdObject(id);
	}

	public int getId() {
		return accessor.getId();
	}

	public RSTile getLocation() {
		return location;
	}

	public String getName() {
		return objDef.getName();
	}
	
    public Point getScreenLocation()
    {
    	Point p = Calculations.worldToScreen(accessor.getWorldX(), accessor.getWorldY(), accessor.getHeight()*2);
    	if(getName().contains("swing")){
    		p = Calculations.worldToScreen(accessor.getWorldX(), accessor.getWorldY(), accessor.getHeight() / 2);
    		p.y -= 20;
    	}
    	return new Point(p);
		/*Point screenPoint = Calculations.worldToScreen(((double) (getLocation()
				.getX() - BSLoader.getClient().getBaseX()) + 0.5D) * 128D,
				((double) (getLocation().getY() - BSLoader.getClient()
						.getBaseY()) + 0.5D) * 128D, 0);
		return screenPoint;*/
    	///return Calculations.tileToScreen(getLocation());
        ////return Calculations.worldToScreen(accessor.getWorldX(), accessor.getWorldY(), 0);
    }
    
    public boolean isOnScreen(){
    	return Calculations.pointOnGameScreen(getScreenLocation());
    }
    
  
    
    public void interact(String action)
    {
        Point p = getScreenLocation();
    ////    System.out.println(accessor.getWorldZ());
        if(Calculations.pointOnGameScreen(p))
        {
            if(Menu.isOpen()){
            	m.moveMouse(100, 100);
            ////////	System.out.println("menu is open");
            }
               /// Mouse.moveMouseRandomly(450);
            m.moveMouse(p);
            BSLoader.getMethods().sleep(600);
            String actions[] = Menu.getValidMenuActions();
        	/////System.out.println(actions[0]);
        	for(int i = 0; i < actions.length; i++){
        		actions[i] = actions[i].toLowerCase();
        	}
        	action = action.toLowerCase();
            if(actions[0] != null && actions[0].contains(action))
            {
            //////	System.out.println(actions[0]);
                m.clickMouse(p, true);
                return;
            }
            m.clickMouse(p, false);
            try{
            	Thread.sleep(300);
            }catch(Exception e){
            	
            }
            ////7BSLoader.getMethods().sleep(700);
            Menu.interact(action);
        } else
        {
            Point mm = Calculations.tileToMinimap(getLocation());
            if(mm.x != -1 && mm.y != -1)
                m.clickMouse(Calculations.tileToMinimap(getLocation()), true);
        }
    }

	

}
