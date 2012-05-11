package scripts;


import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSTile;
import com.bsbot.api.Calculations;
import com.bsbot.api.Objects;
import com.bsbot.api.TilePath;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import scripts.Script;
import scripts.ScriptManifest;

@ScriptManifest(authors = { "Anfernio" }, name = "wc", version = 1, description = "Does BS slave work for free")
public class AIOWoodcutter extends Script {

 
	static TilePath toRegulars = new TilePath(new RSTile(3253, 3420), new RSTile(3252, 3428), new RSTile(3242, 3429), new RSTile(3231, 3429), new RSTile(3224, 3426));
        static TilePath regularsToBank = toRegulars.reverse();
        
        String state;
        private long startTime;
        int startLevel, startExp;
        String treeType;
        boolean guiOn = true;
        GUI a;
        boolean canPaint;
        String treeChoice;
        Woodcut weCut;
        String chop;
        String wcOption;
        boolean hidePaint = true;
    	boolean b;
        Random r = new Random();
        
        enum Woodcut {
        	REGULAR(1393, "Axe", "Chop down", 500, regularsToBank, toRegulars);
        	
        	int id;
            String action;
            String wcItemName;
            int exp;
            
            TilePath toBank;
            TilePath toCut;

			private Woodcut(int objectId, String itemName, String wcAction,
                    int expCount, TilePath toBank, TilePath toCut) {
            this.exp = expCount;
            this.id = objectId;
            this.action = wcAction;
            this.wcItemName = itemName;
            this.toBank = toBank;
            this.toCut = toCut;
			}
			
            public int exp() {
                return exp;
            }
            
            public TilePath getToBank() {
                return toBank;
            }

            public String getItemName() {
                return wcItemName;
            }
            
            public int getId() {
                return id;
            }

            public String getAction() {
                return action;
            }
            
            public TilePath getToWork() {
                return toRegulars;
            }

		};
        
        
        private static final Font font = new Font("Arial", 0, 12);
        @Override
        public int loop() {
            RSObject tree = Objects.getNearest(1276);
            if (!inventory.isFull()) {
                    if (tree != null && Calculations.distanceTo(weCut.toCut.getLast()) < 10){
                    	if(tree.isOnScreen() && getMyPlayer().getAnimation() == -1) {
                    
                            state = "chopping";
                                    tree.interact(weCut.getAction());
                                    sleep(1000);
                                    if(getMyPlayer().getAnimation() == -1 && Calculations.distanceTo(tree.getLocation()) < 2){
                                    	int x = r.nextInt(4);
                                    	int y = r.nextInt(x);
                                    	int a = r.nextInt(2);
                                    	TilePath toThere = null;
                                    	if(a < 1){
                                        	x+=tree.getLocation().getX();
                                        	y+=tree.getLocation().getY();
                                        	toThere = new TilePath(new RSTile(x, y));
                                    	}else{
                                    		toThere = new TilePath(new RSTile(tree.getLocation().getX()-x, tree.getLocation().getY()-y));
                                        	tree.getLocation().getX();
                                        	y+=tree.getLocation().getY();
                                    	}
                                    	toThere.traverse();
                                        return 3000;
                                    }

                                    return 0;

                    	}else if(!tree.isOnScreen()){
                    		camera.turnTo(tree);
                    		return 600;
                    	}
                    } else {
                            state = "travelling";
                            weCut.getToWork().traverse();
                            return 300;
                    }
            } else {
                    if (banking.isOpen() && inventory.getCount() > 1) {
                            state = "banking";
                            banking.depositAllExcept(weCut.getItemName());
                            return 600;
                    }
                    RSObject bank = Objects.getNearest("Bank booth");
                    if (bank != null && !banking.isOpen() && Calculations.distanceTo(bank.getLocation()) < 10) {
                            if (bank.isOnScreen()) {
                                    if (!banking.isOpen()) {
                                            state = "openining bank";
                                            bank.interact("Quick");
                                    }
                                    return 600;
                            } else {
                                    state = "Turning camera to bank";
                                    camera.turnTo(bank);
                                    return 600;
                            }
                    } else {
                            state = "walking to bank";
                            weCut.getToBank().traverse();
                            return 1500;
                    }
            }
            return 10;
    }
        @Override
        public void onBegin() {
        	guiOn = true;
        	canPaint = false;
            a = new GUI();
            a.setVisible(true);
            while (guiOn == true) {
                    sleep(500);
            }
            if (treeChoice != null) {
                if (treeChoice.equals("Regulars")) {
                        weCut = Woodcut.REGULAR;
                }
                chop = weCut.toString();
            }
            System.out.println(weCut.toString());
            startLevel = skills.getLevel(skills.WOODCUTTING);
            startTime = System.currentTimeMillis();
            startExp = skills.getExperience(skills.WOODCUTTING);
        	state = "starting";
        }
        @Override
		public void onFinish() {
		}
        @Override
        public void paint(Graphics g) {
        	if (!canPaint && hidePaint) {
        		long millis = System.currentTimeMillis() - startTime;
        		long hours = millis / (1000 * 60 * 60);
        		millis -= hours * 1000 * 60 * 60;
            	long minutes = millis / (1000 * 60);
            	millis -= minutes * 1000 * 60;
            	long seconds = millis / 1000;
            	int curLevel = skills.getLevel(skills.WOODCUTTING);
            	int expGained = skills.getExperience(skills.WOODCUTTING) - startExp;
            	g.setFont(font);
            	g.setColor(Color.BLACK);
            	g.fillRect(3, 344, 503, 112);
            	g.setColor(Color.GREEN);
            	g.drawString("Time: " + hours + ":" + minutes + ":"+ seconds, 8, 370);
            	g.drawString("Woodcutting level: " + curLevel, 8, 400);
            	g.drawString("Levels achieved: " + (curLevel - startLevel), 8, 420);
				g.drawString("We are chopping: " + treeType, 8, 440);
				g.drawString("Experience: " + expGained, 150, 370);
				g.drawString("We are currently " + state + ".", 150, 440);
        	}
        }
        @SuppressWarnings("serial")
		public class GUI extends JFrame {
			public GUI() {
                    initComponents();
            }

