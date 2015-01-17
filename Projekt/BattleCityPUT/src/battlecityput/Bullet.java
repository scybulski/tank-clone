package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet {

    public Image sprite;
    private Rectangle pos;
    private float posX, posY;
    private float vel = .3f;
    private int shootedDirection;
    private boolean isFired;
    
    private Integer margin = 32;

    
    public Bullet() throws SlickException
    {   
        posX = 26f;
        posY = 26f;
        sprite = new Image("surowce/bullet.png");
        isFired = false;
        pos = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
    }
    
    public float getPosX()
    {
        return posX;
    }
    
    public float getPosY()
    {
        return posY;
    }
    
    public void setPosX(float posX)
    {
        this.posX = posX+margin;
    }
    
    public void setPosY(float posY)
    {
        this.posY = posY+margin;
    }
    
    public int getshootedDirection()
    {
        return shootedDirection;
    }
    
    public void setShootedDirection(int shootedDirection)
    {
        this.shootedDirection = shootedDirection;
    }
    
    public boolean getIsFired()
    {
        return isFired;
    }
    
    public void setIsFired(boolean isFired)
    {
        this.isFired = isFired;
    }
    
    public Rectangle getRect(float dx, float dy)
    {
        pos.x = (int)(posX + dx*vel);
        pos.y = (int)(posY + dy*vel);
        
        return pos;
    }
    
    public void changePosX(float dv)
    {
        posX += dv*vel;
    }
    
    public void changePosY(float dv)
    {
        posY += dv*vel;
    }
    
    public void Move(float dv)
    {
        if(shootedDirection == 0) changePosY(-1*dv);
        if(shootedDirection == 1) changePosX(dv);
        if(shootedDirection == 2) changePosY(dv);
        if(shootedDirection == 3) changePosX(-1*dv);
    }
}
