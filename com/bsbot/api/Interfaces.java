package com.bsbot.api;

import java.util.ArrayList;
import java.util.List;

import com.bsbot.hooks.GameInterface;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSInterfaceChild;
import com.bsbot.wrappers.RSItem;

public class Interfaces {
	
	/**
	 * @author Kubko
	 * NOTE: INTERFACES ARE NOT WORKING 100% YET; WILL BE LOOKED INTO IN THE FUTURE
	 */
	
	private Methods methods;
	
	public Interfaces(Methods m){
		this.methods = m;
	}
	
	
	public synchronized RSInterface get(final int index) {
		RSInterface inter;
		final int cacheLen = getAllParents().length;
		if (index < cacheLen) {
			inter = getAllParents()[index];
			if (inter == null) {
				inter = new RSInterface(index);
			}
			return inter;
		}
		return null;
	}
	
	public Interfaces(){
		
	}
	/**
	 * 
	 * @return the list of all interface parents
	 */
	public RSInterface[] getAllParents(){
		GameInterface[][] cache = BSLoader.getClient().getInterfaceCache();
		List<RSInterface> list = new ArrayList<RSInterface>();
		for(int i = 0; i < cache.length; i++){
			GameInterface[] parent = cache[i];
			if(parent != null){
				list.add(new RSInterface(parent, i));
			}
		}
		
		return list.toArray(new RSInterface[list.size()]);
	}
	
	/**
	 * 
	 * @param id of the interface
	 * @return whether the interface with the given id is open
	 */
	
	public boolean isInterfaceOpen(int id){
		RSInterface[] cache = getAllParents();
		for(RSInterface loop : cache){
			if( loop !=null && loop.getId() == id){
				return true;
			}
		}
		return false;
	}

}
