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
        pos = new Rectangle((int)posX, (int)posY, sprite.getWidth(), sprite.getWidth());
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
                break;
            case 1:
                this.direction = Bullet.Direction.DOWN;
                break;
            case 2:
                this.direction = Bullet.Direction.LEFT;
                break;
            case 3:
                this.direction = Bullet.Direction.UP;
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
        posX += dv*vel;
    }
    
    public void changePosY(float dv)
    {
        posY += dv*vel;
    }
} 
