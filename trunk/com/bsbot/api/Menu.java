package com.bsbot.api;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bsbot.hooks.Client;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;

public class Menu {

	static Random random = new Random();

	public static Client getClient() {
		return BSLoader.getClient();
	}

	public static boolean isOpen() {
		return getClient().isMenuOpen();
	}

	public static int random(int min, int max) {
		return min + (max == min ? 0 : random.nextInt(max - min));
	}

	public boolean clickIndex(final int i) {
		if (!isOpen()) {
			return false;
		}
		String[] items = getItems();
		if (items.length <= i) {
			return false;
		}
		return atMenuItem(i);
	}

	public Point getLocation() {
		if (isOpen()) {
			return new Point(BSLoader.getClient().getMenuOffsetX(), BSLoader
					.getClient().getMenuOffsetY());
		}
		return null;
	}



	public static boolean interact(String action) {
		action = action.toLowerCase();
		String actions[] = getValidMenuActions();
		for (int i = 0; i < actions.length; i++) {
			actions[i] = actions[i].toLowerCase();

			if (actions[i] != null && actions[i].contains(action)) {
		
				return atMenuItem(i);
			}
		}

		return false;
	}
	
	public static boolean interact(String action, boolean inventory) {
		action = action.toLowerCase();
		String actions[] = getValidMenuActions();
		for (int i = 0; i < actions.length; i++) {
			actions[i] = actions[i].toLowerCase();

			if (actions[i] != null && actions[i].contains(action)) {
			
				
					return atMenuItem(i, inventory);
			}
		}

		return false;
	}

	public static int getMenuActionRow() {
		return BSLoader.getClient().getMenuActionRow();
	}

	public static String[] getValidMenuActions() {
		java.util.List sorted = new ArrayList();
		for (int i = 0; i < getMenuActionRow(); i++)
			sorted.add(BSLoader.getClient().getMenuActionNames()[i]);

		return removeFormatting(reverseStrings((String[]) sorted
				.toArray(new String[sorted.size()])));
	}

	public static String[] reverseStrings(String other[]) {
		String t[] = new String[other.length];
		for (int i = 0; i < t.length; i++)
			if (other[other.length - i - 1] != null)
				t[i] = other[other.length - i - 1];

		return t;
	}

	private static String[] removeFormatting(String unformattedStrings[]) {
		for (int i = 0; i < unformattedStrings.length; i++) {
			String unformattedAction = unformattedStrings[i];
			Pattern p = Pattern.compile("@{1}[a-z|A-Z|0-9]{3}@{1}");
			for (Matcher matcher = p.matcher(unformattedAction); matcher.find();)
				unformattedAction = unformattedAction.replaceAll(
						matcher.group(), "");

			unformattedStrings[i] = unformattedAction;
		}

		return unformattedStrings;
	}

	public static int nextInt(int min, int max) {
		return (int) (Math.random() * (double) (max - min)) + min;
	}

	public static Point getMenuLocation() {

		int y = BSLoader.getClient().getMenuOffsetY();
		int x = BSLoader.getClient().getMenuOffsetX();

		return new Point(x, y);
	}
	
	public static Point[] getMenuPoints(){
		ArrayList<Point> list = new ArrayList<Point>();
		for(int i = 0;i < getValidMenuActions().length;i++){
			Point location = getMenuLocation();
			int xOff = getValidMenuActions()[i].length() * 4 - 5;
			int yOff = (15 * i - 1);//// + nextInt(2, 10);
			Point p = new Point(location.x + xOff, location.y + yOff + 30);
			list.add(p);
		}
		return list.toArray(new Point[list.size()]);
	}

	public static boolean atMenuItem(int i, boolean inventory) {
		if (!isOpen()) {
			return false;
		}
		String actions[] = getValidMenuActions();
		try {
			Point location = getMenuLocation();

			int xOff=0;
			int yOff=0;



			if(inventory){


				xOff = 60;

				 yOff = 15 + (20*i)+10;
			}else{

				xOff = 60;

				 yOff = 15 + (20*i);
			}
			Mouse m = new Mouse();
			Point p = new Point(location.x + xOff, location.y + yOff);
			m.clickMouse(p, true);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean atMenuItem(int i) {
		if (!isOpen()) {
			return false;
		}
		String actions[] = getValidMenuActions();
		try {
			Point location = getMenuLocation();
			int xOff=0;
			int yOff=0;
			if(BSLoader.getMethods().banking.isOpen()){

				xOff = 50;

				yOff = 20 + (20*i);///20*i
			}else{

			xOff = 50;

			yOff = 20 + (15*i);
			}
				 
			Mouse m = new Mouse();
			Point p = new Point(location.x + xOff, location.y + yOff); 
			m.moveMouse(p);
			try{
				Thread.sleep(100);
			}catch(Exception e){
				
			}
			m.clickMouse2(p, true);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] getItems() {
		String[] actions;


		actions = BSLoader.getClient().getMenuActionNames();

		ArrayList<String> output = new ArrayList<String>();

		int len = actions.length;
		for (int i = 0; i < len; i++) {
			String action = actions[i];
			if (action != null) {
				String text = action;
				output.add(text.trim());
			}
		}

		if (output.size() > 1 && output.get(0).equals("Cancel")) {
			Collections.reverse(output);
		}

		return output.toArray(new String[output.size()]);
	}

}
