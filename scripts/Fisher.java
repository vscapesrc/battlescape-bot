package scripts;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.bsbot.api.Calculations;
import com.bsbot.api.Inventory;
import com.bsbot.api.Objects;
import com.bsbot.api.TilePath;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSTile;

public class Fisher extends Script {

	public String state;
	
	TilePath toLobsters = new TilePath(new RSTile(2588, 3420), new RSTile(2594, 3415), new RSTile(2598, 3409), new RSTile(2611, 3411));

	TilePath lobstersToBank = new TilePath( new RSTile(2604, 3412), new RSTile(2597, 3410), new RSTile(2590, 3418));

	TilePath toManta = new TilePath(new RSTile(2587, 3419), new RSTile(2595, 3420), new RSTile(2603, 3425));
	
	TilePath mantaToBank = new TilePath(new RSTile(2603, 3425), new RSTile(2595, 3420), new RSTile(2589, 3418));
	
	enum Fish{MANTA(196, "Harpoon"), LOBSTER(191, "Cage");
		
		
		int id;
		String action;
		private Fish(int npcId, String fishAction){
			this.id=npcId;
			this.action = fishAction;
		}
	};
	
	@Override
	public int loop() {
		RSNPC fish = getNearestNpc(196);
		if (!inventory.isFull()) {
			if (fish != null && fish.isOnScreen()) {
				state = "Fishing";
				if (getMyPlayer().getInteracting() == null || getMyPlayer().getAnimation() == -1) {
					fish.interact("Harpoon");
					return 1000;

				}
			} else {
				
				toManta.traverse();
				return 300;
			}
		} else {
			if (banking.isOpen() && inventory.getCount() > 1) {
				state = "Banking";
				banking.depositAllExcept("Harpoon");
			}
			RSObject bank = Objects.getNearest(2213);
			if (bank != null && !banking.isOpen() && bank.isOnScreen() && Calculations.distanceTo(bank.getLocation()) < 8) {
				state = "Open bank";
				bank.interact("Quick");
				return 300;
			} else {
				
				mantaToBank.traverse();
				return 300;
			}
		}
		return 10;
	}

	@Override
	public void onBegin() {
	}

	@Override
	public void onFinish() {
	}
	



	/**
	 * @author Wild Kubko
	 */
	public class GUI extends JFrame {
		public GUI() {
			initComponents();
		}

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			// Generated using JFormDesigner Evaluation license - Wild Kubko
			label1 = new JLabel();
			comboBox1 = new JComboBox();
			label2 = new JLabel();
			comboBox2 = new JComboBox();
			button1 = new JButton();

			//======== this ========
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			//---- label1 ----
			label1.setText("What to fish");
			contentPane.add(label1);
			label1.setBounds(5, 35, 135, 20);

			//---- comboBox1 ----
			comboBox1.setModel(new DefaultComboBoxModel(new String[] {
				"Shrimp",
				"Lobster",
				"Manta ray"
			}));
			contentPane.add(comboBox1);
			comboBox1.setBounds(0, 55, 65, 25);

			//---- label2 ----
			label2.setText("What 2 do with fish");
			contentPane.add(label2);
			label2.setBounds(260, 45, 105, 20);

			//---- comboBox2 ----
			comboBox2.setModel(new DefaultComboBoxModel(new String[] {
				"Drop",
				"Bank"
			}));
			contentPane.add(comboBox2);
			comboBox2.setBounds(new Rectangle(new Point(260, 70), comboBox2.getPreferredSize()));

			//---- button1 ----
			button1.setText("Start");
			contentPane.add(button1);
			button1.setBounds(125, 165, 145, 55);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			pack();
			setLocationRelativeTo(getOwner());
			// JFormDesigner - End of component initialization  //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
		// Generated using JFormDesigner Evaluation license - Wild Kubko
		private JLabel label1;
		private JComboBox comboBox1;
		private JLabel label2;
		private JComboBox comboBox2;
		private JButton button1;
		// JFormDesigner - End of variables declaration  //GEN-END:variables
	}


	@Override
	public String author() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}


}
