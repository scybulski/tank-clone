package battlecityput;

import static battlecityput.Bullet.Direction.DOWN;
import static battlecityput.Bullet.Direction.LEFT;
import static battlecityput.Bullet.Direction.RIGHT;
import static battlecityput.Bullet.Direction.UP;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public class Robot_Szymon implements Robot {

    Tank tank;
    int temp=4000;
    int shootBullet = 0;
    int lastRandAction = 0;
    int doShoot;
    ArrayList<Integer> movesQueue;
    
    Robot_Szymon(Tank t) {
        tank = t;
        movesQueue = new ArrayList();
        doShoot =0;
        /*tank.setPosX(224);
        tank.setPosY(220);
    */}

    @Override
    public Tank get_tank() {
        return tank;
    }
    
    private Integer round32(Float number)
    {
        if(number % 32 < 16)
        {
            number -= number % 32;
        }
        else
        {
            number += 32 -number % 32;
        }
        return Math.round(number);
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta)
    {
        shootBullet++;
        if(doShoot == 10)
        {
            doShoot--;
            return 5;
        }
            if(!movesQueue.isEmpty())
            {
                Integer move = movesQueue.get(0);
                movesQueue.remove(0);
                return move;
            }
        if(shootBullet == 20)
        {
            shootBullet = 0;
            for(GameObject bullet : objects)
            {
                boolean noObstacle = true;
                Integer note = 0;
                if(bullet != tank)
                {
                    if(bullet.getRect(0).getX() > round32(tank.getPosX())-15 && bullet.getRect(0).getX() < round32(tank.getPosX())+15)
                    {
                        if(bullet.Direction == DOWN && bullet.getRect(0).getY() < tank.getPosY())
                        {
                            for(Rectangle block: blocks)
                            {
                                if(block.getX() == round32(tank.getPosX()) &&
                                        block.getY() <= tank.getPosY() &&
                                        block.getY() >= bullet.getRect(0).getY())
                                {
                                    noObstacle = false;
                                }
                            }
                            if(noObstacle)
                            {
                                System.out.println("SZYMON: SH UP");
                                return 8;
                            }
                            else
                                System.out.println("SZYMON: SH U");
                        }
                        else if(bullet.Direction == UP)
                        {
                            for(Rectangle block: blocks)
                            {
                                if(block.getX() == round32(tank.getPosX()) &&
                                        block.getY() >= tank.getPosY() &&
                                        block.getY() <= bullet.getRect(0).getY())
                                {
                                    noObstacle = false;
                                }
                            }
                            if(noObstacle)
                            {
                                System.out.println("SZYMON: SH DOWN");
                                return 9;
                            }
                            else
                                System.out.println("SZYMON: SH D");
                        }
                    }
                    else if(bullet.getRect(0).getY() > Math.round(tank.getPosY())-15 && bullet.getRect(0).getY() < Math.round(tank.getPosY()))
                    {
                        if(bullet.Direction == LEFT && bullet.getRect(0).getX() > round32(tank.getPosX()))
                        {
                            for(Rectangle block: blocks)
                            {
                                if(block.getY() == round32(tank.getPosY()) &&
                                        block.getX() >= tank.getPosX() &&
                                        block.getX() <= bullet.getRect(0).getX())
                                {
                                    noObstacle = false;
                                }
                            }
                            if(noObstacle)
                            {
                                System.out.println("SZYMON: SH RIGHT");
                                if(doShoot == 0) doShoot = 11;
                                return 6;
                            }
                            else
                                System.out.println("SZYMON: SH R");

                        }
                        else if(bullet.Direction == RIGHT)
                        {
                            for(Rectangle block: blocks)
                            {
                                if(block.getY() == round32(tank.getPosY()) &&
                                        block.getX() <= tank.getPosX() &&
                                        block.getX() >= bullet.getRect(0).getX())
                                {
                                    noObstacle = false;
                                }
                            }
                            if(noObstacle)
                            {
                                System.out.println("SZYMON: SH LEFT");
                                return 7;
                            }
                            else
                                System.out.println("SZYMON: SH L");
                        }
                    }
                }
            }
        }
        else
        {
        
            ArrayList<Integer> whichBest;
            ArrayList<ArrayList<Tank>> allTanks;
            allTanks = new ArrayList();
            allTanks.add(tanks);
            allTanks.add(neutrals);
            whichBest = new ArrayList();
            for(ArrayList<Tank> list : allTanks)
            {
                for (Tank opp : list)
                {
                    boolean noObstacle = true;
                    Integer note = 0;
                    if(opp != tank)
                    {
                        if(opp.getPosX() > round32(tank.getPosX())-31 && opp.getPosX() < round32(tank.getPosX())+31)
                        {
                            if(opp.getPosY() < tank.getPosY())
                            {
                                for(Rectangle block: blocks)
                                {
                                    if(block.getX() == round32(tank.getPosX()) &&
                                            block.getY() <= tank.getPosY() &&
                                            block.getY() >= opp.getPosY())
                                    {
                                        noObstacle = false;
                                    }
                                }
                                if(noObstacle)
                                {
                                    System.out.println("SZYMON: UP");
                                    return 8;
                                }
                                else
                                    System.out.println("SZYMON: U");
                            }
                            else
                            {
                                for(Rectangle block: blocks)
                                {
                                    if(block.getX() == round32(tank.getPosX()) &&
                                            block.getY() >= tank.getPosY() &&
                                            block.getY() <= opp.getPosY())
                                    {
                                        noObstacle = false;
                                    }
                                }
                                if(noObstacle)
                                {
                                    System.out.println("SZYMON: DOWN");
                                    return 9;
                                }
                                else
                                    System.out.println("SZYMON: D");
                            }
                        }
                        else if(opp.getPosY() > Math.round(tank.getPosY())-31 && opp.getPosY() < Math.round(tank.getPosY()))
                        {
                            if(opp.getPosX() > round32(tank.getPosX()))
                            {
                                for(Rectangle block: blocks)
                                {
                                    if(block.getY() == round32(tank.getPosY()) &&
                                            block.getX() >= tank.getPosX() &&
                                            block.getX() <= opp.getPosX())
                                    {
                                        noObstacle = false;
                                    }
                                }
                                if(noObstacle)
                                {
                                    System.out.println("SZYMON: RIGHT");
                                    if(doShoot == 0) doShoot = 11;
                                    return 6;
                                }
                                else
                                    System.out.println("SZYMON: R");

                            }
                            else
                            {
                                for(Rectangle block: blocks)
                                {
                                    if(block.getY() == round32(tank.getPosY()) &&
                                            block.getX() <= tank.getPosX() &&
                                            block.getX() >= opp.getPosX())
                                    {
                                        noObstacle = false;
                                    }
                                }
                                if(noObstacle)
                                {
                                    System.out.println("SZYMON: LEFT");
                                    return 7;
                                }
                                else
                                    System.out.println("SZYMON: L");
                            }
                        }
                    whichBest.add(note);
                    }
                }
            }
        }
        //System.out.println("SZYMON: RAND");
        lastRandAction++;
        return lastRandAction;
    }
}