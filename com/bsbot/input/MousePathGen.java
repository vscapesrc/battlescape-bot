package com.bsbot.input;


import java.awt.*;

/**
 * This abstract class allows the script writer to provide thier own pathing algorithim if they wish, or the can
 * use one of the supplied ones.
 * Copyright (C) 2007  Travis Burtrum (moparisthebest)
 * <p/>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * <p/>
 * The code *may* be used under a lesser license (such as the LGPL) only with
 * express written permission from Travis Burtrum (moparisthebest)
 */
public abstract class MousePathGen {

    /**
     * This constructs a path from x1,y1 to x2,y2 using whatever algorithim is supplied by the child class.
     * @param x1 First x coord.
     * @param y1 First y coord.
     * @param x2 Second x coord.
     * @param y2 Second y coord.
     * @return The path to follow in the form of a point array.
     */
    public abstract Point[] makeMousePath(int x1, int y1, int x2, int y2);

    /**
     * This constructs a path from x1,y1 to x2,y2 using whatever algorithim is supplied by the child class.
     * @param from First coord.
     * @param to Second coord.
     * @return The path to follow in the form of a point array.
     */
    public final Point[] makeMousePath(Point from, Point to) {
        return makeMousePath(from.x, from.y, to.x, to.y);
    }

}
