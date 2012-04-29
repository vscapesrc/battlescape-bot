package com.bsbot.launcher;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import scripts.AIOFisher;
import scripts.BankTest;
import scripts.BarbarianAgility;
import scripts.Cooker;
import scripts.Fisher;
import scripts.Script;
import scripts.Thief;

import com.bsbot.api.Inventory;
import com.bsbot.api.Menu;
import com.bsbot.api.Methods;
import com.bsbot.api.Objects;
import com.bsbot.api.TilePath;
import com.bsbot.hooks.Client;
import com.bsbot.hooks.GameInterface;
import com.bsbot.hooks.Npc;
import com.bsbot.wrappers.RSBankItem;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSInterfaceChild;
import com.bsbot.wrappers.RSItem;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSPlayer;
import com.bsbot.wrappers.RSTile;
import com.bsbot.api.Interfaces;

public class BSLoader extends Applet implements AppletStub, ActionListener {
	

	static Loader loader;
	static Client c = null;
	private static Methods m = new Methods();
	static Script runningScript = null;
	static JButton input = new JButton("User input off");
	
	static JMenuBar menuBar = new JMenuBar();
	static JMenu menu = new JMenu("File");
	static JMenuItem start = new JMenuItem("Start script");
	static JMenuItem stop = new JMenuItem("Stop script");
	static JMenuItem quit = new JMenuItem("Quit");
	
	boolean debug = false;
	
	
	
	private static String[] titles = new String[] {
			"BattleScape bot - botting for wild pkers",
			"BattleScape bot - tompa is the bestest",
			"BattleScape bot - items are needed by many",
			"BattleScape bot - please don't teased" };

	public static Script getRunningScript() {
		return runningScript;
	}

	public static Methods getMethods() {
		return m;
	}

	public static Client getClient() {
		return c;
	}

	public static Loader getLoader() {
		return loader;
	}

	public static void setClient(Client gameApplet) {
		c = gameApplet;
	}

	public BSLoader() {
		if(debug){
		loader.addKeyListener(new Keys());
		}
		// c = (Client) loader.gameApplet;
		menu.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
	}

	public static void main(String args[]) {
		
		loader = new Loader();
		loader.setStub(new BSLoader());
		loader.setSize(765 + 23, 503);
		java.util.Random r = new java.util.Random();
		int random = r.nextInt(titles.length);
		JFrame jframe = new JFrame(titles[random]);
		menuBar.add(menu);
		menu.add(start);
		menu.add(stop);
		menu.add(quit);
		menuBar.add(input);
		input.setEnabled(false);
		jframe.setJMenuBar(menuBar);
		jframe.setBounds(40, 40, 773, 531 + 25 + 45);
		JTabbedPane panel = new JTabbedPane();
		panel.setSize(773, 531 + 25);
		jframe.add(panel);
		panel.add(loader);
		panel.setTitleAt(0, "robo");
		// /jframe.getContentPane().add(loader);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(3);
		loader.init();
		// ///loader.start();
	}

