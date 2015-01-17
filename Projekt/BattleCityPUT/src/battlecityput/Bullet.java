package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet {

    private Image sprite;
    private Rectangle pos;
    private float posX, posY;
    private final float velX, velY, VEL = 0.2f;
    //private int shootDirection;
    public enum Direction { RIGHT, DOWN, LEFT, UP; }
    private Direction shootDirection;
    private boolean isFired;
    
    private Integer margin = 32;

    
    public Bullet(Rectangle tankPos, Direction tankDirection) throws SlickException
    {   
        sprite = new Image("surowce/bullet.png");
        shootDirection = tankDirection;
        
        switch(shootDirection)
        {
            case RIGHT:
                velX = VEL;
                velY = 0;
                posX = tankPos.x + tankPos.width;
                posY = tankPos.y + tankPos.width / 2 - sprite.getWidth() / 2;
                break;
                
            case DOWN:
                velX = 0;
                velY = VEL;
                posX = tankPos.x + tankPos.width / 2 - sprite.getWidth() / 2;
                posY = tankPos.y + tankPos.width;
                break;
                
            case LEFT:
                velX = -VEL;
                velY = 0;
                posX = tankPos.x;
                posY = tankPos.y + tankPos.width / 2 - sprite.getWidth() / 2;
                break;
                
            case UP:
                velX = 0;
                velY = -VEL;
                posX = tankPos.x + tankPos.width / 2 - sprite.getWidth() / 2;
                posY = tankPos.y;
                break;
                
            default:
                velX = velY =  posX = posY = 0;
                break;
        }
        
        //isFired = false;
        pos = new Rectangle((int)posX, (int)posY, sprite.getWidth(), sprite.getHeight());
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
    
    public Direction getShootDirection()
    {
        return shootDirection;
    }
    
//    public void setShootDirection(int shootDirection)
//    {
//        this.shootDirection = shootDirection;
//    }
    
    public boolean checkIfFired()
    {
        return isFired;
    }
    
    public void setIsFired(boolean isFired)
    {
        this.isFired = isFired;
    }
    
    public Rectangle getRect(float delta/*float dx, float dy*/)
    {
        pos.x = (int)(posX + delta*velX);
        pos.y = (int)(posY + delta*velY);
        
        return pos;
    }
    
    public void move(float delta)
    {
        posX += delta*velX;
        posY += delta*velY;
    }
    
    public void draw()
    {
        sprite.draw((int)posX, (int)posY);
    }
    
//    public void changePosX(float dv)
//    {
//        posX += dv*vel;
//    }
//    
//    public void changePosY(float dv)
//    {
//        posY += dv*vel;
//    }
//    
//    public void Move(float dv)
//    {
//        if(shootDirection == 0) changePosX(dv);
//        else if(shootDirection == 1) changePosY(dv);
//        else if(shootDirection == 2) changePosX(-dv);
//        else if(shootDirection == 3) changePosY(-dv);
//    }
}
