package com.bsbot.launcher;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import scripts.AIOFisher;
import scripts.BankTest;
import scripts.BarbarianAgility;
import scripts.Cooker;
import scripts.Script;
import scripts.Thief;

import com.bsbot.api.Methods;
import com.bsbot.hooks.Client;
import com.bsbot.hooks.GameInterface;
import com.bsbot.wrappers.RSBankItem;
import com.bsbot.wrappers.RSGroundItem;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSInterfaceChild;
import com.bsbot.api.Interfaces;

public class BSLoader extends Applet implements AppletStub, ActionListener {

	/**
	 * 
	 */
	KeyListener k = new Keys();
	private static final long serialVersionUID = 1L;
	static Loader loader;
	static Client c = null;
	private static Methods m = new Methods();
	static Script runningScript = null;
	static JButton input = new JButton("User input off");

	static JMenuBar menuBar = new JMenuBar();
	static JMenu menu = new JMenu("File");
	static JMenuItem start = new JMenuItem("Start script");
	static JMenuItem stop = new JMenuItem("Stop script");
	static JMenuItem debugItem = new JMenuItem("Debug");
	static JMenuItem quit = new JMenuItem("Quit");

	boolean debug = false; /// set to true if you want to see stuff like players, npcs, interfaces etc with number keys

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
		if (debug) {
			loader.addKeyListener(new Keys());
		}
		// c = (Client) loader.gameApplet;
		menu.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		debugItem.addActionListener(this);
	}

	public static void main(String args[]) {


		java.util.Random r = new java.util.Random();
		int random = r.nextInt(titles.length);
		JFrame jframe = new JFrame(titles[random]);
		menuBar.add(menu);
		menu.add(start);
		menu.add(stop);
		menu.add(debugItem);
		menu.add(quit);
		menuBar.add(input);
		input.setEnabled(false);
		loader = new Loader();
		loader.setStub(new BSLoader());
		loader.setSize(765 + 23, 503);
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

	private static Hashtable<String, String> a;

	static {
		a = new Hashtable<String, String>();
		a.put("nodeid", "1");
		a.put("portoff", "0");
		a.put("lowmem", "0");
		a.put("free", "0");
		a.put("version", "474");
		a.put("worldid", "1");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("Debug")){
			System.out.println("debug is here");
			if(!debug){
				debug = true;
				loader.addKeyListener(k);
			}else{
				System.out.println(debug);
				loader.removeKeyListener(k);
			}
		}
		if (e.getActionCommand().equals("Start script")) {
			Thread s = null;
			if (runningScript != null) {
			}
			if (runningScript == null) {
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
					/*}else if(a.equalsIgnoreCase("fighter")){ private script, make your own if you want 
						runningScript = new AIOFighter();
						s = new Thread(runningScript, "Script");
						s.start();*/
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
					} else if (a.equals("cooker")) {
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





	public class Keys implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int id = e.getKeyCode();
			switch (id) {

			case KeyEvent.VK_6:
					for (RSGroundItem gi : getMethods().grounditems.getAll()) {
						if (gi != null) {
							System.out.println("groundItem: " + gi.getName()
									+ " at: " + gi.getLocation());
						}
					}
				break;
			case KeyEvent.VK_2:
				System.out.println(getClient().getPlane());
				System.out.println(Methods.getMyPlayer().getLocation()
						.toString());
				System.out.println(Methods.getMyPlayer().getCurrentHealth()
						+ "/" + Methods.getMyPlayer().getMaxHealth());
				break;


			case KeyEvent.VK_4:
				Interfaces i = new Interfaces();
				for (RSInterface inv : i.getAllParents()) {
					for (RSInterfaceChild child : inv.getChildren()) {
						if (child != null && child.getText() != null) {
								System.out.println(child.getText() + " id: "
										+ child.getId());
						}
					}
				}
				break;

			case KeyEvent.VK_5:
				System.out.println(getClient().getGameComponentHook(0)
						.getMousePosition());
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
					System.out.println("id: " + bo.getId() + bo.getName()
							+ "slot: " + bo.getSlot() + " screen loc:"
							+ bo.getPoint());
				}



				break;


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