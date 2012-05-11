import java.awt.Graphics;

import scripts.Script;
import scripts.ScriptManifest;

@ScriptManifest(authors = { "Your name" }, name = "script name(used to run it)", version = 1, description = "Bs slave job")
public class SkeletonScript extends Script {

	@Override
	public void paint(Graphics g) {
		// paint
		g.drawString("Hello world!", 10, 10);
	}

	@Override
	public int loop() {
		//loop code here fo
		return 0;
	}

	@Override
	public void onBegin() {
		// on begin

	}

	@Override
	public void onFinish() {
		System.out.println("starting");
		// TODO Auto-generated method stub

	}

}