	public String getParameter(String s) {
		return (String) a.get(s);
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public URL getCodeBase() {
		try {
			return new URL("http://www.battle-scape.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void appletResize(int i, int j) {
	}

	public AppletContext getAppletContext() {
		return null;
	}

	public boolean isActive() {
		return true;
	}

	private static Hashtable a;

	static {
		a = new Hashtable();
		a.put("nodeid", "1");
		a.put("portoff", "0");
		a.put("lowmem", "0");
		a.put("free", "0");
		a.put("version", "474");
		a.put("worldid", "1");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start script")) {
			Thread s = null;
			if (runningScript != null) {
			}
			if(runningScript == null){
			String a = JOptionPane.showInputDialog(null,
					"What script do you want to run? (fisher, cooker)");

			if (a != null) {
				a = a.toLowerCase();
				if (a.equals("fisher")) {
					runningScript = new AIOFisher();
					s = new Thread(runningScript, "Script");
					s.start();
				} else if (a.equals("agility")) {
					runningScript = new BarbarianAgility();
					s = new Thread(runningScript, "Script");
					s.start();
				} else if (a.equals("thief")) {
					runningScript = new Thief();
					s = new Thread(runningScript, "Script");
					s.start();
				} else if (a.equals("aiofisher")) {
					runningScript = new AIOFisher();
					s = new Thread(runningScript, "Script");
					s.start();
				} else if (a.equals("bankingtest")) {
					runningScript = new BankTest();
					s = new Thread(runningScript, "Script");
					s.start();
				}
				else if (a.equals("cooker")){
					runningScript = new Cooker();
					s = new Thread(runningScript, "Script");
					s.start();
				}
			}
			}
		} else if (e.getActionCommand().equals("Stop script")) {
			runningScript.stop();
			runningScript = null;
		}
	}

	private final BufferedImage gameImage = new BufferedImage(764, 503,
			BufferedImage.TYPE_INT_ARGB);

	public void draw() {
		Graphics buffer = gameImage.getGraphics();
		buffer.drawString("Jello is yellow!", 100, 100);
		buffer.dispose();
		loader.getGraphics().drawImage(gameImage, 0, 0, null);
		/*Graphics buffer = gameImage.getGraphics();
		buffer.drawString("Jello is yellow!", 100, 100);
		buffer.dispose();
		loader.getGraphics().drawImage(gameImage, 0, 0, null);*/
	}

	public class Painter implements Runnable {
		private final BufferedImage gameImage = new BufferedImage(764, 503,
				BufferedImage.TYPE_INT_RGB);

		@Override
		public void run() {
			while (true) {
				Image i;
				Graphics buffer = gameImage.getGraphics();
				buffer.drawString("Jello is yellow!", 100, 100);
				buffer.dispose();
				getClient().getGraphics().drawImage(gameImage, 0, 0, null);
			}
		}
	}

	public class Keys implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int id = e.getKeyCode();
			switch (id) {

			case KeyEvent.VK_1:
			///	RSNPC npc = m.getNearestNpc("rope");
				RSNPC npc = getMethods().getNearestNpc("Fishing spot");
				System.out.println("nearest " + npc.getName() + " at: "
				+ npc.getLocation() + "its id is " + npc.getId());
				break;
			case KeyEvent.VK_2:
				System.out.println(Methods.getMyPlayer().getLocation()
						.toString());
				System.out.println(Methods.getMyPlayer().getCurrentHealth()
						+ "/" + Methods.getMyPlayer().getMaxHealth());
				break;
			case KeyEvent.VK_3:
				System.out.println(Methods.getMyPlayer().getCurrentHealth()
						+ " /" + Methods.getMyPlayer().getMaxHealth());
				double maxHp = Methods.getMyPlayer().getCurrentHealth();
				double currentHp = Methods.getMyPlayer().getMaxHealth();
				double div = currentHp / 100;
				double res = maxHp / div;
				System.out.println(res);
				break;

			case KeyEvent.VK_4:
				Interfaces i = new Interfaces();
				for (RSInterface inv : i.getAllParents()) {
					for (RSInterfaceChild child : inv.getChildren()) {
						if (child != null && child.getText() != null) {
							if (child.getText().contains("here to continue")) {
								System.out.println(child.getText() + " id: "
										+ child.getId());
								child.doClick(true);
							}
						}
					}
				}
				break;

			case KeyEvent.VK_5:
				System.out.println(getClient().getGameComponentHook(0)
						.getMousePosition());
				break;

			case KeyEvent.VK_6:
				for (RSItem item : getMethods().inventory.getItems()) {
					if (item != null && item.getId() != 0) {
						System.out.println("slot: " + item.getSlot()
								+ " name: " + item.getName() + " id: "
								+ item.getId());
					}
				}
				break;

			case KeyEvent.VK_7:
				break;

			case KeyEvent.VK_PLUS:
				GameInterface[][] all = getClient().getInterfaceCache();
				GameInterface[] bank = all[116];
				for (int ii = 0; ii < bank.length; ii++) {
					if (bank[ii] != null && bank[ii].getInv() != null) {
						System.out.println(ii);
					}

				}
				break;

			case KeyEvent.VK_0:
				for (RSBankItem bo : getMethods().banking.getBankItems()) {
					System.out.println("id: " + bo.getId() + bo.getName() + "slot: " + bo.getSlot()
							+ " screen loc:" + bo.getPoint());
				}

				/*
				 * if(child.getActions() != null){ for (String action
				 * :child.getActions()){ if(action != null &&
				 * action.contains("Withdraw")){ System.out.println(action); } }
				 * } if (child != null) { if (child.getT() != null){/////getV()
				 * != null) { t = jotain String ac = child.getT(); if(ac != null
				 * && ac.contains("Wind")){ System.out.println(ac + " id: " +
				 * child.getId() + " x:" + child.getX() + " y: "+ child.getY());
				 * } } } }
				 * 
				 * }
				 */

				break;

			/*
			 * 
			 * 
			 * case KeyEvent.VK_2: Methods m = new Methods(); RSNPC near =
			 * m.getNearestNpc("Man"); if (near != null) {
			 * System.out.println(near.getName() +
			 * near.getLocation().toString()); near.interact("Pickpocket"); }
			 * break;
			 * 
			 * case KeyEvent.VK_4: Methods aa = new Methods(); RSInterface
			 * iface; for (int i = 0; i <
			 * getClient().getInterfaceCache().length; i++) { if
			 * (getClient().getInterfaceCache()[i] != null) { for
			 * (com.bsbot.hooks.RSInterface a : getClient()
			 * .getInterfaceCache()[i]) { iface = new RSInterface(a); if
			 * (a.getType() == 2) { System.out.println(iface.getId() >> 16); }
			 * // /System.out.println(iface.getMessage());
			 * 
			 * } // // System.out.println(iface.getMessage()); } } break;
			 * 
			 * case KeyEvent.VK_5:
			 * if(getClient().getGameComponentHook(0).getMousePosition() !=
			 * null){ System.out.println("mouse coords: " +
			 * getClient().getGameComponentHook(0)
			 * .getMousePosition().toString()); }
			 * 
			 * break; case KeyEvent.VK_6: if (getClient().isMenuOpen()) {
			 * System.out.println("Menu loc:" +
			 * Menu.getMenuLocation().toString()); }
			 * 
			 * System.out.println(""); break;
			 * 
			 * case KeyEvent.VK_7:
			 * 
			 * Inventory.interact(386, "Eat");
			 * 
			 * /* for (com.bsbot.hooks.RSInterface[] inface : getClient()
			 * .getInterfaceCache()) { if (inface != null) { for
			 * (com.bsbot.hooks.RSInterface iface2 : inface) { if
			 * (iface2.getInv() != null) { for (int a = 0; a <
			 * iface2.getInv().length; a++) { if (iface2.getInv()[a] != 0) {
			 * System.out.println("slot: " + (a+1) + " " + getClient().getForId(
			 * iface2.getInv()[a]) .getName() + " item id: " +
			 * iface2.getInv()[a]
			 * 
			 * + " interface id:" + iface2.getId()); } } } } } }
			 */

			/*
			 * for (RSItem i : Inventory.getInventoryCache()) {
			 * System.out.println(i.getName()); }
			 */

			/*
			 * break;
			 * 
			 * case KeyEvent.VK_8: Inventory.getInventoryCache(); break; case
			 * KeyEvent.VK_0: System.out.println(getClient().getBaseX() + " " +
			 * getClient().getBaseY()); for (int i = 0; i <
			 * getClient().getNpcs().length; i++) { Npc a =
			 * getClient().getNpcs()[i]; if (a != null) {
			 * System.out.println(a.getDefinition().getName()); int x =
			 * getClient().getBaseX() + (a.getX() >> 7); int y =
			 * getClient().getBaseY() + (a.getY() >> 7);
			 * System.out.println("coords: (" + x + "," + y + ")"); // //
			 * System.out.println("X: " + a.getSmallX()[0] + //
			 * getClient().getBaseX() + " y: " + a.getSmallY()[0] + //
			 * getClient().getBaseY()); for (String action :
			 * a.getDefinition().getActions()) { System.out.println("action: " +
			 * action); } System.out.println(); } } break;
			 */
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}