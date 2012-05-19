package com.bsbot.hooks;


public interface Node {

    public Node getNext();

    public Node getFirst();

    public abstract long getID();


}
