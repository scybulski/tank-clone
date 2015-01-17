package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class BattleCityPUT extends BasicGame
{
    private Terrain terrain;
    private Tank tank;
    private static ArrayList<Bullet> objects;
    //private Bullet bullet;
    
    public BattleCityPUT()
    {
        super("Battle City");
    }
    
    public static void main(String[] args)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new BattleCityPUT());
            app.setDisplayMode(260, 260, false);
            app.start();
        }
        catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void init(GameContainer container) throws SlickException
    {
        terrain = new Terrain();
        tank = new Tank(0);
        
        // lista obiektow do sprawdzania kolizji
        objects = new ArrayList<Bullet>();
        //bullet = new Bullet();
        
        for(int x = 0; x < terrain.getMap().getWidth(); x++)
        {
            for(int y = 0; y < terrain.getMap().getHeight(); y++)
            {
                int tile = terrain.getMap().getTileId(x, y, 0);
                String property = terrain.getMap().getTileProperty(tile, "blocked", "false");
                if("true".equals(property))
                {
                    terrain.addBlock(new Rectangle(x * Terrain.TILESIZE, y * Terrain.TILESIZE, Terrain.TILESIZE, Terrain.TILESIZE));
                }
            }
        }
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        
        if(input.isKeyDown(Input.KEY_RIGHT))
        {
            if(!terrain.checkCollision(tank.getRect(delta, 0)))
            {
                tank.rotate(0);
                //tank.setDirection(1);
                tank.changePosX(delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_LEFT))
        {
            if(!terrain.checkCollision(tank.getRect(-delta, 0)))
            {
                //tank.setDirection(3);
                tank.rotate(180);
                tank.changePosX(-delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_UP))
        {
            if(!terrain.checkCollision(tank.getRect(0, -delta)))
            {
                //tank.setDirection(0);
                tank.rotate(270);
                tank.changePosY(-delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_DOWN))
        {
            if(!terrain.checkCollision(tank.getRect(0, delta)))
            {
                //tank.setDirection(2);
                tank.rotate(90);
                tank.changePosY(delta);
            }
        }
        
        if(input.isKeyPressed(Input.KEY_SPACE)) 
        {
            tank.shoot();
            
//            if(!bullet.checkIfFired())
//            {
//                bullet.setIsFired(true);
//                bullet.setShootedDirection(tank.getDirection());
//            }
        }
        
        for(Iterator<Bullet> iterator = objects.iterator(); iterator.hasNext(); ) //(Bullet b : objects)
        {
            Bullet b = iterator.next();
            if(!terrain.checkCollision(b.getRect(delta)))
            {
                b.move(delta);
            }
            else // kolizja - usuniecie
            {
                iterator.remove();
            }
        }
        
//        if(bullet.checkIfFired())
//        {
//            bullet.Move(delta);
//        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        terrain.getMap().render(0,0);
        
        tank.draw();
        
        for(Bullet b : objects)
        {
            b.draw();
        }
        
//        if(bullet.checkIfFired())
//        {
//            bullet.sprite.draw((int)bullet.getPosX(), (int)bullet.getPosY());
//        } 
//        else 
//        {
//            bullet.setPosX(tank.getPosX() + ((tank.sprite.getWidth()/2)-bullet.sprite.getWidth()/2));
//            bullet.setPosY(tank.getPosY() + ((tank.sprite.getHeight()/2)-bullet.sprite.getHeight()/2));
//        }
    }
    
    public static void addObject(Bullet b)
    {
        objects.add(b);
    }
}
