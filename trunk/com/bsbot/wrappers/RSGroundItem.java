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

		return screenPoint;
	}

	public void interact(String action) {
		Point p = getScreenLocation();
		System.out.println(p);
		if (Calculations.pointOnScreen(p)) {
			if (Menu.isOpen()) {
				System.out.println("menu is open");
			}
			m.moveMouse(p);
			BSLoader.getMethods().sleep(300);
			String actions[] = Menu.getValidMenuActions();
			System.out.println(actions[0]);
			for (int i = 0; i < actions.length; i++) {
				System.out.println(actions[i]);
				actions[i] = actions[i].toLowerCase();
			}
			action = action.toLowerCase();
			if (actions[0] != null && actions[0].contains(action + " " + getName())) {
				System.out.println(actions[0]);
				m.clickMouse(p, true);
				return;
			}
			m.clickMouse(p, false);
			BSLoader.getMethods().sleep(700);
			Menu.interact(action + " " + getName().toLowerCase());
		} else {
			Point mm = Calculations.tileToMinimap(getLocation());
			if (mm.x != -1 && mm.y != -1)
				m.clickMouse(Calculations.tileToMinimap(getLocation()), true);
		}
	}
}
