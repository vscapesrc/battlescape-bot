package com.bsbot.wrappers;

import com.bsbot.hooks.Entity;
import com.bsbot.hooks.Npc;
import com.bsbot.launcher.BSLoader;



public class RSNPC extends RSEntity {
	
	private Npc lol;
	private int id;
	
	public RSNPC(Npc a, int id){
		this.lol = a;
		this.id = id;
	}
	
	public RSNPC(int id){
		this.id = id;
		this.lol = BSLoader.getClient().getNpcs()[id];
	}
	
	public int getId(){
		return id;
	}
	
	public int getLevel(){
		return -1;
	}
	
	
	public String getName(){
		return lol.getDefinition().getName();
	}

	@Override
	protected Entity getAccessor() {
		return lol;
	}
	

}
