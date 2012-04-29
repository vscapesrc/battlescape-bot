package com.bsbot.wrappers;

import java.awt.Point;
import java.util.Random;

import com.bsbot.api.Inventory;
import com.bsbot.api.Menu;
import com.bsbot.hooks.ItemDef;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;

public class RSBankItem {
	int id;
	int slot;
	private ItemDef itemDef;
	public RSInterfaceChild child;
	Random r = new Random();
	
	
	public RSBankItem(int id, int slot, RSInterfaceChild component){

		id--;
		this.id = id;
		this.slot = slot;
		if(id > -1){
		this.itemDef = BSLoader.getClient().getForId(id);
		}
		this.child = component;
	}
	
	private int getScreenX(){
		return ((child.getScreenX()+child.getAccessor().getXOffset()) + slot+1 * (32 + child.getAccessor().getInvSpritePadX()));
	}
	
	private int getScreenY(){
		System.out.println(child.getAccessor().getInvSpritePadX());
		System.out.println(child.getAccessor().getInvSpritePadY());
		return ((child.getScreenY()+child.getAccessor().getYOffset()) + slot+1 * (32 + child.getAccessor().getInvSpritePadY()));
	}
	
	public Point getScreenLocation(){
		return new Point(getScreenX() + r.nextInt(8), getScreenY()+r.nextInt(8));
	}
	
	public int getId(){
		return id;
	}
	
	
	public String getName() {
		if(itemDef != null && itemDef.getName() != null){
		return itemDef.getName();
		}
		return "null";
	}
	public int getSlot(){
		return slot;
	}
	
	public void interact(String action) {
		Mouse n = new Mouse();
		if (Menu.isOpen()) {
			n.moveMouse(new Point(10, 10));
		}
		/*
		 * if (BSLoader.getClient().getOpenTab() != 3){
		 * 
		 * n.mouse(new Point(nextInt(627, 654), nextInt(169, 196)),
		 * n.LEFT_BUTTON); }
		 */
		// /////System.out.println("clicking mouse");
		try{
		Point p = getPoint();
		n.moveMouse(p);
		Thread.sleep(100);
		// n.mouse(p, n.LEFT_BUTTON);
		n.clickMouse(p, false);
		// }// //(getInventoryItemPoint(slot),

			Thread.sleep(1000);
		}catch(Exception e){
			
		}
		// //System.out.println("in menu.interact()");
		Menu.interact(action, false);
	}
	
	public Point getPoint() {
		int theSlot = slot;
		Random r = new Random();
		int col = theSlot-- % 8;///--theSlot % 8;
		int row = theSlot / 8;
		int x = 73 + col * 49;/// - 10 + r.nextInt(6);
		int y = 63 + (row * 39) + r.nextInt(6);// + 40;
		///////System.out.println("y: " +y + " row*38 " + (row*38));
		return new Point(x+10, y);
	}

}
