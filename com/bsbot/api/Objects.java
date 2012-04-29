package com.bsbot.api;

import java.util.ArrayList;
import java.util.List;

import com.bsbot.hooks.GameObject;
import com.bsbot.hooks.Ground;
import com.bsbot.hooks.Node;
import com.bsbot.hooks.NodeList;
import com.bsbot.hooks.Npc;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSItem;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSTile;

// Referenced classes of package net.xotiksys.script.methods:
//            Players, Calculations

public class Objects {

	Methods methods = null;
	public Objects(Methods m) {
		this.methods=m;
	}

	public static RSObject getNearest(int id) {
		RSObject returnGameObject = null;
		int maxDist = 50;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				RSObject items[] = getAtLocal(x, y);
				RSObject agrounditem[];
				int j = (agrounditem = items).length;
				for (int i = 0; i < j; i++) {
					RSObject item = agrounditem[i];
					if (item.getId() == id
							&& Calculations.distanceBetween(Methods
									.getMyPlayer().getLocation(), item
									.getLocation()) < (double) maxDist) {
						maxDist = (int) Calculations.distanceBetween(Methods
								.getMyPlayer().getLocation(), item
								.getLocation());
						returnGameObject = item;
					}
				}

			}

		}

		return returnGameObject;
	}
	
	public static RSObject getNearestByName(String name){
		RSObject returnObj = null;
		RSObject[] all = getAll();
		int maxDist = 9000;
		int curX = Methods.getMyPlayer().getLocation().getX();
		int curY = Methods.getMyPlayer().getLocation().getY();
		double dist = -1;
		RSObject cur = null;
		for (RSObject a : getAll()) {
			if (a != null
					&& a.getName() != null)
				if (a.getName().equalsIgnoreCase(name)) {
					int x, y;
					/*
					 * x = a.getX(); y = a.getY();
					 */
					x = a.getLocation().getX();
					y = a.getLocation().getY();
					double distance = Math.sqrt((curX - x) * (curX - x)
							+ (curY - y) * (curY - y));
					if (distance < dist || dist == -1) {
						dist = distance;
						cur = a;
					}
				}
		}
	/*	for(RSObject obj : all){
			if(obj.getName().equalsIgnoreCase(name)){
				if(Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), obj.getLocation()) < maxDist){
					maxDist = Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), obj.getLocation());
					returnObj = obj;
				}
			}
		}*/
		return cur;
	}

	public static RSObject[] getAll() {
		ArrayList<RSObject> temp = new ArrayList<RSObject>();
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				RSObject items[] = getAtLocal(x, y);
				int j = (items).length;
				for (RSObject gi : items) {
					if (gi != null) {
						
						temp.add(gi);
					}
				}

			}

		}
		return temp.toArray(new RSObject[temp.size()]);
	}

	/*
	 * public static RSObject[] getAll(){ return getAll(52); }
	 */


	private static RSObject[] getAtLocal(int x, int y) {
		ArrayList<RSObject> objects = new ArrayList<RSObject>();
		if (BSLoader.getClient().getWorldController().getGroundArray() == null) {
			return new RSObject[0];
		}

		
			Ground rsGround = BSLoader.getClient().getWorldController().getGroundArray()[0][x][y];

			if (rsGround != null) {
				RSObject rsObj;

				
				x += BSLoader.getClient().getBaseX();
				y += BSLoader.getClient().getBaseY();

				// Interactable (e.g. Trees)
					for(GameObject obj : rsGround.getGround()){
						if (obj != null && obj instanceof GameObject) {
							if (obj.getId() != -1) {
								objects.add(new RSObject(obj.getId(), new RSTile(x, y), obj));
							}
						}
					}
				}
		return (RSObject[]) objects.toArray(new RSObject[objects.size()]);
	}
}