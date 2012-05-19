package com.bsbot.events.debugpaint;

import com.bsbot.api.Methods;

import java.awt.*;

public abstract class DebugPaint {

    Methods methods;

    public DebugPaint(Methods m) {
        this.methods = m;
    }

    public abstract void paint(Graphics g);

    private boolean isEnabled = false;

    public void setEnabled(boolean which) {
        isEnabled = which;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
