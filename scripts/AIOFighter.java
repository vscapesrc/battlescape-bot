package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.bsbot.api.Calculations;
import com.bsbot.api.TilePath;
import com.bsbot.hooks.Npc;
import com.bsbot.launcher.BSLoader;
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
	Rectangle r;

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.YELLOW);
		g.drawString(state, 10, 10);
		g.drawString("in combat: " +getMyPlayer().isInCombat(), 10, 30);
		if(nearest != null){
			g.setColor(Color.GREEN);
			Point p = Calculations.worldToMinimap(nearest.getLocation());
			g.drawRect(p.x, p.y, 1, 1);//:((int)p.getX() , (int)p.getY(), 6, 6);
			g.setColor(Color.WHITE);
		}

	}

	public RSNPC getNearestNonCombatNpc(String name) {
		double dist = -1;
		RSNPC fin;
		RSNPC cur = null;
		int curX = getMyPlayer().getLocation().getX();
		int curY = getMyPlayer().getLocation().getY();

		for (int i = 0; i < BSLoader.getClient().getNpcs().length; i++) {
			Npc a = BSLoader.getClient().getNpcs()[i];
			if (a != null && a.getDefinition() != null
					&& a.getDefinition().getName() != null){
				RSNPC aa = new RSNPC(a, i);
				if(!aa.isInCombat() || aa.getInteracting() == getMyPlayer())
			//	if (aa.getInteracting() == null || aa.getInteracting() == getMyPlayer())
					if (aa.getName().equalsIgnoreCase(name)) {
						int x, y;

						RSNPC th = new RSNPC(a, i);
						x = th.getLocation().getX();
						y = th.getLocation().getY();
						double distance = Math.sqrt((curX - x) * (curX - x)
								+ (curY - y) * (curY - y));

						if (distance < dist || dist == -1) {
							dist = distance;
							cur = new RSNPC(a, i);
						}
					}
					}
		}
		return cur;

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
			nearest = this.getNearestNonCombatNpc(s);
		}
		if (nearest != null) {
			if (getMyPlayer().getLocation().equals(nearest.getLocation())) {
				TilePath tp = new TilePath(new RSTile(getMyPlayer()
						.getLocation().getX() + 1, getMyPlayer().getLocation()
						.getY() + 1));
			}
		}
		if (nearest != null
				&& nearest.getAnimation() != 2304  ) {
			if(!getMyPlayer().isInCombat()){
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
		npcNames = a.split(" ");
		for(int i=0;i<npcNames.length;i++){
			npcNames[i] = npcNames[i].replace("_", " ");
		}
		System.out.println(npcNames[0]);
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub

	}

}
