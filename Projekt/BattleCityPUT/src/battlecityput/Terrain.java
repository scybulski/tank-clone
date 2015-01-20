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
    
    public Terrain(String mapPath) throws SlickException
    {
        map = new TiledMap(mapPath);

        TILESIZE = map.getTileWidth();
        
        blocks = new ArrayList<Rectangle>();
        blocks.add(new Rectangle(0,0,32*15,32));
        blocks.add(new Rectangle(0,32*14-1,32*15,32));
        blocks.add(new Rectangle(0,32,32,32*13));
        blocks.add(new Rectangle(32*14-1,32,32,32*13));
        
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