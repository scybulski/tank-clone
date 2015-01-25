package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends GameObject
{
    private Image sprite,explosion[];
    private Tank parentTank;
    private Rectangle pos;
    private float posX, posY;
    private final float velX, velY, VEL = 0.25f;
    public enum Direction { RIGHT, DOWN, LEFT, UP; }
    private Direction shootDirection;
    private boolean isexplosion = false;
    
    private Integer margin = 32, explosionFrame;

    
    public Bullet(Tank tank) throws SlickException
    {   
        sprite = new Image("surowce/bullet.png");
        parentTank = tank;
        parentTank.setFireState(true);
        shootDirection = tank.getDirection();
        pos = new Rectangle((int)tank.getPosX(), (int)tank.getPosY(), sprite.getWidth(), sprite.getHeight());
        explosion = new Image[2];
        explosionFrame = 0;
        isexplosion = false;
        
        for(int i = 0; i < 2; i++)
            explosion[i] = new Image("surowce/expl"+i+".png");
        
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
    
    
    public Tank getParentTank()
    {
        return parentTank;
    }
    
    
    public Rectangle getRect(float delta)
    {
        pos.x = (int)(posX + delta*velX);
        pos.y = (int)(posY + delta*velY);
        
        return pos;
    }
    
    @Override
    public boolean collides(Tank t)
    {
        return pos.intersects(t.getHitBox()) && !parentTank.equals(t);
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
        isexplosion = true;
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
    public boolean draw()
    {
        if(isexplosion)
        {
            //System.out.println("DRAW COLLISION");
            if(explosionFrame < 10) // crash dla wartosci wiekszych od 10
            {
                explosion[explosionFrame/5].draw((int)posX - 35, (int)posY - 35);
                explosionFrame++;
            }
            else
            {
                return false;
            }
        }
        else
            sprite.draw((int)posX, (int)posY);
        
        return true;
    }
}
