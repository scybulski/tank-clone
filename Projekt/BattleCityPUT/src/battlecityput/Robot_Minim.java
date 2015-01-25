package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public class Robot_Minim implements Robot {

    Tank tank;
    int temp=0;
    
    Robot_Minim(Tank t) {
        tank = t;
    }

    @Override
    public Tank get_tank() {
        return tank;
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<Rectangle> obj, ArrayList<Rectangle> neut, ArrayList<Rectangle> enem) {
        temp++;
        if (temp>4000)
            temp=0;
        
        for (Rectangle e:enem)
        {
            if(e.x==tank.getPosX())
            {
                if(e.y>tank.getPosY())
                    return 6;
                else
                    return 7;
            }
            else if (e.y==tank.getPosY())
            {
                if(e.x>tank.getPosX())
                    return 8;
                else
                    return 9;
            }
        }
        
        
        if (temp>3000)
            return 1;
        else if (temp>2000)
            return 2;
        else if (temp>1000)
            return 3;
        else
            return 4;     
    }
    
}