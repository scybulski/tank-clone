package battlecityput;

import java.awt.Rectangle;

public abstract class GameObject
{
    Tank parentTank;
    Bullet.Direction Direction;
    public abstract boolean collides(Tank t);
    
    public abstract boolean draw();
    
    public abstract Rectangle getHitBox();
    
    public abstract void handleCollision();
    
    public abstract void move(float delta);

    Tank getParentTank() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean getIsDestroyed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setIsDestroyed(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Rectangle getRect(float i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} 