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
    private float moveCoolDown;
    private int randomMove;
    public float shootCoolDown;
    
    
    public Tank() throws SlickException
    {
        ID = 1;
        posX = 64f;
        posY = 64f;
        vel = 0.1f;
        direction = Bullet.Direction.RIGHT;
        
        sprite = new Image("surowce/tank.png");
              
        pos = new Rectangle((int)posX, (int)posY, 32, 32);
    }
    
    //konstruktor dla neutralnych czolgow
    public Tank(float posX, float posY) throws SlickException
    {
        ID = 0;
        this.posX = posX;
        this.posY = posY;
        vel = 0.1f;
        moveCoolDown = 0;
        shootCoolDown = 100;
        direction = Bullet.Direction.DOWN;
        sprite = new Image("surowce/neutral_tank.png");
        
        pos = new Rectangle((int)this.posX, (int)this.posY, 32, 32);
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
    
    public void shoot(int randomFire) throws SlickException
    {
 
        BattleCityPUT.addObject(new Bullet(pos, direction));
        shootCoolDown = 300 + randomFire;
   
    }

    
    public void draw()
    {
        sprite.draw((int)posX, (int)posY);
    }
    
    public int getRandomMove()
    {
        return randomMove;
    }
    
    public void setRandomMove(int randomMove)
    {
        this.randomMove = randomMove;
    }
    
    public float getMoveCoolDown()
    {
        return moveCoolDown;
    }
    
    public void setMoveCoolDown(float moveCoolDown)
    {
        this.moveCoolDown = moveCoolDown;
    }
    
    public void decreaseMoveCoolDown(float value)
    {
        this.moveCoolDown -= value;
    }
    
    public void decreaseShootCoolDown(float value)
    {
        this.shootCoolDown -= value;
    }
    
    public float getShootCoolDown()
    {
        return shootCoolDown;
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
    
    public void setPosY(float posY)
    {
        this.posY = posY;
    }
    
    public void setPosX(float posX)
    {
        this.posX = posX;
    }
    
} 
