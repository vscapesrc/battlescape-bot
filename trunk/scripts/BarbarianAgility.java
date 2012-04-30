package scripts;

import java.awt.Graphics;

import com.bsbot.wrappers.RSObject;



public class BarbarianAgility extends Script{

	@Override
	public String author() {
		return "kubko";
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return "barb agility";
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "does barb agility";
	}

	@Override
	public int loop() {
		RSObject ropeswing = objects.getNearest(2282);
		if(ropeswing != null){
		ropeswing.interact("Swing-on");
		}
		// TODO Auto-generated method stub
		return 1000;
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
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	
}