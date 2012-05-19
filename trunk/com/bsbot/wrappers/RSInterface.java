package com.bsbot.wrappers;

import com.bsbot.api.Methods;
import com.bsbot.hooks.Client;
import com.bsbot.hooks.GameInterface;

import java.util.ArrayList;


public class RSInterface {


    private int index;
    private Methods methods;
    private Client hook;

    private com.bsbot.hooks.GameInterface[] ifaceHook;

    public com.bsbot.hooks.GameInterface[] getAccessor() {
        return ifaceHook;
    }

    public RSInterface(final int iface, Methods m) {
        this.methods = m;
        this.hook = methods.getHook();
        index = iface;
        ifaceHook = hook.getInterfaceCache()[iface];
    }

    public RSInterface(com.bsbot.hooks.GameInterface[] iface, int id) {
        index = id;
        ifaceHook = iface;
    }


    public GameInterface[] getParentInterface() {
        return ifaceHook;
    }

    public RSInterfaceChild[] getChildren() {
        ArrayList<RSInterfaceChild> child = new ArrayList<RSInterfaceChild>();
        for (GameInterface i : ifaceHook) {
            child.add(new RSInterfaceChild(ifaceHook, i, index, methods));
        }
        return child.toArray(new RSInterfaceChild[child.size()]);
    }

    public int getId() {
        return index;
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RSInterface) {
            final RSInterface inter = (RSInterface) obj;
            return inter.index == index;
        }
        return false;
    }


    @Override
    public int hashCode() {
        return index;
    }

}
