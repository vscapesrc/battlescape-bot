package com.bsbot.hooks;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AppletHook extends Applet {
	
	public AppletHook(){
		super();
		System.out.println("pylly");
	}
	
    private static BufferedImage botBuffer = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
    private BufferedImage gameBuffer = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
    
    
    @Override
    public Graphics getGraphics() {
            Graphics render = botBuffer.getGraphics();
            render.drawImage(gameBuffer, 0, 0, null);
            render.drawString("Hello", 30, 30);
            render.dispose();
            Graphics g = super.getGraphics();
            g.drawImage(botBuffer, 0, 0, null);
            return gameBuffer.getGraphics();
    }
    

	
	public void paint(){
		Graphics g = super.getGraphics();
		g.drawString("pylly", 100, 100);
	}
	
	public int getWidth(){
		return super.getWidth();
	}

}
