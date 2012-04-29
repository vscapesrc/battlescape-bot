package com.bsbot.hooks;

public interface GameInterface {
	
	   public int[] getInv();
	   public int[] getInvStackSizes();
	   public GameInterface[] getChildren();
	   public String getText();
	   public int getId();
	   public String[] getActions();
	   public int getType();
	   
	   public int getParentId();
	   
	   public int getXOffset(); //basically x location
	   public int getYOffset(); //basically y location
	   public int getWidth();
	   public int getHeight();
	   public int getX();
	   public int getY();
	   public int getMediaType();
	   public int getCurrentMediaType();
	   
	   public int getMediaId();
	   
	   public int getInvSpritePadX();
	   public int getInvSpritePadY();
	   
	   
	   
	   String getInt();
	   String getDo();
	   String getAd();
	   String getT();
	   String getV();
	

}
