package scripts;

import java.awt.Graphics;

import scripts.randoms.LevelUp;
import scripts.randoms.NumberRandom;
import scripts.randoms.Random;

import com.bsbot.api.Methods;
import com.bsbot.launcher.BSLoader;

public abstract class Script extends Methods implements Runnable {

	public String[] getDefinition() {
		String[] returnString = null;
		returnString[0] = scriptName();
		returnString[1] = description();
		returnString[2] = author();

		return returnString;

	}

	public abstract void paint(Graphics g);

	public abstract String author();

	public abstract String scriptName();

	public abstract String description();

	public abstract int loop();

	public abstract void onBegin();

	public abstract void onFinish();

	private boolean running;

	private boolean paused;

	public void setPaused(boolean a) {
		paused = a;
	}

	public void stop() {
		running = false;
	}

	Random[] randoms = new Random[2];// = new NumberRandom();

	public void setRunning() {
		running = true ? running == false : running == true;
		System.out.println(running);
	}

	@Override
	public void run() {
		if (isLoggedIn()) {
			running = true;
			int sleep;
			try {
				onBegin();
				randoms[0] = new NumberRandom();
				///Thread s = new Thread(randoms[0], "NumberRandom");
				////s.start();
				randoms[1] = new LevelUp();
			///	Thread l = new Thread(randoms[1], "LevelUp");
			/////	l.start();

				try {
					while (running) {
						try {
							if (!paused) {
								for(Random r : randoms){
									if(r.startUp()){
										sleep = r.loop();
										Thread.sleep(sleep);
									}
								}
								sleep = loop();
								if (sleep < 0) {
									running = false;
								} else {
									Thread.sleep(sleep);
									Thread.sleep(300);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				} catch (Exception e) {
					for (Random rand : randoms) {
						rand.setRunning(false);
						rand = null;
					}// / stop random event
					randoms = null;
					onFinish();

				}
			} catch (Exception e) {
			}
		} else {
			System.out
					.println("Please start script AFTER login screen and AFTER lobby");
		}
	}
}