			@SuppressWarnings({ "unchecked", "rawtypes" })
			private void initComponents() {
                // JFormDesigner - Component initialization - DO NOT MODIFY
                // //GEN-BEGIN:initComponents
                // Generated using JFormDesigner Evaluation license - Anfernio
                label1 = new JLabel();
                comboBox1 = new JComboBox();
                label2 = new JLabel();
                comboBox2 = new JComboBox();
                label3 = new JLabel();
                button1 = new JButton();
                label4 = new JLabel();
                label5 = new JLabel();
                
                // ======== this ========
                setTitle("AIO Woodcutter GUI");
                Container contentPane = getContentPane();
                contentPane.setLayout(null);
                
                // ---- comboBox1 ----
                comboBox1.setModel(new DefaultComboBoxModel(new String[] {
                                "Regulars", "Willows", "Yews" }));
                contentPane.add(comboBox1);
                comboBox1.setBounds(5, 75, 205, 25);
                
                // ---- comboBox2 ----
                comboBox2.setModel(new DefaultComboBoxModel(new String[] { "Drop","Bank" }));
                contentPane.add(comboBox2);
                comboBox2.setBounds(new Rectangle(new Point(260, 70), comboBox2
                                .getPreferredSize()));
                
                // ---- label1 ----
                label1.setText("What to chop?");
                contentPane.add(label1);
                label1.setBounds(5, 50, 135, 20);
              
                // ---- label2 ----
                label2.setText("Drop/Bank?");
                contentPane.add(label2);
                label2.setBounds(260, 45, 105, 20);

                // ---- label3 ----
                label3.setText("Anfernio's BS Woodcutter");
                label3.setFont(label3.getFont().deriveFont(
                                label3.getFont().getSize() + 10f));
                contentPane.add(label3);
                label3.setBounds(15, 0, 270, 35);

                // ---- button1 ----
                button1.setText("Start");
                button1.setFont(button1.getFont().deriveFont(
                                button1.getFont().getSize() + 6f));
                contentPane.add(button1);
                button1.setBounds(30, 185, 290, 55);

                // ---- label4 ----
                label4.setText("Requirements:");
                contentPane.add(label4);
                label4.setBounds(110, 100, 150, 25);

                // ---- label5 ----
                label5.setText("Axe (wielded before starting script)");
                contentPane.add(label5);
                label5.setBounds(110, 120, 200, 25);
                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < contentPane.getComponentCount(); i++) {
                            Rectangle bounds = contentPane.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width,
                                            preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height,
                                            preferredSize.height);
                    }
                    Insets insets = contentPane.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPane.setMinimumSize(preferredSize);
                    contentPane.setPreferredSize(preferredSize);
            }
            button1.addActionListener(new ActionListener() {
            	
            		@Override
                    public void actionPerformed(ActionEvent e) {
                          //  String action = e.getActionCommand();
                            treeChoice = (String)comboBox1.getSelectedItem();
                            System.out.println("treeChoice: " + treeChoice);
                            //wcOption = (String) toCut;
                            guiOn = false;
                            doDispose();

                    }

            });
            comboBox1.addItemListener(new ItemListener() {

                    public void itemStateChanged(ItemEvent e) {
                            if (e.getItem().toString().equals("Regulars")) {
                            System.out.println("Switched to Regulars");
                            label5.setText("An axe (wielded)");
                    } else if (e.getItem().toString().equals("Willows")) {
                    		System.out.println("Switched to Willows");
                            label5.setText("An axe (wielded)");
                    } else if (e.getItem().toString().equals("Yews")) {
                            label5.setText("An axe (wielded)");
                            System.out.println("Switched to Yews");
                    }                                       
                    }


            });
            pack();
            setLocationRelativeTo(getOwner());
			}
            // JFormDesigner - End of component initialization
            // //GEN-END:initComponents
            public void doDispose() {
                setVisible(false);
                super.dispose();
                guiOn = false;
            }
            // JFormDesigner - Variables declaration - DO NOT MODIFY
            // //GEN-BEGIN:variables
            // Generated using JFormDesigner Evaluation license - Wild Kubko
            private JLabel label1;
            @SuppressWarnings("rawtypes")
			private JComboBox comboBox1;
            private JLabel label2;
			@SuppressWarnings("rawtypes")
			private JComboBox comboBox2;
            private JLabel label3;
            private JButton button1;
            private JLabel label4;
            private JLabel label5;
            // JFormDesigner - End of variables declaration //GEN-END:variables
        }
}