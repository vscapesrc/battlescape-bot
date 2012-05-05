package scripts.randoms;

import java.awt.Point;

import com.bsbot.hooks.Player;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.wrappers.RSPlayer;

public class AntiMod extends Random {
	Point loginPoint1 = new Point(633, 485);
	Point loginPoint2 = new Point(623, 372);

	@Override
	public boolean startUp() {
		if (isLoggedIn()) {
			try {
				for (RSPlayer p : getAllPlayers()) {
					if (p != null && p.getName() != null) {
						if (p.getName().equals("Go Hard")
								|| p.getName().equals("U Got 0wned")) {
							return true;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			BSLoader.getRunningScript().stop();
		}
		return false;

	}

	@Override
	public int loop() {
		System.out.println("loop");
		if (BSLoader.getClient().getOpenTab() != 10) {
			n.clickMouse(loginPoint1, true);
			sleep(300);
		}
		if (BSLoader.getClient().getOpenTab() == 10) {
			n.clickMouse(loginPoint2, true);
			sleep(300);
		}
		return 0;
	}

}
