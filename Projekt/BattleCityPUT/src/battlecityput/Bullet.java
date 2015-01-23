package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends GameObject
{
    private Image sprite;
    private Tank parentTank;
    private Rectangle pos;
    private float posX, posY;
    private final float velX, velY, VEL = 0.25f;
    public enum Direction { RIGHT, DOWN, LEFT, UP; }
    private Direction shootDirection;
    
    private Integer margin = 32;

    
    public Bullet(Tank tank) throws SlickException
    {   
        sprite = new Image("surowce/bullet.png");
        parentTank = tank;
        parentTank.setFireState(true);
        shootDirection = tank.getDirection();
        pos = new Rectangle((int)tank.getPosX(), (int)tank.getPosY(), sprite.getWidth(), sprite.getHeight());
        
        switch(shootDirection)
        {
            case RIGHT:
                velX = VEL;
                velY = 0;
                posX = pos.x + tank.getRect(0,0).width;
                posY = pos.y + tank.getRect(0,0).width / 2 - pos.width / 2;
                break;
                
            case DOWN:
                velX = 0;
                velY = VEL;
                posX =  pos.x + tank.getRect(0,0).width / 2 - pos.width / 2;
                posY =  pos.y + tank.getRect(0,0).width;
                break;
                
            case LEFT:
                velX = -VEL;
                velY = 0;
                posX = pos.x;
                posY = pos.y + tank.getRect(0,0).width / 2 - pos.width / 2;
                break;
                
            case UP:
                velX = 0;
                velY = -VEL;
                posX = pos.x + tank.getRect(0,0).width / 2 - pos.width / 2;
                posY = pos.y;
                break;
                
            default:
                velX = velY =  posX = posY = 0;
                break;
        }
        
        pos.x = (int)posX;
        pos.y = (int)posY;
    }
    
    public Rectangle getRect(float delta)
    {
        pos.x = (int)(posX + delta*velX);
        pos.y = (int)(posY + delta*velY);
        
        return pos;
    }
    
    @Override
    public boolean collides(Rectangle rect)
    {
        return pos.intersects(rect);
    }
    
    @Override
    public Rectangle getHitBox()
    {
        return pos;
    }
    
    
    @Override
    public void handleCollision()
    {
        parentTank.setFireState(false);
    }
    
    @Override
    public void move(float delta)
    {
        posX += delta*velX;
        posY += delta*velY;
        pos.x = (int)posX;
        pos.y = (int)posY;
    }
    
    @Override
    public void draw()
    {
        sprite.draw((int)posX, (int)posY);
    }
}
