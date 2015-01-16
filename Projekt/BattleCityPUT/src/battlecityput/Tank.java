package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tank
{
    public Image sprite;
    private Rectangle pos;
    private float posX, posY;
    private float vel = 0.1f;
    
    public Tank() throws SlickException
    {
        posX = 26f;
        posY = 26f;
        
        pos = new Rectangle(0, 0, Terrain.TILESIZE, Terrain.TILESIZE);
        
        sprite = new Image("surowce/tank.png");
    }
    
    public Rectangle getRect(float dx, float dy)
    {
        pos.x = (int)(posX + dx*vel);
        pos.y = (int)(posY + dy*vel);
        
        return pos;
    }
    
    // obrocenie DO danego kata
    public void rotate(float angle)
    {
        sprite.setRotation(angle);
    }
    
    public float getPosX()
    {
        return posX;
    }
    
    public float getPosY()
    {
        return posY;
    }
    
    public void changePosX(float dv)
    {
        posX += dv*vel;
    }
    
    public void changePosY(float dv)
    {
        posY += dv*vel;
    }
} 