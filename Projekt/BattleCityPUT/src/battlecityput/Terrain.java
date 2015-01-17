package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;

public class Terrain
{
    private TiledMap map;
    
    private ArrayList<Rectangle> blocks;

    public static int TILESIZE;
    
    public Terrain() throws SlickException
    {
        map = new TiledMap("surowce/map.tmx");
        
        
        
        TILESIZE = map.getTileWidth();
        
        blocks = new ArrayList<Rectangle>();
        
    }
    
    public void addBlock(Rectangle rect)
    {
        blocks.add(rect);
    }
    
    public boolean checkCollision(Rectangle objectRect)
    {   
        for(Rectangle rect : blocks)
        {
            if(objectRect.intersects(rect))
            {
                return true;
            }
                
        }
        return false;
    }
    
    public void draw()
    {
        map.render(BattleCityPUT.margin, BattleCityPUT.margin);
    }
    
    public TiledMap getMap()
    {
        return map;
    }
} 