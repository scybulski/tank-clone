/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author szymon
 */
class Counters {
    
    private TiledMap map;
    
    private ArrayList<Rectangle> blocks;

    public static int TILESIZE;


    public Counters() throws SlickException
    {
        map = new TiledMap("surowce/counters.tmx");
        
        TILESIZE = map.getTileWidth();
        
        blocks = new ArrayList<Rectangle>();
    }
    
    public TiledMap getMap()
    {
        return map;
    }
    
    public void addBlock(Rectangle rect)
    {
        blocks.add(rect);
    }
    
}
