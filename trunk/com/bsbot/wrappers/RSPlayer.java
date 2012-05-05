package com.bsbot.wrappers;

import com.bsbot.api.Methods;
import com.bsbot.hooks.Client;
import com.bsbot.hooks.Entity;
import com.bsbot.hooks.Player;
import com.bsbot.launcher.BSLoader;


public class RSPlayer extends RSEntity {
	Client c = BSLoader.getClient();
	
	Player pl;
	
	public RSPlayer(Player p){
		this.pl = p;
	}

	@Override
	protected Entity getAccessor() {
		return pl;
	}
	
	public String getName(){
		if(pl != null && pl.getPlayerName() != null){
		return pl.getPlayerName();
		}
		return null;
	}
	
	
	@Override
	public int getCurrentHealth(){

		if(pl.equals(c.getMyPlayer())){
			return c.getCurrentStats()[3];
		}
		return super.getCurrentHealth();
		
	}
	
	@Override
	public int getMaxHealth(){
		Client c = BSLoader.getClient();
		if(pl.equals(c.getMyPlayer())){
			return c.getMaxStats()[3];
		}
		return super.getMaxHealth();
		
	}
	
	public double getHpPercent(){
		if(pl.equals(BSLoader.getClient().getMyPlayer()) || this.equals(Methods.getMyPlayer())){
			Client c = BSLoader.getClient();
			double maxHp = Methods.getMyPlayer().getCurrentHealth();
			double currentHp =  Methods.getMyPlayer().getMaxHealth();
			double div = currentHp / 100;
			double res = maxHp / div;
			return res;
			}else{
		System.out.println(pl.getCurrentHealth() + "/" + pl.getMaxHealth());
		return pl.getMaxHealth() / 100 * pl.getCurrentHealth();
	}
	}
	@Override
	public RSTile getLocation() {
		Entity c = getAccessor();
		if (c == null) {
			return new RSTile(-1, -1);
		}
		Client cl = BSLoader.getClient();
		int x = cl.getBaseX() + (c.getX() >> 7);
		int y = cl.getBaseY() + (c.getY() >> 7);
		return new RSTile(x, y);
	}
	
	


}
