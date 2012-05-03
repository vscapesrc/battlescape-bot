package com.bsbot.wrappers;

import java.awt.Point;

import com.bsbot.api.Calculations;
import com.bsbot.api.Menu;
import com.bsbot.hooks.Client;
import com.bsbot.hooks.Entity;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;


public abstract class RSEntity {
	
	Mouse m = new Mouse();

	protected abstract com.bsbot.hooks.Entity getAccessor();


	
	public RSEntity getInteracting(){
		int interact = getAccessor().getInteracting();
		if (interact == -1) {
			return null;
		}
		if (interact < 32768) {
			return new RSNPC(interact);
		}else{
			interact =- 32768;
			return null;
		}
	}
	
	public int getCurrentHealth(){
		return getAccessor().getCurrentHealth();
	}
	
	public int getMaxHealth(){
		return getAccessor().getMaxHealth();
	}
	
	


	
    public boolean isOnScreen(){
    	return Calculations.pointOnGameScreen(getScreenLocation());
    }
    public void interact(String action)
    {
        Point p = getScreenLocation();
        if(Calculations.pointOnGameScreen(p))
        {
            if(Menu.isOpen()){
            	m.moveMouse(1, 1);
            }

            m.moveMouse(p);
            String actions[] = Menu.getValidMenuActions();

        	for(int i = 0; i < actions.length; i++){
        		actions[i] = actions[i].toLowerCase();
        	}
        	action = action.toLowerCase();
            if(actions[0] != null && actions[0].contains(action))
            {
            	System.out.println(actions[0]);
                m.clickMouse(p, true);
                return;
            }
            m.clickMouse(p, false);
            BSLoader.getMethods().sleep(300);
            Menu.interact(action);
        } else
        {
            Point mm = Calculations.tileToMinimap(getLocation());
            if(mm.x != -1 && mm.y != -1)
                m.clickMouse(Calculations.tileToMinimap(getLocation()), true);
        }
    }
    
    public Point getScreenLocation()
    {
        return Calculations.worldToScreen(getAccessor().getX(), getAccessor().getY(), getAccessor().getHeight() / 2);
    }
	
	
	public String getName() {
		return "-1";
	}

	public int getHeight() {
		return getAccessor().getHeight();
	}

	public RSTile getLocation() {
		Entity c = getAccessor();
		if (c == null) {
			return new RSTile(-1, -1);
		}
	int x = BSLoader.getClient().getBaseX() + (c.getX() >> 7);
		int y = BSLoader.getClient().getBaseY() + (c.getY() >> 7);
		Client cl = BSLoader.getClient();
		return new RSTile(x, y);
		//alternative:
       // return new RSTile(getAccessor().getSmallX()[0] + BSLoader.getClient().getBaseX(), getAccessor().getSmallY()[0] + BSLoader.getClient().getBaseY());
	}

	public int getX() {
		return getAccessor().getX();
	}

	public int getAnimation() {
		return getAccessor().getAnimation();
	}

	public int getY() {
		return getAccessor().getY();
	}


}
