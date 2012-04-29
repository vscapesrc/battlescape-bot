package com.bsbot.api;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.bsbot.hooks.GameInterface;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSItem;

public class Inventory {

	/*
	 * public RSItem[] getInventoryCache() { ArrayList<RSItem> list = new
	 * ArrayList<RSItem>(); for (com.bsbot.hooks.RSInterface[] inface :
	 * BSLoader.getClient() .getInterfaceCache()) { if (inface != null) { for
	 * (com.bsbot.hooks.RSInterface iface2 : inface) { if (iface2.getInv() !=
	 * null) { for (int a = 0; a < iface2.getInv().length; a++) { list.add(new
	 * RSItem(iface2.getInv()[a])); } } } }
	 * 
	 * } return list.toArray(new RSItem[list.size()]); }
	 */
	static Methods methods;

	public Inventory(Methods m) {
		methods = m;
	}
	
	public boolean containsItem(String name){
		for(RSItem item : getItems()){
			if(item.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}

	public int[] getInventoryCache() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (com.bsbot.hooks.GameInterface[] inface : BSLoader.getClient()
				.getInterfaceCache()) {
			if (inface != null) {
				for (com.bsbot.hooks.GameInterface iface2 : inface) {
					if (iface2 != null) {

						if (iface2.getId() == 4521985
								&& !methods.banking.isOpen()) {
							if (iface2.getInv() != null) {
								for (int a = 0; a < iface2.getInv().length; a++) {
									list.add(iface2.getInv()[a]);
								}
							}
						} else if (methods.banking.isOpen()
								&& iface2.getId() == 7471105) {
							if (iface2.getInv() != null) {
								for (int a = 0; a < iface2.getInv().length; a++) {
									list.add(iface2.getInv()[a]);
								}
							}
						}
					}
				}
			}
		}
		return convertIntegers(list);
	}

	public int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}

	public final int INTERFACE_INVENTORY = 149;

	static Mouse n = new Mouse();

	public Inventory() {
	}

	public RSItem getItem(String name) {
		for (RSItem one : getItems()) {
			if (one != null && one.getId() != -1 && one.getName() != null) {
				if (one.getName().equals(name)) {
					return one;
				}
			}
		}
		return null;
	}
	
	public RSItem getItem(int id) {
		for (RSItem one : getItems()) {
			if (one != null && one.getId() != -1 && one.getId() == id) {
					return one;
			}
		}
		return null;
	}

	/*
	 * public String getSelectedItemName() { return
	 * BSLoader.getClient().getSelectedItem(); }
	 */

	public int[] getItemIds() {
		return getInventoryCache();
	}

	/*
	 * public int getStackSize(int slot) { int invCache[] =
	 * BSLoader.getClient().getInterfaceCache()[3214] .getInvStackSizes();
	 * return invCache[slot - 1] + 1; }
	 */

	/*
	 * public void clickItemByName(String name) { int invCache[] =
	 * BSLoader.getClient().getInterfaceCache()[3214].getInvStackSizes(); for
	 * (int i = 0; i < invCache.length; i++) { System.out.println(invCache[i]);
	 * } }
	 */

	public static void interactSlotBank(int slot, String action) {
		if (Menu.isOpen()) {
			n.moveMouse(new Point(10, 10));
			methods.sleep(1000);
		}
		/*
		 * if (BSLoader.getClient().getOpenTab() != 3){
		 * 
		 * n.mouse(new Point(nextInt(627, 654), nextInt(169, 196)),
		 * n.LEFT_BUTTON); }
		 */
		// /////System.out.println("clicking mouse");
		Point p = getInventoryItemPoint(slot);
		n.moveMouse(p);
		methods.sleep(1000);
		// n.mouse(p, n.LEFT_BUTTON);
		n.clickMouse(p, false);
		// }// //(getInventoryItemPoint(slot),
		methods.sleep(1000);
		// //System.out.println("in menu.interact()");
		Menu.interact(action, false);
	}
	
