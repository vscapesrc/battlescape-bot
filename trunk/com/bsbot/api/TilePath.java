package com.bsbot.api;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSTile;

public class TilePath {

	Mouse n = new Mouse();

	public RSTile[] tiles;

	public TilePath(RSTile... theTiles) {
		int len = theTiles.length;
		tiles = new RSTile[len];
		for (int i = 0; i < len; i++) {

			tiles[i] = theTiles[i];
		}
	}
	
	public RSTile getFirst(){
		return tiles[0];
	}
	
	public RSTile getLast(){
		return tiles[tiles.length-1];
	}

	public void walkTileMM(RSTile t) {
		Point p = Calculations.worldToMinimap(t);
		if (p.getX() != -1D && p.getY() != -1D)

			n.clickMouse(p, true);
	}
	
	/**
	 * Reverses the tilepath
	 * @return the reversed tilepath
	 */


	public TilePath reverse(){
        List < RSTile > list = new ArrayList<RSTile>();
        for(RSTile a : tiles){
        	list.add(a);
        }
        Collections.reverse(list);
        
        return new TilePath(list.toArray(new RSTile[list.size()]));
	}

	public RSTile getNext() {
		for (int i = tiles.length - 1; i > -1; i--) {

			if (tiles[i] != null) {
				if (Calculations.tileOnMap(tiles[i])) {
						return tiles[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * Walks the tilepath
	 * @return whenever the walking was succesfull
	 */

	public boolean traverse() {
		RSTile next = getNext();
		if (next != null) {
			walkTileMM(next);
			return true;
		}
		return false;
	}
}
