package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Robot_Droid implements Robot
{
    private Tank tank;
    private int moving;
    private int axis;
    private int retVal;
    private final Random rand = new Random();
    
    Robot_Droid(Tank t)
    {
        tank = t;
        moving = 0;
        axis = 0;
        retVal = 0;
    }
    
    
    @Override
    public int moveTank(ArrayList<Rectangle> blocks,
            ArrayList<GameObject> objects,
            ArrayList<Tank> neutrals,
            ArrayList<Tank> tanks,
            float delta)
    {
        // 1 - right, 2 - left, 3 - up, 4 - down
        
        if(moving > 0)
        {
            moving--;
            return retVal;
        }
        
        delta *= 2;
        axis++;
        if(axis > 500)
            axis = 0;
        
        Rectangle opos = getOpponentTank(tanks).getRect(0, 0);
        
        if(axis < 250)
        {
            if(tank.getPosX() < opos.x)
            {
                if(!checkCollision(blocks, delta, 0))
                    return 6; // w prawo
                else if(!checkCollision(blocks, 0, -delta))
                {
                    setParams(8);
                    return 8; // dol
                }
                else if(!checkCollision(blocks, 0, delta))
                {
                    setParams(9);
                    return 9; // gora
                }
            }
            else
            {
                if(!checkCollision(blocks, -delta, 0))
                    return 7;
                else if(!checkCollision(blocks, 0, delta))
                {
                    setParams(9);
                    return 9;
                }
                else if(!checkCollision(blocks, 0, -delta))
                {
                    setParams(8);
                    return 8;
                }
            }
        }
        else
        {
            if(tank.getPosY() < opos.y)
            {
                if(!checkCollision(blocks, 0, delta))
                    return 9;
                else if(!checkCollision(blocks, delta, 0))
                {
                    setParams(6);
                    return 6;
                }
                else if(!checkCollision(blocks, -delta, 0))
                {
                    setParams(7);
                    return 7;
                }
            }
            else
            {
                if(!checkCollision(blocks, 0, -delta))
                    return 8;
                else if(!checkCollision(blocks, delta, 0))
                {
                    setParams(6);
                    return 6;
                }
                else if(!checkCollision(blocks, -delta, 0))
                {
                    setParams(7);
                    return 7;
                }
            }
        }
        
        return 0;
    }
    
    
    private Tank getOpponentTank(ArrayList<Tank> tanks)
    {
        for(Tank t : tanks)
        {
            if(t != tank)
            {
                return t;
            }
        }
        return null;
    }
    
    
    private boolean checkCollision(ArrayList<Rectangle> blocks, float dx, float dy)
    {
        Rectangle rect = tank.getRect(dx, dy);
        if(insideMap(rect.x) && insideMap(rect.y))
            for(Rectangle block : blocks)
            {
                if(tank.getRect(dx, dy).intersects(block))
                {
                    return true;
                }
            }
        return false;
    }
    
    
    private boolean insideMap(int val)
    {
        return (val >= 32 && val <= 416);
    }
    
    
    private void setParams(int dir)
    {
        moving = rand.nextInt(200);
        moving += 100;
        retVal = dir;
    }
    
    
    @Override
    public Tank get_tank()
    {
        return tank;
    }
} 