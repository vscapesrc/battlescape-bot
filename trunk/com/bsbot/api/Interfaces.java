package com.bsbot.api;

import java.util.ArrayList;
import java.util.List;

import com.bsbot.hooks.GameInterface;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSItem;

public class Interfaces {
	private Methods methods;
	
	public Interfaces(Methods m){
		this.methods = m;
	}
	
	public Interfaces(){
		
	}
	
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
