package com.bsbot.wrappers;

import java.util.ArrayList;

import com.bsbot.hooks.GameInterface;
import com.bsbot.launcher.BSLoader;

/**
 * Represents an interface. Each interface consists of a group of components.
 * 
 * @author Qauters
 */
public class RSInterface {

	/**
	 * Cache of this interface's children.
	 */


	/**
	 * The index of this interface.
	 */
	private int index;

	private com.bsbot.hooks.GameInterface[] ifaceHook;

	public com.bsbot.hooks.GameInterface[] getAccessor() {
		return ifaceHook;
	}

	public RSInterface(final int iface) {
		index = iface;
		ifaceHook = BSLoader.getClient().getInterfaceCache()[iface];
	}

	public RSInterface(com.bsbot.hooks.GameInterface[] iface, int id) {
		index = id;
		ifaceHook = iface;
	}
	

	/*
	 * public String[] getActions(){ return ifaceHook.getActions(); }
	 * 
	 * 
	 * public String getMessage(){ return ifaceHook.getText(); }
	 */

	public GameInterface[] getParentInterface() {
		return ifaceHook;
	}

	public RSInterfaceChild[] getChildren() {
		ArrayList<RSInterfaceChild> child = new ArrayList<RSInterfaceChild>();
		for (GameInterface i : ifaceHook) {
			child.add(new RSInterfaceChild(ifaceHook, i, index));
		}
		return child.toArray(new RSInterfaceChild[child.size()]);
	}

	public int getId() {
		return index;
	}

	/**
	 * @inheritDoc java/lang/Object#equals(java/lang/Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof RSInterface) {
			final RSInterface inter = (RSInterface) obj;
			return inter.index == index;
		}
		return false;
	}

	/**
	 * @inheritDoc java/lang/Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return index;
	}

}
