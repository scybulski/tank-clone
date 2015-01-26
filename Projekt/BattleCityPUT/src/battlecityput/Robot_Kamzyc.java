package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Robot_Kamzyc implements Robot {

    Tank tank;
    int temp = 0;
    int random = 0;
    boolean isMoving = false;
    
    Robot_Kamzyc(Tank t) {
        tank = t;
    }

    @Override
    public Tank get_tank() {
        return tank;
    }

    @Override
    public int moveTank(ArrayList<Rectangle> blocks, ArrayList<GameObject> objects, ArrayList<Tank> neutrals, ArrayList<Tank> tanks, float delta) {
                
        /*
        1 prawo
        2 lewo
        3 gora
        4 dol
        5 strzal
        */
        
        temp ++;
        if(temp > 4000)
        {
            temp = 0;
            isMoving = false;
        }
        
        Random moveGenerator = new Random();
        if(!isMoving)
        {
            random = moveGenerator.nextInt(4) + 1; 
            isMoving = true;
        }
        
        if(temp == 3500 || temp == 3000 || temp == 2500 || temp == 2000 ||
                temp == 1500 || temp == 1000 || temp == 500)
        {
            isMoving = false;
        }
        
        for (Tank e : neutrals)
        {
            if(e.getPosX() == tank.getPosX())
            {
                if(e.getPosY() > tank.getPosY())
                    random = 9;
                else
                    random = 8;
            }
            else
                if (e.getPosY() == tank.getPosY())
                {
                    if(e.getPosX() > tank.getPosX())
                        random = 6;
                    else
                        random = 7;
                }
        }
        
        for (GameObject o : objects)
        {
            if(o.getHitBox().getX() == tank.getPosX())
            {
                if(o.getHitBox().getY() > tank.getPosY())
                    random = 6;
                else
                    random = 7;
            }
            else
                if(o.getHitBox().getY() == tank.getPosY())
                {
                    if(o.getHitBox().getX() > tank.getPosX())
                        random = 9;
                    else
                        random = 8;
                }     
        }
        
        for(Tank t : tanks)
        {
            if(tank != t)
            {
                if(t.getPosX() == tank.getPosX())
                {
                if(t.getPosY() > tank.getPosY())
                    random = 9;
                else
                    random = 8;
                }
                else
                if (t.getPosY() == tank.getPosY())
                {
                    if(t.getPosX() > tank.getPosX())
                        random = 6;
                    else
                        random = 7;
                }
            }
        }
        
        
        
        
        
        return random;
        

    }

}