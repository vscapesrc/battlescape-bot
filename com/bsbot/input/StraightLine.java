package com.bsbot.input;



import java.awt.*;
import java.util.ArrayList;

/**
 * A simple MousePathGen that generates straight line paths.
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
public class StraightLine extends MousePathGen {

    /**
     * This constructs a path from x1,y1 to x2,y2 using a straight line algorithim.
     * @param x1 First x coord.
     * @param y1 First y coord.
     * @param x2 Second x coord.
     * @param y2 Second y coord.
     * @return The path to follow in the form of a point array.
     */
    public Point[] makeMousePath(int x1, int y1, int x2, int y2) {
        if ((x1 == x2) && (y1 == y2))
            return new Point[]{};
        else if (x1 == x2)
            return makeVerticalPath(x1, y1, y2);
        else if (y1 == y2)
            return makeHorizontalPath(x1, x2, y2);
        else {
            ArrayList<Point> path = new ArrayList<Point>();
            double slopex = x1 - x2;
            boolean posx = (slopex >= 0);
            double slopey = y1 - y2;
            boolean posy = (slopey >= 0);
            if (slopex > slopey) {
                slopey = Math.abs(slopey / slopex);
                slopex = 1;
            }
            if (slopey > slopex) {
                slopex = Math.abs(slopex / slopey);
                slopey = 1;
            }
            double dX1 = x1;
            double dY1 = y1;
            do {
                if (posx)
                    dX1 -= slopex;
                if (posy)
                    dY1 -= slopey;
                if (!(posx))
                    dX1 += slopex;
                if (!(posy))
                    dY1 += slopey;
                path.add(new Point((int) dX1, (int) dY1));
            }
            while (!((dX1 == x2) || (dY1 == y2)));
            return path.toArray(new Point[0]);
        }
    }

    private Point[] makeHorizontalPath(int x1, int x2, int y) {
        int len = Math.abs(x2 - x1);
        Point[] ret = new Point[len + 1];
        for (int x = 0; x <= len; ++x) {
            if (x1 < x2)
                ret[x] = new Point(x1 + x, y);
            else
                ret[x] = new Point(x1 - x, y);
        }
        return ret;
    }

    private Point[] makeVerticalPath(int x1, int y1, int y2) {
        int len = Math.abs(y2 - y1);
        Point[] ret = new Point[len + 1];
        for (int x = 0; x <= len; ++x) {
            if (y1 < y2)
                ret[x] = new Point(x1, y1 + x);
            else
                ret[x] = new Point(x1, y1 - x);
        }
        return ret;
    }

}
