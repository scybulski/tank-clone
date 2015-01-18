package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tank
{
    private Image sprite;
    private Rectangle pos;
    private float posX, posY;
    private final float vel;
    private Bullet.Direction direction;
    private int ID;
    
    public Tank(int ID) throws SlickException
    {
        this.ID = ID;
        posX = 64f;
        posY = 64f;
        vel = 0.1f;
        direction = Bullet.Direction.RIGHT;
        sprite = new Image("surowce/tank.png");
        pos = new Rectangle((int)posX, (int)posY, 32, 32);
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
        switch((int)angle / 90)
        {
            case 0:
                this.direction = Bullet.Direction.RIGHT;
                if((posY % 16) <= 8)
                    posY -= posY % 16;
                else
                    posY += 16 - (posY % 16);
                break;
            case 1:
                this.direction = Bullet.Direction.DOWN;
                if((posX % 16) <= 8)
                    posX -= posX % 16;
                else
                    posX += 16 - (posX % 16);
                break;
            case 2:
                this.direction = Bullet.Direction.LEFT;
                if((posY % 16) <= 8)
                    posY -= posY % 16;
                else
                    posY += 16 - (posY % 16);
                break;
            case 3:
                this.direction = Bullet.Direction.UP;
                if((posX % 16) <= 8)
                    posX -= posX % 16;
                else
                    posX += 16 - (posX % 16);
                break;
        }
    }
    
    public void shoot() throws SlickException
    {
        BattleCityPUT.addObject(new Bullet(pos, direction));
    }
    
    public void draw()
    {
        sprite.draw((int)posX, (int)posY);
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
        if((posX + dv*vel >= 32) && (posX+dv*vel <= 416))
        {
            posX += dv*vel;
        }
    }
    
    public void changePosY(float dv)
    {
        if((posY + dv*vel >= 32) && (posY+dv*vel <= 416))
        {
            posY += dv*vel;
        }
    }
} 
