package com.bsbot.wrappers;

import java.awt.Point;

import com.bsbot.api.Calculations;
import com.bsbot.api.Menu;
import com.bsbot.hooks.ItemDef;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;

public class RSGroundItem {
	private int id;
	private RSTile location;
	private ItemDef itemDef;
	Mouse m = new Mouse();

	public RSGroundItem(int theId, RSTile loc) {
		this.id = theId;
		this.location = loc;
		this.itemDef = BSLoader.getClient().getForId(id);
	}

	public int getId() {
		return id;
	}

	public RSTile getLocation() {
		return location;
	}

	public String getName() {
		return itemDef.getName();
	}


	public Point getScreenLocation() {
		Point screenPoint = Calculations.worldToScreen(((double) (getLocation()
				.getX() - BSLoader.getClient().getBaseX()) + 0.5D) * 128D,
				((double) (getLocation().getY() - BSLoader.getClient()
						.getBaseY()) + 0.5D) * 128D, 0);
		//Point screenPoint = Calculations.tileToScreen(getLocation());

		return screenPoint;
	}

	public void interact(String action) {
		Point p = getScreenLocation();
		System.out.println(p);
		if (Calculations.pointOnGameScreen(p)) {
			System.out.println("point on screen");
			if (Menu.isOpen()) {
				System.out.println("menu is open");
			}
			System.out.println("moving to p");
			m.moveMouse(p);
			BSLoader.getMethods().sleep(300);
			String actions[] = Menu.getValidMenuActions();
			System.out.println(actions[0]);
			for (int i = 0; i < actions.length; i++) {
				System.out.println(actions[i]);
				actions[i] = actions[i].toLowerCase();
			}
			action = action.toLowerCase();
			if (actions[0] != null && actions[0].contains(action) && actions[0].contains(getName())) {
				System.out.println(actions[0]);
				m.moveMouse(p);
				try{
					Thread.sleep(300);
				}catch(Exception e){
					
				}
				m.clickMouse(p, true);
				return;
			}
			m.moveMouse(p);
			m.clickMouse(p, false);
			try{
			Thread.sleep(700);
			}catch(Exception e){
				e.printStackTrace();
			}
			Menu.interact(action + " " + getName());
		} else {
			Point mm = Calculations.tileToMinimap(getLocation());
			if (mm.x != -1 && mm.y != -1)
				m.clickMouse(Calculations.tileToMinimap(getLocation()), true);
		}
	}
}