	public static void interactSlot(int slot, String action) {
		if (Menu.isOpen()) {
			n.moveMouse(new Point(10, 10));
			methods.sleep(1000);
		}
		/*
		 * if (BSLoader.getClient().getOpenTab() != 3){
		 * 
		 * n.mouse(new Point(nextInt(627, 654), nextInt(169, 196)),
		 * n.LEFT_BUTTON); }
		 */
		// /////System.out.println("clicking mouse");
		Point p = getInventoryItemPoint(slot);
		n.moveMouse(p);
		methods.sleep(150);
        String actions[] = Menu.getValidMenuActions();
    ///////	System.out.println(actions[0]);
    	for(int i = 0; i < actions.length; i++){
    		actions[i] = actions[i].toLowerCase();
    	}
    	action = action.toLowerCase();
        if(actions[0] != null && actions[0].contains(action))
        {
        	System.out.println(actions[0]);
            n.clickMouse(p, true);
            return;
        }
		///methods.sleep(1000);
		// n.mouse(p, n.LEFT_BUTTON);
		n.clickMouse(p, false);
		// }// //(getInventoryItemPoint(slot),
		methods.sleep(1000);
		// //System.out.println("in menu.interact()");
		Menu.interact(action, true);
	}
	
	

	public void interact(int id, String action) {
		if (getFirstSlotWith(id) != -1) {
			interactSlot(getFirstSlotWith(id), action);
		}
	}

	public void dropAllBut(int except) {
		for (int i = 1; i < 27; i++) {
			int slotItem = getSlotItem(i);
			if (slotItem != except && slotItem != -1) {
				interactSlot(i, "Drop");
				// /sleep(nextInt(500, 850));
			}
		}

	}

	public void dropAll() {
		for (int i = 1; i < 27; i++) {
			int slotItem = -1;
			slotItem = getSlotItem(i);
			if (slotItem != -1) {
				interactSlot(i, "Drop");
				methods.sleep(nextInt(500, 850));
			}

		}
	}

	public int getSlotItem(int slot) {
		int invCache[] = getInventoryCache();
		return invCache[slot];
	}

	public int getFirstSlotWith(int id) {
		for (int i = 0; i < 28; i++) {
			if (getSlotItem(i) == id) {
				return i + 1;
			}
		}
		return -1;
	}

	public void clickItem(int idArray[]) {
		for (int i = 1; i < 29; i++) {
			int slotID = getSlotItem(i);
			int ai[];
			int k = (ai = idArray).length;
			for (int j = 0; j < k; j++) {
				int id = ai[j];
				if (id == slotID) {
					clickSlot(i);
					return;
				}
			}

		}

	}

	public void clickItem(int id) {
		n.clickMouse(getInventoryItemPoint(getFirstSlotWith(id)), false);
	}

	public void clickSlot(int slot) {
		n.clickMouse(getInventoryItemPoint(slot), false);
	}

	/*
	 * public RSComponent getInterface() { RSInterface in = new
	 * RSInterface(3123);
	 * 
	 * return new RSComponent(in., 3123); }
	 */

	public RSItem[] getItems() {
		final List<RSItem> out = new ArrayList<RSItem>();
		for (int i = 0; i < getInventoryCache().length; i++) {
			RSItem in = new RSItem(getInventoryCache()[i] - 1, i + 1);
			out.add(in);
		}
		return out.toArray(new RSItem[out.size()]);
	}

	public static Point getInventoryItemPoint(int slot) {
		Random r = new Random();
		int col = --slot % 4;
		int row = slot / 4;
		int x = 580 + col * 42 - 10 + r.nextInt(6);
		int y = 219 + row * 35 + r.nextInt(6);
		return new Point(x, y);
	}

	public int getCount(int id) {
		int i = 0;
		int invCache[] = getInventoryCache();
		int ai[];
		int k = (ai = invCache).length;
		for (int j = 0; j < k; j++) {
			int x = ai[j];
			if (x != 0 && x - 1 == id)
				i++;
		}

		return i;
	}

	public int getCount() {
		int i = 0;
		int invCache[] = getInventoryCache();
		int ai[];
		int k = (ai = invCache).length;
		for (int j = 0; j < k; j++) {
			int x = ai[j];
			if (x != 0)
				i++;
		}

		return i;
	}

	public boolean isFull() {
		return getCount() == 28;
	}

	public boolean isEmpty() {
		return getCount() == 0;
	}

	public void sleep(int min, int max) {
		methods.sleep(nextInt(min, max));
	}

	public int nextInt(int min, int max) {
		return (int) (Math.random() * (double) (max - min)) + min;
	}
}
