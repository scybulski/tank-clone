package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public class Robot_Szymon implements Robot {

    Tank tank;
    int temp=0;
    int doShoot = 0;
    
    Robot_Szymon(Tank t) {
        tank = t;
    }

    @Override
    public Tank get_tank() {
        return tank;
    }
    
    private Integer round32(Float number)
    {
        System.out.print("Round "+number);
        if(number % 32 < 16)
        {
            number -= number % 32;
        }
        else
        {
            number += 32 -number % 32;
        }
        System.out.println(" to "+number);
        return Math.round(number);
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta)
    {
        if(doShoot == 10)
        {
            doShoot--;
            return 5;
        }
        ArrayList<Integer> whichBest;
        whichBest = new ArrayList();
        for (Tank opp : tanks)
        {
            boolean noObstacle = true;
            Integer note = 0;
            if(opp != tank)
            {
                if(opp.getPosX() > round32(tank.getPosX())-15 && opp.getPosX() < round32(tank.getPosX())+15)
                {
                    if(opp.getPosY() < Math.round(tank.getPosY()))
                    {
                        for(Rectangle block: blocks)
                        {
                            System.out.println(Math.round(block.getY())+15 >= Math.round(tank.getPosY()));
                            if(Math.round(block.getX()) == round32(tank.getPosX()) &&
                                    Math.round(block.getY())+15 >= Math.round(tank.getPosY()) &&
                                    Math.round(block.getY())-15 <= Math.round(tank.getPosY()))
                            {
                                System.out.println("OBST"); noObstacle = false;
                            }
                        }
                        if(noObstacle)
                        {
                            System.out.println("SZYMON: UP");
                            if(doShoot > 0) doShoot = 11;
                            return 3;
                        }
                        else
                            System.out.println("SZYMON: D");
                    }
                    else
                    {
                        for(Rectangle block: blocks)
                        {
                            if(Math.round(block.getX()) == round32(tank.getPosX()) &&
                                    Math.round(block.getY())-15 <= Math.round(tank.getPosY()) &&
                                    Math.round(block.getY())+15 >= Math.round(tank.getPosY()))
                            {
                                System.out.println("OBST"); noObstacle = false;
                            }
                        }
                        if(noObstacle)
                        {
                            System.out.println("SZYMON: DOWN");
                            if(doShoot > 0) doShoot = 11;
                            return 4;
                        }
                        else
                            System.out.println("SZYMON: D");
                    }
                }
                else if(opp.getPosY() > Math.round(tank.getPosY())-15 && opp.getPosY() < Math.round(tank.getPosY()))
                {
                    if(opp.getPosX() > round32(tank.getPosX()))
                    {
                        for(Rectangle block: blocks)
                        {
                            if(Math.round(block.getY()) == Math.round(tank.getPosY()) &&
                                    Math.round(block.getX())-15 <= tank.getPosX() &&
                                    Math.round(block.getX())+15 >= round32(tank.getPosX()))
                            {
                                System.out.println("OBST"); noObstacle = false;
                            }
                        }
                        if(noObstacle)
                        {
                            System.out.println("SZYMON: RIGHT");
                            if(doShoot > 0) doShoot = 11;
                            return 1;
                        }
                        else
                            System.out.println("SZYMON: R");

                    }
                    else
                    {
                        for(Rectangle block: blocks)
                        {
                            if(Math.round(block.getY()) == Math.round(tank.getPosY()) &&
                                    Math.round(block.getX())-15 >= tank.getPosX() &&
                                    Math.round(block.getX())+15 <= round32(tank.getPosX()))
                            {
                                System.out.println("OBST"); noObstacle = false;
                            }
                        }
                        if(noObstacle)
                        {
                            System.out.println("SZYMON: LEFT");
                            if(doShoot > 0) doShoot = 11;
                            return 2;
                        }
                        else
                            System.out.println("SZYMON: L");
                    }
                }
            whichBest.add(note);
            }
        }
 //       System.out.println("SZYMON: SHOOT");
        return 5;
    }
}