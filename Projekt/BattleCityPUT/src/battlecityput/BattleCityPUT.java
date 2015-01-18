package battlecityput;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import sun.applet.Main;


public class BattleCityPUT extends BasicGame
{
    private Terrain terrain;
    private Tank tank;
    private static ArrayList<Bullet> objects;
    private Counters counters;
    private org.newdawn.slick.geom.Rectangle battlefieldbackground, grayforeground;
    private Music startmusic;
    private boolean playerenginesoundplaying, levelchooser;
    private int isPlayerMoving;
    
    public final static Integer margin = 32;
    private Clip startmusicclip;
    private AudioInputStream inputStream;
    private AudioClip shotsound, playerenginesound;
    
    public BattleCityPUT()
    {
        super("Battle City");
    }
    
    public static void main(String[] args)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new BattleCityPUT());
            app.setDisplayMode(512, 480, false);
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
        levelchooser = true; 
        tank = new Tank(0);
        counters = new Counters();
        battlefieldbackground = new org.newdawn.slick.geom.Rectangle(margin, margin, 416, 416);
        startmusic = new Music("surowce/start.ogg");
        //shotsound = new Music("surowce/shot.ogg");
        //playerenginesound = new Music("surowce/playerengine.ogg");

        URL url;
        try {
            shotsound = Applet.newAudioClip(new URL("file:surowce/shot.wav"));
            playerenginesound = Applet.newAudioClip(new URL("file:surowce/playerengine.wav"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // lista obiektow do sprawdzania kolizji
        objects = new ArrayList<Bullet>();
                
        counters = new Counters();
        counters.startGame();
}
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        if(levelchooser)
        {
            if(input.isKeyDown(Input.KEY_UP))
            {
                counters.increaseLevelNumber();
                try {
                    Thread.sleep(99);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(input.isKeyDown(Input.KEY_DOWN))
            {
                counters.decreaseLevelNumber();
                try {
                    Thread.sleep(99);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(input.isKeyDown(Input.KEY_ENTER))
            {
                levelchooser = false;
                terrain = new Terrain("surowce/stages/"+counters.getLevelNuber()+".tmx");
                for(int x = 0; x < terrain.getMap().getWidth(); x++)
                {
                    for(int y = 0; y < terrain.getMap().getHeight(); y++)
                    {
                        int tile = terrain.getMap().getTileId(x, y, 0);
                        String property = terrain.getMap().getTileProperty(tile, "blocked", "false");
                        if("true".equals(property))
                        {
                            terrain.addBlock(new Rectangle((x+1) * Terrain.TILESIZE, (y+1) * Terrain.TILESIZE, Terrain.TILESIZE, Terrain.TILESIZE));
                        }
                    }
                }
                startmusic.play();
            }
        }
        else
        {
            if(input.isKeyDown(Input.KEY_RIGHT))
            {
                tank.rotate(0);
                if(!terrain.checkCollision(tank.getRect(delta, 0)))
                {
                    //tank.setDirection(1);
                    tank.changePosX(delta);
                }
                isPlayerMoving = 11;
            }
            else if(input.isKeyDown(Input.KEY_LEFT))
            {
                tank.rotate(180);
                if(!terrain.checkCollision(tank.getRect(-delta, 0)))
                {
                    tank.changePosX(-delta);
                }
                isPlayerMoving = 11;
            }
            else if(input.isKeyDown(Input.KEY_UP))
            {
                tank.rotate(270);
                if(!terrain.checkCollision(tank.getRect(0, -delta)))
                {
                    tank.changePosY(-delta);
                }
                isPlayerMoving = 11;
            }
            else if(input.isKeyDown(Input.KEY_DOWN))
            {
                tank.rotate(90);
                if(!terrain.checkCollision(tank.getRect(0, delta)))
                {
                    tank.changePosY(delta);
                }
                isPlayerMoving = 11;
            }
            else
                isPlayerMoving--;

            if(input.isKeyPressed(Input.KEY_SPACE)) 
            {
                tank.shoot();
                counters.tankSpawned();  //testing purposes only BEGIN
                counters.setLives1P(counters.getLives1P()+1);
                counters.takeLive2P();
                counters.increaseLevelNumber();  //testing purposes only END 
                if(!startmusic.playing())
                    shotsound.play();
            }

            for(Iterator<Bullet> iterator = objects.iterator(); iterator.hasNext(); )
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
            if((isPlayerMoving > 0))
            {
                if(!playerenginesoundplaying && (!startmusic.playing()))
                {
                    playerenginesound.loop();
                    playerenginesoundplaying = true;
                }
            }
            else
            {
                playerenginesound.stop();
                playerenginesoundplaying = false;
            }
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.setBackground(org.newdawn.slick.Color.decode("#636363"));  //need2use Color from slick library
        if(levelchooser)
        {
            counters.drawLevelChooser();
        }
        else
        {
            g.fill(battlefieldbackground);
                g.setColor(org.newdawn.slick.Color.black);

            terrain.draw();
            tank.draw();
            counters.drawCounters();

            for(Bullet b : objects)
            {
                b.draw();
            }
        }
    }
    
    public static void addObject(Bullet b)
    {
        objects.add(b);
    }
}
