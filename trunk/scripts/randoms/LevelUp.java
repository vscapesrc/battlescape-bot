package scripts.randoms;

import java.awt.Point;

import com.bsbot.hooks.GameInterface;
import com.bsbot.input.Mouse;
import com.bsbot.wrappers.RSInterface;

public class LevelUp extends Random {

	Mouse n = new Mouse();

	@Override
	public
	int loop() {
							n.moveMouse(new Point(273, 426));
							sleep(500);
							n.clickMouse(new Point(273, 426), true);
		return 0;
	}

	@Override
	public
	boolean startUp() {
		RSInterface[] parents = interfaces.getAllParents();
		for (RSInterface single : parents) {
			if (single != null) {
				for (GameInterface child : single.getParentInterface()) {
					if (child != null) {
						if (child.getText() != null
								&& child.getText().contains(
										"Click here to continue")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
