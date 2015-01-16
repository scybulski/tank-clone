package battlecityput;

import java.awt.Rectangle;
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
        tank = new Tank();
        
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
                tank.changePosX(delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_LEFT))
        {
            if(!terrain.checkCollision(tank.getRect(-delta, 0)))
            {
                tank.rotate(180);
                tank.changePosX(-delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_UP))
        {
            if(!terrain.checkCollision(tank.getRect(0, -delta)))
            {
                tank.rotate(270);
                tank.changePosY(-delta);
            }
        }
        else if(input.isKeyDown(Input.KEY_DOWN))
        {
            if(!terrain.checkCollision(tank.getRect(0, delta)))
            {
                tank.rotate(90);
                tank.changePosY(delta);
            }
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        terrain.getMap().render(0,0);
        tank.sprite.draw((int)tank.getPosX(), (int)tank.getPosY());
    }
}
