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
    private int direction; // 0-up 1-right 2-down 3-left
    private int ID;
    
    public Tank(int ID) throws SlickException
    {
        this.ID = ID;
        posX = 32f;
        posY = 32f;
        direction = 1;
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
    
    public int getDirection()
    {
        return direction;
    }
    
    public void setDirection(int newDirection)
    {
        this.direction = newDirection;
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