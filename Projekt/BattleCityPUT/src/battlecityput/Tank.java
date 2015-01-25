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
    private Image sprite1,sprite2, immune1, immune2;
    private Rectangle pos;
    private float posX, posY;
    private final float vel;
    private Bullet.Direction direction;
    private boolean isNeutral;
    private int ID;
    private int lives;
    private float moveCoolDown;
    private int randomMove, frameImmune;
    private boolean frame;
    private boolean bulletFired;
    private boolean isDestroyed;
    public float shootCoolDown;
    private AudioClip shotsound;
    
    
    public Tank(int player) throws SlickException, MalformedURLException
    {
        isNeutral = false;
        ID = player;
        if (player==1){
            posX = 64f;
            posY = 64f;
            sprite1 = new Image("surowce/tankI.png");  //player I
            sprite2 = new Image("surowce/tankI2.png");  //plaer I frame 2
        }
        else if (player==2){
            posX = 384f;
            posY = 384f;
            sprite1 = new Image("surowce/tankII.png");  //player I
            sprite2 = new Image("surowce/tankII2.png");  //plaer I frame 2
        }
        else if (player==3){
            posX = 64f;
            posY = 384f;
            sprite1 = new Image("surowce/tankI.png");  //player I
            sprite2 = new Image("surowce/tankI2.png");  //plaer I frame 2
        }
        else{
            posX = 384f;
            posY = 64f;
            sprite1 = new Image("surowce/tankII.png");  //player I
            sprite2 = new Image("surowce/tankII2.png");  //plaer I frame 2
        }

        vel = 0.1f;
        direction = Bullet.Direction.RIGHT;
        lives = 1;
        bulletFired = false;
        this.loadCommonResources();
        pos = new Rectangle((int)posX, (int)posY, 32, 32);
    }
    
    //konstruktor dla neutralnych czolgow
    public Tank(float posX, float posY) throws SlickException, MalformedURLException
    {
        isNeutral = true;
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
        this.loadCommonResources();
        pos = new Rectangle((int)this.posX, (int)this.posY, 32, 32);
    }
    
    private void loadCommonResources() throws SlickException, MalformedURLException
    {
        immune1 = new Image("surowce/immune1.png");
        immune2 = new Image("surowce/immune2.png");
        shotsound = Applet.newAudioClip(new URL("file:surowce/shot.wav"));
    }
    
    public Rectangle getRect(float dx, float dy)
    {
        pos.x = (int)(posX + dx*vel);
        pos.y = (int)(posY + dy*vel);
        
        return pos;
    }
    
    @Override
    public boolean collides(Tank t)
    {
        return true;
    }
    
    @Override
    public Rectangle getHitBox()
    {
        return pos;
    }
    
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
            shootCoolDown = 100 + randomFire;
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
    public boolean draw()
    {
        if(frame)        
            sprite1.draw((int)posX, (int)posY);
        else
            sprite2.draw((int)posX, (int)posY);
        
        return true;
    }
    
    public void drawImmune()
    {
        frameImmune++;
        if(frameImmune < 4)
        {
            immune1.draw((int) posX, (int)posY);
        }
        else
        {
            immune2.draw((int) posX, (int)posY);
            if(frameImmune > 8)
                frameImmune = 0;
        }
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
    
    public boolean getIsNeutral()
    {
        return isNeutral;
    }
    
    public boolean getIsDestroyed()
    {
        return isDestroyed;
    }
    
    public void setIsDestroyed(boolean Is)
    {
        isDestroyed = Is;
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
