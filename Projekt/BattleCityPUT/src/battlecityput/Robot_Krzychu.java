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
        /*t++;
        if (t>3200)
            t=0;
        for (Tank e : tanks)
        {
            if(e.getPosX()==tank.getPosX() && e.getPosY()==tank.getPosY()) continue;
            if(e.getPosX()==tank.getPosX())
            {
                if(e.getPosY()>tank.getPosY())
                    return 9;//dol
                else
                    return 8;//gora
            }
            else if (e.getPosY()==tank.getPosY())
            {
                if(e.getPosX()>tank.getPosX())
                    return 6;//prawo
                else
                    return 7;//lewo
            }
        }
        
        
        for (Tank e : neutrals)
        {
            if(e.getPosX()==tank.getPosX())
            {
                if(e.getPosY()>tank.getPosY())
                    return 9;//dol
                else
                    return 8;//gora
            }
            else if (e.getPosY()==tank.getPosY())
            {
                if(e.getPosX()>tank.getPosX())
                    return 6;//prawo
                else
                    return 7;//lewo
            }*/
        i = r.nextInt(10);
        t = t+i;
        if(t>800) t = 0;
        for (Tank p: tanks)
        {
            if(p.getPosX()==tank.getPosX() && p.getPosY()==tank.getPosY()) continue;
            else
            {
                if((p.getPosX() - tank.getPosX()) < 20 && (tank.getPosX() - p.getPosX() > -20))
                {
                    if(p.getPosY()>tank.getPosY())
                    {
                    
                            return 9;
                    }
                    else if (p.getPosY()<tank.getPosY())
                    {
                    if(t>400)
                    {
                            return 8;
                        
                    }  
                }
            else if ((p.getPosY() - tank.getPosY()) < 20 && (tank.getPosY() - p.getPosY() > -20))
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
            else if((p.getPosX() - tank.getPosX()) < 50 && (p.getPosX() - tank.getPosX()) > 0 )  
            {
                //t=0;
                return 1;
            }
            else if((p.getPosX() - tank.getPosX()) > -50 && (p.getPosX() - tank.getPosX()) < 0)
            {
                //t=0;
                return 2;
            }
            else if((p.getPosY() - tank.getPosY()) < 50 && (p.getPosY() - tank.getPosY()) > 0 )
            {
                //t=0;
                return 4;
            }
            else if((p.getPosY() - tank.getPosY()) > -50 && (p.getPosY() - tank.getPosY()) < 0 )
            {
                //t=0;
                return 3;
            }
        }    
        }
        }
        for (Tank n : neutrals)
        {
            if(n.getPosX()==tank.getPosX())
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
            else if (n.getPosY()==tank.getPosY())
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
        if(t>700) return 1;
        else if(t>500) return 2;
        else if(t>400) return 1;
        else if(t>300) return 3;
        else if(t>100) return 4;
        else return 3;
        }
        
        
        /*if (t>2800)
            return 3;
        else if (t>2000)
            return 4;
        else if (t>1600)
            return 3;
        else if(t>1200)
            return 1;
        else if(t>400)
            return 2;
        else
            return 1;   */  
    }
    
   
