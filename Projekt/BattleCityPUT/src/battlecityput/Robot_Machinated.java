package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Michał
 */

public class Robot_Machinated implements Robot {

    Tank tank;
    private int outCounter = 0;
    
    final private int edgePos = 32 * 13 - 8;
    final private Random randGen = new Random();
    private int randNumber;
    private int dir = getRandDir();
    private int weaponCooldown;
    private boolean downFree, upFree, leftFree, rightFree;
    private int spaceDown, spaceUp, spaceLeft, spaceRight;
    
    Robot_Machinated(Tank t) {
        tank = t;
    }

    @Override
    public Tank get_tank() {
        return tank;
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta)
    {
        spaceUp = 1000;
        spaceDown = 1000;
        spaceLeft = 1000;
        spaceRight = 1000;
        downFree = true;
        upFree = true;
        leftFree = true;
        rightFree = true;
        
        if (weaponCooldown > 0)
            weaponCooldown--;
        
        if (tank.getPosX() <= 40)
            leftFree = false;

        else if (tank.getPosX() >= edgePos)
            rightFree = false;
        
        if (tank.getPosY() <= 40)
            upFree = false;
        else if (tank.getPosY() >= edgePos)
            downFree = false;

        for (Rectangle block: blocks)       // locate obstacles
        {
            if (Math.abs((int)block.x - (int)tank.getPosX()) < 32)
            {
                int relpos = (int) (block.y - tank.getPosY());
                if (relpos >= 0)    // block above
                {
                    spaceDown = Math.min(spaceDown, relpos);
                    if (relpos <= 32)
                        downFree = false;
                }
                else
                {
                    spaceUp = Math.min(spaceUp, -relpos);
                    if (-relpos <= 32)
                        upFree = false;
                }
            }
            
            if (Math.abs((int)block.y - (int)tank.getPosY()) < 32)
            {
                int relpos = (int) (block.x - tank.getPosX());
                if (relpos >= 0)
                {
                    spaceRight = Math.min(spaceRight, relpos);
                    if (relpos <= 32)
                        rightFree = false;
                }
                else
                {
                    spaceLeft = Math.min(spaceLeft, -relpos);
                    if (-relpos <= 32)
                        leftFree = false;
                }
            }
        }
        
        for (Tank e : tanks)     // look for targets
        {
            if (e.getPosX() == tank.getPosX() && e.getPosY() == tank.getPosY()) // own tank
                continue;
            int relPos;
            if(Math.abs(e.getPosX() - tank.getPosX()) <= 24)
            {
                relPos = (int) (e.getPosY() - tank.getPosY());
                if(relPos >= 0)
                {
                    if (relPos <= spaceDown && shouldShoot())
                        return 9;
                }
                else
                {
                    if (-relPos <= spaceUp && shouldShoot())
                        return 8;
                }
            }
            else if (Math.abs(e.getPosY() - tank.getPosY()) <= 24)
            {
                relPos = (int) (e.getPosX() - tank.getPosX());
                if(relPos >= 0)
                {
                    if (relPos <= spaceRight && shouldShoot())
                        return 6;
                }
                else
                {
                    if (-relPos <= spaceLeft && shouldShoot())
                        return 7;
                }
            }
        }
        
        for (Tank e : neutrals)     // look for targets
        {
            //if (e.getPosX() == tank.getPosX() && e.getPosY() == tank.getPosY()) // own tank
            //    continue;
            int relPos;
            if(Math.abs(e.getPosX() - tank.getPosX()) <= 24)
            {
                relPos = (int) (e.getPosY() - tank.getPosY());
                if(relPos >= 0)
                {
                    if (relPos <= spaceDown && shouldShoot())
                        return 9;
                }
                else
                {
                    if (-relPos <= spaceUp && shouldShoot())
                        return 8;
                }
            }
            else if (Math.abs(e.getPosY() - tank.getPosY()) <= 24)
            {
                relPos = (int) (e.getPosX() - tank.getPosX());
                if(relPos >= 0)
                {
                    if (relPos <= spaceRight && shouldShoot())
                        return 6;
                }
                else
                {
                    if (-relPos <= spaceLeft && shouldShoot())
                        return 7;
                }
            }
        }
        
        if (randGen.nextInt(40) < 2)
        {
            dir = getRandDir();
        }
        
        /*
        if (outCounter++ == 500)
        {
            outCounter = 0;
            System.out.println("posx");
            System.out.println(tank.getPosX());
            System.out.println("posy");
            System.out.println(tank.getPosY());
        }
                */
        
        
        if (!isFreeAhead())
        {
            if (getFreeDirCount() > 1)
                dir = randGen.nextInt(getFreeDirCount()) + 1;
            else
                dir = getFreeDir();
        }
        
        /*
        if (randGen.nextInt(200) < 1)
        {
            dir += 5;   // take extra shot
        }
                */
        
        
        return dir;
          
    }
    
    private boolean shouldShoot()
    {
        if (weaponCooldown == 0)
        {
            weaponCooldown = 40;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private int getRandDir()
    {
        return randGen.nextInt(4) + 1;
    }
    
    private boolean isFreeAhead()
    {
        if (dir == 1)
            return rightFree;
        if (dir == 2)
            return leftFree;
        if (dir == 3)
            return upFree;
        if (dir == 4)
            return downFree;
        return false;
    }
    
    private int getFreeDir()
    {
        if (rightFree)
            return 1;
        if (leftFree)
            return 2;
        if (upFree)
            return 3;
        if (downFree)
            return 4;
        return 1;
    }
    
    private int getFreeDirCount()
    {
        int ret = 0;
        if (rightFree)
            ret++;
        if (leftFree)
            ret++;
        if (upFree)
            ret++;
        if (downFree)
            ret++;
        
        return ret;
    }
    
}