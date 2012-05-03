package com.bsbot.api;



import java.util.ArrayList;
import java.util.List;

import com.bsbot.hooks.GroundItem;
import com.bsbot.hooks.Node;
import com.bsbot.hooks.NodeList;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSGroundItem;
import com.bsbot.wrappers.RSTile;



public class GroundItems {

	public GroundItems() {
	}

	public RSGroundItem getNearest(int id) {
		RSGroundItem returnItem = null;
		int maxDist = 104;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				RSGroundItem items[] = getGroundItemsAt(x, y);
				RSGroundItem agrounditem[];
				int j = (agrounditem = items).length;
				for (int i = 0; i < j; i++) {
					RSGroundItem item = agrounditem[i];
					System.out.println(item.getId());
					if (item.getId() == id
							&& Calculations.distanceBetween(Methods
									.getMyPlayer().getLocation(), item
									.getLocation()) < (double) maxDist) {
						maxDist = (int) Calculations.distanceBetween(Methods
								.getMyPlayer().getLocation(), item
								.getLocation());
						returnItem = item;
					}
				}

			}

		}

		return returnItem;
	}

	public RSGroundItem getNearest(String name) {
		RSGroundItem returner = null;
		int maxDist = 104;
		for (RSGroundItem it : getAll()) {
			if (it.getName().toLowerCase().equals(name.toLowerCase())
					&& Calculations.distanceBetween(Methods.getMyPlayer()
							.getLocation(), it.getLocation()) < (double) maxDist) {
				maxDist = (int) Calculations.distanceBetween(Methods
						.getMyPlayer().getLocation(), it.getLocation());
				returner = it;
			}

		}
		return returner;
	}

	public RSGroundItem[] getAll() {
		ArrayList<RSGroundItem> temp = new ArrayList<RSGroundItem>();
		RSGroundItem returnItem = null;
		int maxDist = 104;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				RSGroundItem items[] = getGroundItemsAt(x, y);
				RSGroundItem agrounditem[];
				if(items != null){
				int j = (agrounditem = items).length;
				for (RSGroundItem gi : items) {
					if (gi != null) {
						temp.add(gi);
					}
				}

			}
			}

		}

		return temp.toArray(new RSGroundItem[temp.size()]);
	}

	public RSGroundItem[] getGroundItemsAt(int x, int y) {
		NodeList nl = BSLoader.getClient().getGroundArray()[BSLoader
				.getClient().getPlane()][x][y];
		if (nl == null) {
			return new RSGroundItem[0];
		}
			ArrayList<RSGroundItem> list = new ArrayList<RSGroundItem>();


				for (Node curNode = nl.getFirstHook(false); curNode != null; curNode = nl.getNextHook(173))
					if (curNode instanceof GroundItem) {
						GroundItem item = (GroundItem) curNode;
						list.add(new RSGroundItem(item.getId(), new RSTile(
								(x + BSLoader.getClient().getBaseX()),
								(y + BSLoader.getClient().getBaseY()))));
					}

				return (RSGroundItem[]) list.toArray(new RSGroundItem[list
						.size()]);
	}
}