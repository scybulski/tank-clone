package battlecityput;

import java.net.MalformedURLException;
import org.newdawn.slick.SlickException;

public class Ai {
    
    private int spawned;
    private int current;
    
    public Ai()
    {
        spawned = 0;
        current = 0; 
    }
    
    public void addSpawned() 
    {
        spawned ++;
    }
    
    public int getSpawned()
    {
        return spawned;
    }
    
    public void addCurrent()
    {
        current ++;
    }
    
    public void removeCurrent()
    {
        current --;
    }
    
    public int getCurrent()
    {
        return current;
    }
    
    public void spawn(int randomX, int randomY) throws SlickException, MalformedURLException
    {
        BattleCityPUT.addNeutralTank(new Tank(randomX,randomY));
        spawned ++;  
    }
}
