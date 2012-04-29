package com.bsbot.hooks;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FrameHook extends Frame {

	public FrameHook() {
		System.out.println("framehook");
	}

	
    public Graphics getGraphics() {
    	GAME_BUFFER.getGraphics().drawString("HI I H4x3d U", 10, 10);
        super.getGraphics().drawImage(GAME_BUFFER, 0, 0, null);

        return GAME_BUFFER.getGraphics();
}

	public final BufferedImage GAME_BUFFER = new BufferedImage(765, 503, 1);
	public final BufferedImage BOT_BUFFER = new BufferedImage(765, 503, 1);

}