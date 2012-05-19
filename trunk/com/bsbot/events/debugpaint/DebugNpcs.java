package com.bsbot.events.debugpaint;

import com.bsbot.api.Methods;
import com.bsbot.wrappers.RSNPC;

import java.awt.*;

public class DebugNpcs extends DebugPaint {

    public DebugNpcs(Methods m) {
        super(m);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        for (RSNPC npc : methods.getAllNpcs()) {
            if (npc != null && npc.getScreenLocation() != null && !npc.getScreenLocation().equals(new Point(-1, -1)) && npc.isOnScreen()) {
                Point p = npc.getScreenLocation();
                g.drawString(Integer.toString(npc.getId()), p.x, p.y);
            }
        }
        g.setColor(Color.WHITE);

    }

}
