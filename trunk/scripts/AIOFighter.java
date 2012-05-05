package scripts;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import com.bsbot.api.Calculations;
import com.bsbot.api.TilePath;
import com.bsbot.wrappers.RSEntity;
import com.bsbot.wrappers.RSGroundItem;
import com.bsbot.wrappers.RSItem;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSTile;

@ScriptManifest(authors = { "Kubko" }, name = "AIOFighter", version = 1, description = "Fights any npc")
public class AIOFighter extends Script {

	String lootItems = "Casket";
	String[] npcNames;
	String state = "starting up";
	RSNPC nearest = null;

	@Override
	public void paint(Graphics g) {
		g.drawString(state, 10, 10);

	}

	@Override
	public int loop() {
		if (inventory.containsItem("Casket")) {
			RSItem casket = inventory.getItem("Casket");
			casket.interact("Open");
			return 400;
		}
		for (RSGroundItem a : grounditems.getAll()) {
			if (a.getName().equals(lootItems)) {
				state = "Taking " + lootItems;
				a.interact("Take");
				return 600;
			}
		}
		for (String s : npcNames) {
			nearest = getNearestNpc(s);
		}
		if (nearest != null) {
			if (getMyPlayer().getLocation().equals(nearest.getLocation())) {
				TilePath tp = new TilePath(new RSTile(getMyPlayer()
						.getLocation().getX() + 1, getMyPlayer().getLocation()
						.getY() + 1));
			}
		}
		if (nearest != null && nearest.getInteracting() == null
				&& getMyPlayer().getAnimation() == -1
				&& !(getMyPlayer().getInteracting() instanceof RSEntity)
				&& nearest.getAnimation() != 2304) {
			if (Calculations.distanceTo(nearest.getLocation()) < 7) {
				if (nearest.isOnScreen()) {

					state = "Attacking " + nearest.getName();
					nearest.interact("Attack");
					return 1000;
				} else {
					state = "Turning camera to " + nearest.getName();
					camera.turnTo(nearest);
					return 500;
				}
			} else {
				state = "walking to " + nearest.getName();
				TilePath tp = new TilePath(new RSTile(nearest.getLocation()
						.getX() + 2, nearest.getLocation().getY() + 2));
				tp.traverse();
				return 1000;
			}
		}
		// TODO Auto-generated method stub
		state = "Waiting";
		return 1000;
	}

	@Override
	public void onBegin() {
		String a = JOptionPane
				.showInputDialog(null,
						"NPC names to kill? seperate with spaces like this:goblin skeleton");
		a.replace("pirate", "");
		npcNames = a.split(" ");
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub

	}

}
