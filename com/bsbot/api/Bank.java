package com.bsbot.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSBankItem;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSInterfaceChild;
import com.bsbot.wrappers.RSItem;

public class Bank {

	private Methods methods;

	public Bank(Methods m) {
		this.methods = m;
	}

	public void depositAll() {
		if (isOpen()) {
			if (methods.inventory.getCount() > 0) {
				RSItem[] items = methods.inventory.getItems();
				items[0].interact("Store all");
				depositAll();
			}
		}
	}

	/*
	 * public void depositAllExcept(int... ids) { int len = ids.length; int[]
	 * cache = new int[len]; for (int i = 0; i < len; i++) { cache[i] = ids[i];
	 * } if (isOpen()) { for (RSItem i : Inventory.getItems()) { if (i != null)
	 * { for (int a : cache) { if (i.getId() != a) { i.interact("Store All");
	 * methods.sleep(300); } } } } } }
	 */

	/*
	 * public boolean withdraw(final int itemID, final int count) { if
	 * (isOpen()) { if (count < 0) { throw new
	 * IllegalArgumentException("count (" + count + ") < 0"); } RSItem rsi =
	 * methods.inventory.getItem(itemID); if (rsi == null) { return false; }
	 * 
	 * // Check tab /*while (item.getRelativeX() == 0 && getCurrentTab() != 0) {
	 * methods.interfaces.getComponent(INTERFACE_BANK,
	 * INTERFACE_BANK_TAB[0]).doClick(); sleep(random(800, 1300)); }
	 * 
	 * // Scroll to the item if (!methods.interfaces.scrollTo(item,
	 * (INTERFACE_BANK << 16) + INTERFACE_BANK_SCROLLBAR)) { return false; }
	 */

	// /// int invCount = methods.inventory.getCount(true);
	/*
	 * switch (count) { case 0: item.doAction("Withdraw-All"); break; case 1:
	 * item.doClick(true); break; case 5: case 10: item.doAction("Withdraw-" +
	 * count); break; /* default: if (!item.doAction("Withdraw-" + count)) { if
	 * (item.doAction("Withdraw-X")) { sleep(random(1000, 1300));
	 * methods.keyboard.sendText(String.valueOf(count), true); } } }
	 */
	// / sleep(random(1000, 1300));
	// / int newInvCount = methods.inventory.getCount(true);
	// / return newInvCount > invCount || newInvCount == 28;
	// }
	// ///// return false;
	// / }

	public int[] getBankCache() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (com.bsbot.hooks.GameInterface[] inface : BSLoader.getClient()
				.getInterfaceCache()) {
			if (inface != null) {
				for (com.bsbot.hooks.GameInterface iface2 : inface) {
					if (iface2 != null) {

						if (iface2.getId() == 7602279) {
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

	/*
	 * public RSBankItem[] getBankItems(){ int[] cache = getBankCache(); int len
	 * = cache.length; RSBankItem[] returnItem = new RSBankItem[len]; for(int
	 * i=0; i<len; i++){ returnItem[i] = new RSBankItem(cache[i], i); } return
	 * returnItem; }
	 */

	public int getBankCount() {
		return getBankItems().length;
	}

	public int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}

	public void depositAllExcept(String... names) {
		int len = names.length;
		String[] cache = new String[len];
		ArrayList<String> alreadyDeposit = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			cache[i] = names[i].toLowerCase();
		}
		if (isOpen()) {
			RSItem[] items = methods.inventory.getItems();
			for (RSItem a : items) {
				if (a != null && a.getId() != -1 && a.getId() != 0) {
					for (String name : cache) {
						if (a.getName() != null) {
							String itemName = a.getName().toLowerCase();
							if (a != null && !itemName.equals(name)) {
								if (!alreadyDeposit.contains(itemName)) {
									a.interactBank("Store All"); // TODO: make
																	// code
																	// neater,
																	// its very
																	// messy,
																	// sorry :>
									methods.sleep(1000);
									alreadyDeposit.add(itemName);
									items = methods.inventory.getItems();
									for (String namee : cache) {
										for (RSItem aa : items) {
											if (aa.getId() != -1
													&& aa.getName() != null
													&& !aa.getName().equals(
															names)) {
												depositAllExcept(names);
												return;
											}
										}
									}
									return;
								}
							}
						}
					}
				}
			}
		}
	}

	/*
	 * public boolean depositAllExcept(int... items) { if (isOpen()) { boolean
	 * deposit = true; int invCount = isOpen() ? methods.inventory.getCount() :
	 * 0; outer: for (int i = 0; i < 28; i++) { RSComponent item = isOpen() ?
	 * methods.inventory.getItemAt(i) .getComponent() :
	 * methods.interfaces.get(11) .getComponent( 17).getComponent( i); if (item
	 * != null && item.getComponentID() != -1) { for (int id : items) { if
	 * (item.getComponentID() == id) { continue outer; } } for (int tries = 0;
	 * tries < 5; tries++) { deposit(item.getComponentID(), 0);
	 * sleep(random(600, 900)); int cInvCount = isOpen() ? methods.inventory
	 * .getCount(true) : getBoxCount(); if (cInvCount < invCount) { invCount =
	 * cInvCount; continue outer; } } deposit = false; } } return deposit; }
	 * return false; }
	 */
	public boolean isOpen() {
		return methods.interfaces.isInterfaceOpen(114);
	}

	public RSBankItem getItem(String name) {
		name = name.toLowerCase();
		for (RSBankItem item : getBankItems()) {
			if (item.getName().toLowerCase().equals(name)) {
				System.out.println(item.getName() + " " + name);
				return item;
			}
		}
		return null;
	}

	public RSBankItem[] getBankItems() {
		ArrayList<RSBankItem> list = new ArrayList<RSBankItem>();
		RSInterfaceChild iface = new RSInterfaceChild(BSLoader.getClient()
				.getInterfaceCache()[116], BSLoader.getClient()
				.getInterfaceCache()[116][103], 116);
		int[] inv = iface.getAccessor().getInv();
		for (int slot = 0; slot < inv.length - 1; slot++) {
			RSBankItem item = new RSBankItem(inv[slot], slot, iface);
			if (item != null && item.getName() != null && item.getName() != "null") {
				list.add(new RSBankItem(inv[slot], slot, iface));
			}
		}

		return list.toArray(new RSBankItem[list.size()]);
	}
}
