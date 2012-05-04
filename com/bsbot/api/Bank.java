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
	
	/**
	 * Deposits all items from the inventory
	 */

	public void depositAll() {
		if (isOpen()) {
			if (methods.inventory.getCount() > 0) {
				RSItem[] items = methods.inventory.getItems();
				items[0].interact("Store all");
				depositAll();
			}
		}
	}

	/**
	 *
	 * @return The bank cache, used by internal operations
	 */

	private int[] getBankCache() {
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

	/**
	 * 
	 * @return
	 */
	public int getBankCount() {
		return getBankItems().length;
	}

	private int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}
	
	/**
	 * Deposits all items to the bank except ones given
	 * @param names names of items not to be deposited
	 */

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


	/**
	 * 
	 * @return whether the bank is open
	 */
	
	public boolean isOpen() {
		return methods.interfaces.isInterfaceOpen(114);
	}
	
	/**
	 * 
	 * @param name name of the bank item. NOTE bank items are not fully working yet, it has to be visible.
	 * @return the bank item specified, if not found returns null.
	 */

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
	
	/**
	 * 
	 * @return All the bank items in bank.
	 */

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
