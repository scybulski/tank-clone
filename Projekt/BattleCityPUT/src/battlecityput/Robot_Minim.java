package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public class Robot_Minim implements Robot {

    Tank tank;
    int temp;
    ArrayList<Rectangle> blocks;
    
    Robot_Minim(Tank t) {
        tank = t;
    }

    @Override
    public Tank get_tank() {
        return tank;
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blo, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta) {
        blocks=blo;
        
        if (!tank.getFireState())
        {
            int i=0;
            if(!objects.isEmpty())
                i=bullet(objects);       
            if (i!=0)
                return i;

            i=enemy(tanks);       
            if (i!=0)
                return i;

            if(!neutrals.isEmpty())
                i=neutral(neutrals);       
            if (i!=0)
                return i;
        }

        temp++;
        if (temp>400)
            temp=0;

        if (temp>300)
            return 1;
        else if (temp>200)
            return 3;
        else if (temp>100)
            return 2;
        else
            return 4;     
    }
    
    private int bullet(ArrayList<GameObject> objects)
    {
        double d1,d2,dist=900;
        GameObject temp;
        temp=objects.get(0);
        
        for (GameObject o:objects)
        {
            d1=abso(o.getHitBox().y-tank.getHitBox().y);
            d2=abso(o.getHitBox().x-tank.getHitBox().x);
            if(o.getHitBox().x==tank.getHitBox().x+16 && d1<dist)
            {
                temp=o;
                dist=d1;
            }
            else if (o.getHitBox().y==tank.getHitBox().y+16 && d2<dist)
            {
                temp=o;
                dist=d2;
            } 
        }
        
        if(dist!=900)
        {
            if (temp.getHitBox().y>tank.getHitBox().y)
                return 9;//dol
            else if (temp.getHitBox().y<tank.getHitBox().y)
                return 8;//gora
            else if (temp.getHitBox().x>tank.getHitBox().x)
                return 6;//prawo
            else
                return 7;//lewo
        }
        else
            return 0;
    }
    
    private double abso(double temp)
    {
        if (temp>0)
            return temp;
        else
            return -temp;
    }
    
    private int enemy(ArrayList<Tank> tanks)
    {
        double d1,d2,dist=900;
        Tank temp;
        temp=tanks.get(0);
        
        for (Tank o:tanks)
        {
            d1=abso(o.getHitBox().y-tank.getHitBox().y);
            d2=abso(o.getHitBox().x-tank.getHitBox().x);
            if(o!=tank && o.getHitBox().x==tank.getHitBox().x && d1<dist)
            {
                temp=o;
                dist=d1;
            }
            else if (o!=tank && o.getHitBox().y==tank.getHitBox().y && d2<dist)
            {
                temp=o;
                dist=d2;
            } 
        }
        
        if(dist!=900)
        {
            if (temp.getHitBox().y>tank.getHitBox().y)
                return 9;//dol
            else if (temp.getHitBox().y<tank.getHitBox().y)
                return 8;//gora
            else if (temp.getHitBox().x>tank.getHitBox().x)
                return 6;//prawo
            else
                return 7;//lewo
        }
        else
            return 0;
    }
    
    private int neutral(ArrayList<Tank> neutrals)
    {
        double d1,d2,dist=900;
        Tank temp;
        temp=neutrals.get(0);
        
        for (Tank o:neutrals)
        {
            d1=abso(o.getHitBox().y-tank.getHitBox().y);
            d2=abso(o.getHitBox().x-tank.getHitBox().x);
            if(o.getHitBox().x==tank.getHitBox().x && d1<dist)
            {
                temp=o;
                dist=d1;
            }
            else if (o.getHitBox().y==tank.getHitBox().y && d2<dist)
            {
                temp=o;
                dist=d2;
            } 
        }
        
        if(dist!=900)
        {
            if (temp.getHitBox().y>tank.getHitBox().y)
                return 9;//dol
            else if (temp.getHitBox().y<tank.getHitBox().y)
                return 8;//gora
            else if (temp.getHitBox().x>tank.getHitBox().x)
                return 6;//prawo
            else
                return 7;//lewo
        }
        else
            return 0;
    }
    
}