package battlecityput;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet
{
    private Image sprite;
    private Rectangle pos;
    private float posX, posY;
    private final float velX, velY, VEL = 0.25f;
    public enum Direction { RIGHT, DOWN, LEFT, UP; }
    private Direction shootDirection;
    
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
        
        pos = new Rectangle((int)posX, (int)posY, sprite.getWidth(), sprite.getHeight());
    }
    
    public Rectangle getRect(float delta)
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
}
