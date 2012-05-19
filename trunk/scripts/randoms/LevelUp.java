package scripts.randoms;

import com.bsbot.hooks.GameInterface;
import com.bsbot.input.Mouse;
import com.bsbot.wrappers.RSInterface;

import java.awt.*;

public class LevelUp extends Random {

	Mouse n = script.mouse;

	@Override
	public
	int loop() {
							n.moveMouse(new Point(273, 426));
							script.sleep(500);
							n.clickMouse(new Point(273, 426), true);
		return 0;
	}

	@Override
	public
	boolean startUp() {
		RSInterface[] parents = script.interfaces.getAllParents();
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
