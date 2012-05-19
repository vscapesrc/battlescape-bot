package com.bsbot.events.debugpaint;

import com.bsbot.api.Methods;

import java.awt.*;

public class DebugPosition extends DebugPaint {

    public DebugPosition(Methods m) {
        super(m);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString(methods.getMyPlayer().getLocation().toString(), 10, 10);

    }

}
