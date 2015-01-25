package battlecityput;

import java.awt.Rectangle;

public abstract class GameObject
{
    public abstract boolean collides(Tank t);
    
    public abstract boolean draw();
    
    public abstract Rectangle getHitBox();
    
    public abstract void handleCollision();
    
    public abstract void move(float delta);
} 