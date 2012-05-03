package scripts;

import java.awt.Graphics;


import com.bsbot.api.Calculations;
import com.bsbot.api.TilePath;
import com.bsbot.wrappers.RSTile;

public class AIOFighter extends Script{
	
	String state = "Starting up";

	@Override
	public void paint(Graphics g) {
		g.drawString("State:" + state, 10, 10);
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

	@Override
	public int loop() {
	com.bsbot.wrappers.RSGroundItem casket = grounditems.getNearest("Casket");
		if(casket != null){
			state = "Taking casket";
			casket.interact("Take");
			return 600;
		}
		com.bsbot.wrappers.RSNPC guard = getNearestNpc("Pirate");
		if(guard != null && getMyPlayer().getAnimation() == -1 && guard.getInteracting() == null){
			if(Calculations.distanceTo(guard.getLocation()) < 10){
				System.out.println("distance");
			if(guard.isOnScreen()){
				System.out.println("attack");
				state = "Attack " + guard.getName();
				guard.interact("Attack");
				return 700;
			}else{
				System.out.println("turn");
				state = "Turn camera to guard";
				camera.turnTo(guard);
				return 700;
			}
			}else{
				System.out.println("walk");
				state = "Walk to npc";
				TilePath toGuard = new TilePath(guard.getLocation());
				toGuard.traverse();
				return 600;
			}
		}
		System.out.println("sleep");
		return 500;
	}

	@Override
	public void onBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}
	
	

}
