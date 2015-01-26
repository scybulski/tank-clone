/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlecityput;

/**
 *
 * @author Krzysiek
 */

import java.util.ArrayList;
import java.util.Random;
import java.awt.Rectangle;

public class Robot_Krzychu implements Robot{
    Tank tank;
    int t, i;
    Random r = new Random();
    Robot_Krzychu(Tank t){
        tank = t;
    }
    
    @Override
    public Tank get_tank() {
        return tank;
    }
    
    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta){
        i = r.nextInt(20);
        t = t+i;
        if(t>1600) t = 0;
        for (Tank p: tanks)
        {
            if(p.getPosX()==tank.getPosX() && p.getPosY()==tank.getPosY()) continue;
            else
            {
                if((p.getPosX() - tank.getPosX()) < 10 && (tank.getPosX() - p.getPosX() < 10))
                {
                    if(p.getPosY()>tank.getPosY())
                    {
                            return 9;
                    }
                    else if (p.getPosY()<tank.getPosY())
                    {
                            return 8;
                    }
                }
            else if ((p.getPosY() - tank.getPosY()) < 10 && (tank.getPosY() - p.getPosY() < 10))
            {
                if(p.getPosX()>tank.getPosX())
                {
                    return 6;//prawo
                }
                else
                {
                        return 7;//lewo
                }
                 
            }
            else if((p.getPosX() - tank.getPosX()) < 25 && (p.getPosX() - tank.getPosX()) > 0 )  
            {
                return 1;
            }
            else if((p.getPosX() - tank.getPosX()) > -25 && (p.getPosX() - tank.getPosX()) < 0)
            {
                return 2;
            }
            else if((p.getPosY() - tank.getPosY()) < 25 && (p.getPosY() - tank.getPosY()) > 0 )
            {
                return 4;
            }
            else if((p.getPosY() - tank.getPosY()) > -25 && (p.getPosY() - tank.getPosY()) < 0 )
            {
                return 3;
            }
        }    
        }

        for (Tank n : neutrals)
        {
            if((n.getPosX() - tank.getPosX()) < 10 && (tank.getPosX() - n.getPosX() < 10))
                {
                    if(n.getPosY()>tank.getPosY())
                    {
                            return 9;
                    }
                    else
                    {
                            return 8;
                    }
                }
            else if ((n.getPosY() - tank.getPosY()) < 10 && (tank.getPosY() - n.getPosY() < 10))
            {
                if(n.getPosX()>tank.getPosX())
                {
                    return 6;//prawo
                }
                else
                {
                        return 7;//lewo
                }
            }
        }
        if(t>1400) return 1;
        else if(t>1000) return 2;
        else if(t>800) return 1;
        else if(t>600) return 3;
        else if(t>200) return 4;
        else return 3;
    }
}   
