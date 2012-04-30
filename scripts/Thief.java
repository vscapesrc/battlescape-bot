package scripts;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.bsbot.api.Calculations;
import com.bsbot.api.Inventory;
import com.bsbot.api.Objects;
import com.bsbot.api.TilePath;
import com.bsbot.wrappers.RSBankItem;
import com.bsbot.wrappers.RSItem;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSTile;

public class Thief extends Script {

	TilePath toBank = new TilePath(new RSTile(3099, 3509), new RSTile(3097,
			3496));
	
	TilePath guardToBank = new TilePath(new RSTile(3102, 3516), new RSTile(3104, 3508) , new RSTile(3097,
			3496));
	
	String fishOption;
	
	boolean guiOn;
	
	
	

	@Override
	public int loop() {
		RSItem food = inventory.getItem("Lobster");
		if (food != null && getMyPlayer().getHpPercent() <= 50) {

			food.interact("Eat");
			return 10;
		}

		RSNPC man = getNearestNpc("Guard");
		if (!inventory.isFull()) {
			if (man != null) {
				if (man.isOnScreen()) {
					getNearestNpc("Guard").interact("Pickpocket");
					return 10;
				}else if(!inventory.isFull()){
					toBank.reverse().traverse();
					return 10;
				}
			}

		} else if (food != null) {
			food.interact("Eat");
			return 300;
		} else {
			if (banking.isOpen() && !inventory.containsItem("Lobster")) {
				banking.depositAll();
				sleep(300);
				RSBankItem lob = banking.getItem("Lobster");
				lob.interact("Withdraw 10");
				return 10;
			}
			RSObject bank = Objects.getNearest(26972);
			if (bank != null && !banking.isOpen() && bank.isOnScreen()
					&& Calculations.distanceTo(bank.getLocation()) < 8) {
				bank.interact("Quick");
				return 300;
			} else {
				
				toBank.traverse();
				return 300;
			}
		}
		return 10;
	}

	@Override
	public void onBegin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub

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
	
	public class GUI extends JFrame {
		public GUI() {
			initComponents();
		}

		private void initComponents() {
			super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			// Generated using JFormDesigner Evaluation license - Wild Kubko
			label1 = new JLabel();
			comboBox1 = new JComboBox();
			label2 = new JLabel();
			comboBox2 = new JComboBox();
			label3 = new JLabel();
			button1 = new JButton();

			//======== this ========
			setTitle("AIO Thief GUI");
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			//---- label1 ----
			label1.setText("What to thief");
			contentPane.add(label1);
			label1.setBounds(5, 50, 135, 20);

			//---- comboBox1 ----
			comboBox1.setModel(new DefaultComboBoxModel(new String[] {
				"Man",
				"Guard",
				"Paladin"
			}));
			contentPane.add(comboBox1);
			comboBox1.setBounds(5, 75, 100, 25);

			//---- label2 ----
			label2.setText("Banking mode");
			contentPane.add(label2);
			label2.setBounds(200, 45, 105, 20);

			//---- comboBox2 ----
			comboBox2.setModel(new DefaultComboBoxModel(new String[] {
				"Bank",
				"Drop"
			}));
			contentPane.add(comboBox2);
			comboBox2.setBounds(new Rectangle(new Point(260, 70), comboBox2.getPreferredSize()));
			comboBox2.setEnabled(false);
			
			//---- label3 ----
			label3.setText("AIO Thief v");
			label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 15f));
			contentPane.add(label3);
			label3.setBounds(15, 0, 270, 35);

			//---- button1 ----
			button1.setText("Start!");
			button1.setFont(button1.getFont().deriveFont(button1.getFont().getSize() + 6f));
			contentPane.add(button1);
			button1.setBounds(30, 185, 290, 55);

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
			button1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					Object toFish = comboBox1.getSelectedItem();
					fishOption = (String)toFish;
					doDispose();
					
				}
				
			});
			setVisible(true);
			// JFormDesigner - End of component initialization  //GEN-END:initComponents
		}
		
		public void doDispose(){
			setVisible(false);
			super.dispose();
			guiOn = false;
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
		// Generated using JFormDesigner Evaluation license - Wild Kubko
		private JLabel label1;
		private JComboBox comboBox1;
		private JLabel label2;
		private JComboBox comboBox2;
		private JLabel label3;
		private JButton button1;
		// JFormDesigner - End of variables declaration  //GEN-END:variables
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
