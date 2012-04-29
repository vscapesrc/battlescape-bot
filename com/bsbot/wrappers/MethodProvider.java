package com.bsbot.wrappers;

import com.bsbot.api.Inventory;
import com.bsbot.api.Methods;

public class MethodProvider {
	
	Methods m;
	
	Inventory inventory;
	
	public MethodProvider(Methods a){
		this.m = a;
		this.inventory = m.inventory;
	}
	
	public MethodProvider(){
		
	}

}
