package battlecityput;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tank extends GameObject
{
    private Image sprite1,sprite2;
    private Rectangle pos;
    private float posX, posY;
    private final float vel;
    private Bullet.Direction direction;
    private int ID;
    private int lives;
    private float moveCoolDown;
    private int randomMove;
    private boolean frame;
    private boolean bulletFired;
    public float shootCoolDown;
    private AudioClip shotsound;
    
    
    public Tank() throws SlickException, MalformedURLException
    {
        ID = 1;
        posX = 64f;
        posY = 64f;
        vel = 0.1f;
        direction = Bullet.Direction.RIGHT;
        lives = 3;
        bulletFired = false;
        sprite1 = new Image("surowce/tankI.png");  //player I
        sprite2 = new Image("surowce/tankI2.png");  //plaer I frame 2
        shotsound = Applet.newAudioClip(new URL("file:surowce/shot.wav"));
        pos = new Rectangle((int)posX, (int)posY, 32, 32);
    }
    
    //konstruktor dla neutralnych czolgow
    public Tank(float posX, float posY) throws SlickException, MalformedURLException
    {
        ID = 0;
        this.posX = posX;
        this.posY = posY;
        vel = 0.05f;
        lives = 1;
        bulletFired = false;
        moveCoolDown = 0;
        shootCoolDown = 100;
        direction = Bullet.Direction.DOWN;
        sprite1 = new Image("surowce/neutral_tank.png");
        sprite2 = new Image("surowce/neutral_tank2.png");
        shotsound = Applet.newAudioClip(new URL("file:surowce/shot.wav"));
        pos = new Rectangle((int)this.posX, (int)this.posY, 32, 32);
    }
    
    public Rectangle getRect(float dx, float dy)
    {
        pos.x = (int)(posX + dx*vel);
        pos.y = (int)(posY + dy*vel);
        
        return pos;
    }
    
    @Override
    public boolean collides(Rectangle rect)
    {
        return true;
    }
    
    @Override
    public Rectangle getHitBox()
    {
        return pos;
    }
    
    // deklaracja z koniecznosci dziedziczenia
    @Override
    public void move(float d)
    {}
    
    // obrocenie DO danego kata
    public void rotate(float angle)
    {
        sprite1.setRotation(angle);
        sprite2.setRotation(angle);
        frame = !frame ;
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
    
    public boolean shoot(int randomFire, boolean doPlayShotSound) throws SlickException
    {
        if(!bulletFired)
        {
            BattleCityPUT.addObject(new Bullet(this));
            shootCoolDown = 300 + randomFire;
            if(doPlayShotSound)
                shotsound.play();
            return true;
        }
        return false;
    }
    
    
    public void setFireState(boolean state)
    {
        bulletFired = state;
    }
    
    
    @Override
    public void handleCollision()
    {
        
    }

    
    @Override
    public void draw()
    {
        if(frame)        
            sprite1.draw((int)posX, (int)posY);
        else
            sprite2.draw((int)posX, (int)posY);
    }
    
    public Bullet.Direction getDirection()
    {
        return direction;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    public void decreaseLives()
    {
        lives--;
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
