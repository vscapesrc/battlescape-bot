package com.bsbot.events.debugpaint;

import com.bsbot.api.Methods;
import com.bsbot.wrappers.RSItem;

import java.awt.*;

public class DebugInventory extends DebugPaint {

    public DebugInventory(Methods m) {
        super(m);
    }

    @Override
    public void paint(Graphics g) {
        for (RSItem i : methods.inventory.getItems()) {
            if (i != null) {
                g.drawString("" + i.getId(), i.getScreenLocation().x, i.getScreenLocation().y);
            }
        }

    }

}
