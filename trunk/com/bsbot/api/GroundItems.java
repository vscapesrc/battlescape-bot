package com.bsbot.api;



// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroundItems.java


import java.util.ArrayList;
import java.util.List;

import com.bsbot.hooks.GroundItem;
import com.bsbot.hooks.Node;
import com.bsbot.hooks.NodeList;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSGroundItem;
import com.bsbot.wrappers.RSTile;



// Referenced classes of package net.xotiksys.script.methods:
//            Players, Calculations

public class GroundItems
{

    public GroundItems()
    {
    }

    public RSGroundItem getNearest(int id)
    {
        RSGroundItem returnItem = null;
        int maxDist = 104;
        for(int x = 0; x < 104; x++)
        {
            for(int y = 0; y < 104; y++)
            {
                RSGroundItem items[] = getGroundItemsAt(x, y);
                RSGroundItem agrounditem[];
                int j = (agrounditem = items).length;
                for(int i = 0; i < j; i++)
                {
                    RSGroundItem item = agrounditem[i];
                    System.out.println(item.getId());
                    if(item.getId() == id && Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), item.getLocation()) < (double)maxDist)
                    {
                        maxDist = (int)Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), item.getLocation());
                        returnItem = item;
                    }
                }

            }

        }

        return returnItem;
    }
    
    public RSGroundItem getNearest(String name){
    	RSGroundItem returner = null;
        int maxDist = 104;
    	for(RSGroundItem it : getAll()){
    		if(it.getName().toLowerCase().equals(name.toLowerCase()) && Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), it.getLocation()) < (double)maxDist)
            {
                maxDist = (int)Calculations.distanceBetween(Methods.getMyPlayer().getLocation(), it.getLocation());
                returner = it;
            }
    			
    		}
    	return returner;
    	}


    
    
    public RSGroundItem[] getAll()
    {
		ArrayList<RSGroundItem> temp = new ArrayList<RSGroundItem>();
        RSGroundItem returnItem = null;
        int maxDist = 104;
        for(int x = 0; x < 104; x++)
        {
            for(int y = 0; y < 104; y++)
            {
                RSGroundItem items[] = getGroundItemsAt(x, y);
                RSGroundItem agrounditem[];
                int j = (agrounditem = items).length;
                for(RSGroundItem gi : items){
                	if(gi != null){
					temp.add(gi);
                	}
                }

            }

        }

		return temp.toArray(new RSGroundItem[temp.size()]);
    }
    
   /* public static RSGroundItem[] getAll(){
    	return getAll(52);
    }*/
    
	/*public static RSGroundItem[] getAll(int range) {
		ArrayList<RSGroundItem> temp = new ArrayList<RSGroundItem>();
		/*int pX = Methods.getMyPlayer().getX();
		int pY = Methods.getMyPlayer().getY();
		int minX = 104 - range, minY = 104 - range;
		int maxX = 104 + range, maxY = 104 + range;
		System.out.println(104+" "+104+" "+minX+" "+maxX);
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				RSGroundItem[] items = getGroundItemsAt(x, y);
				for (RSGroundItem item : items) {
						temp.add(item);
				}
			}
		}
		return temp.toArray(new RSGroundItem[temp.size()]);
	}*/
    public RSGroundItem[] getGroundItemsAt(int x, int y)
    {
        NodeList nl = BSLoader.getClient().getGroundArray()[BSLoader.getClient().getPlane()][x][y];
        if(nl == null)
            return new RSGroundItem[0];
        ArrayList<RSGroundItem> list = new ArrayList<RSGroundItem>();
        Node holder = nl.getFirstHook();
        if(holder instanceof GroundItem)
            list.add(new RSGroundItem(((GroundItem)holder).getId(), new RSTile(x + BSLoader.getClient().getBaseX(), y + BSLoader.getClient().getBaseY())));
        for(Node curNode = nl.getNextHook(); curNode != null && curNode != holder; curNode = nl.getNextHook())
            if(curNode instanceof GroundItem)
            {
                GroundItem item = (GroundItem)curNode;
                list.add(new RSGroundItem(item.getId(), new RSTile(x + BSLoader.getClient().getBaseX(), y + BSLoader.getClient().getBaseY())));
            }

        return (RSGroundItem[])list.toArray(new RSGroundItem[list.size()]);
    }
}