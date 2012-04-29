package com.bsbot.api;

import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSEntity;
import com.bsbot.wrappers.RSObject;
import com.bsbot.wrappers.RSTile;




public class Camera
{
	
	public Methods accessor;
    public Camera(Methods m){
    	accessor = m;
    	
    }
    
	public void turnTo(final RSEntity c) {
		int angle = getCharacterAngle(c);
		setCameraRotation(angle);
	}
	
	public int getCharacterAngle(RSEntity n) {
		return getTileAngle(n.getLocation());
	}
	
	public int getTileAngle(RSTile t) {
		int a = (accessor.calc.angleToTile(t) - 90) % 360;
		return a < 0 ? a + 360 : a;
	}
	
	public void turnTo(final RSObject o) {
		int angle = getObjectAngle(o);
		setCameraRotation(angle);
	}
	
	public int getObjectAngle(RSObject o) {
		return getTileAngle(o.getLocation());
	}
	

    public int getCameraYaw()
    {
        return BSLoader.getClient().getXCameraCurve();
    }


    public int getCameraPitch()
    {
        return BSLoader.getClient().getYCameraCurve();
    }

    public int getCameraX()
    {
        return BSLoader.getClient().getXCameraPos();
    }

    public int getCameraY()
    {
        return BSLoader.getClient().getYCameraPos();
    }

    public static int getCameraZ()
    {
        return BSLoader.getClient().getZCameraPos();
    }

    public int getCameraAngle()
    {
        double mapAngle = getCameraYaw();
        mapAngle /= 2040D;
        mapAngle *= 360D;
        return (int)mapAngle;
    }

    public void setCameraRotation(int degrees)
    {
        char left = '%';
        char right = '\'';
        char whichDir = left;
        int start = getCameraAngle();
        if(start < 180)
            start += 360;
        if(degrees < 180)
            degrees += 360;
        if(degrees > start)
        {
            if(degrees - 180 < start)
                whichDir = right;
        } else
        if(start > degrees && start - 180 >= degrees)
            whichDir = right;
        degrees %= 360;
        accessor.keyboard.pressKey(whichDir);
        int timeWaited = 0;
        while(getCameraAngle() > degrees + 5 || getCameraAngle() < degrees - 5) 
        {
            sleep(10);
            if((timeWaited += 10) > 500)
            {
                int time = timeWaited - 500;
                if(time == 0)
                	accessor.keyboard.pressKey(whichDir);
                else
                if(time % 40 == 0)
                	accessor.keyboard.pressKey(whichDir);
            }
        }
        accessor.keyboard.releaseKey(whichDir);
    }

    public void setCompass(char direction)
    {
        switch(direction)
        {
        case 110: // 'n'
            setCameraRotation(359);
            break;

        case 101: // 'e'
            setCameraRotation(89);
            break;

        case 115: // 's'
            setCameraRotation(179);
            break;

        case 119: // 'w'
            setCameraRotation(269);
            break;

        default:
            setCameraRotation(359);
            break;
        }
    }

    public boolean setCameraAltitude(boolean up)
    {
        try
        {
            char key = up ? '&' : '(';
            accessor.keyboard.pressKey(key);
            sleep(nextInt(1000, 1500));
            accessor.keyboard.releaseKey(key);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public boolean setCameraAltitude(double altPercent)
    {
        int alt = (int)((altPercent / 100D) * -1237D - 1226D);
        int curAlt = getCameraZ();
        int lastAlt = 0;
        if(curAlt == alt)
            return true;
        if(curAlt > alt)
        {
        	accessor.keyboard.pressKey('&');
            for(long start = System.currentTimeMillis(); curAlt > alt && System.currentTimeMillis() - start < 30L; curAlt = getCameraZ())
            {
                if(lastAlt != curAlt)
                    start = System.currentTimeMillis();
                lastAlt = curAlt;
                sleep(1);
            }

            accessor.keyboard.releaseKey('&');
            return true;
        }
        accessor.keyboard.pressKey('(');
        for(long start = System.currentTimeMillis(); curAlt < alt && System.currentTimeMillis() - start < 30L; curAlt = getCameraZ())
        {
            if(lastAlt != curAlt)
                start = System.currentTimeMillis();
            lastAlt = curAlt;
            sleep(1);
        }

        accessor.keyboard.releaseKey('(');
        return true;
    }

    public static int nextInt(int min, int max)
    {
        return (int)(Math.random() * (double)(max - min)) + min;
    }

    private static void sleep(int i)
    {
        try
        {
            Thread.sleep(i);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
