package scripts.randoms;

import com.bsbot.api.Methods;
import com.bsbot.launcher.BSLoader;

public abstract class Random extends Methods implements Runnable {
	
	private boolean running = false;
	
	private boolean asd;
	
	public void setRunning(boolean what){
		running = what;
	}
	
	public abstract boolean startUp();
	
	public abstract int loop();
	

	@Override
	public void run() {
	int sleep;
	running = true;
	try{
		while(running){
			try{
			asd = startUp();
			if(asd){
				BSLoader.getRunningScript().setPaused(true);
				sleep = loop();
				BSLoader.getRunningScript().setPaused(false);
			if(sleep < 0){
				running = false;
			}else{
				Thread.sleep(sleep);
				Thread.sleep(2000);
			}
			}else{
				Thread.sleep(2000);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	}catch(Exception e){
	}
	}

}
