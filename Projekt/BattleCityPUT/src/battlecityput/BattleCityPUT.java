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
import java.util.Random;
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
import org.newdawn.slick.geom.Shape;
import sun.applet.Main;


public class BattleCityPUT extends BasicGame
{
    private Terrain terrain;
    private Tank tank;
    private Ai ai;
    private static ArrayList<Bullet> objects;
    private static ArrayList<Tank> neutrals;
    private Counters counters;
    private org.newdawn.slick.geom.Rectangle battlefieldbackground, grayforeground;
    private Music startmusic;
    private boolean playerenginesoundplaying,russiantanksoundplaying, levelchooser,
            isgameover;
    private int isPlayerMoving;
    private long lasttimetanksspawned;
    
    public final static Integer margin = 32;
    private Clip startmusicclip;
    private AudioInputStream inputStream;
    private AudioClip shotsound, playerenginesound, russianenginesound;
    
    int randomX;
    int randomY;
    boolean canSpawn;
    int collision = 0;
    int randomfire;
    
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
        
        ai = new Ai();
        levelchooser = true; 
        tank = new Tank();
        counters = new Counters();
        lasttimetanksspawned = System.currentTimeMillis();
        
        
        battlefieldbackground = new org.newdawn.slick.geom.Rectangle(margin, margin, 416, 416);
        startmusic = new Music("surowce/start.ogg");

        URL url;
        try {
            shotsound = Applet.newAudioClip(new URL("file:surowce/shot.wav"));
            playerenginesound = Applet.newAudioClip(new URL("file:surowce/playerengine.wav"));
            russianenginesound = Applet.newAudioClip(new URL("file:surowce/russiantank.wav"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // lista obiektow do sprawdzania kolizji
        objects = new ArrayList<Bullet>();
        neutrals = new ArrayList<Tank>();
                
        counters = new Counters();
        counters.startGame();
}
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        if(levelchooser)
        {
            if(input.isKeyPressed(Input.KEY_UP))
            {
                counters.increaseLevelNumber();
            }
            else if(input.isKeyPressed(Input.KEY_DOWN))
            {
                counters.decreaseLevelNumber();
            }
            if(input.isKeyDown(Input.KEY_ENTER))
            {
                levelchooser = false;
                isgameover = false;
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
                tank.shoot(0);
                if(!startmusic.playing())
                    shotsound.play();
            }

            if(ai.getCurrent() < 8 && counters.getRussianTanksLeft() > 0 && System.currentTimeMillis() > lasttimetanksspawned + 5432)
            {
                do
                {
                    Random posGenerator = new Random();
                    randomX = posGenerator.nextInt(353) + margin;
                    randomY = posGenerator.nextInt(353) + margin;
                    if(!terrain.checkCollision(new Rectangle(randomX, randomY, 32, 32)))
                    {
                        for(Tank t : neutrals)
                        {
                            if(t.getRect(0, 0).intersects(new Rectangle(randomX, randomY, 32, 32))) collision ++;
                        }
                        if(collision == 0)
                            canSpawn = true;
                    }
                    else canSpawn = false;
                    collision = 0;
                }
                while(!canSpawn);
                if((!russiantanksoundplaying) && !(startmusic.playing()))
                {
                    russianenginesound.loop();
                    russiantanksoundplaying = true;
                }
                ai.spawn(randomX, randomY);
                ai.addCurrent();
                ai.addSpawned();
                counters.tankSpawned();
                lasttimetanksspawned = System.currentTimeMillis();
            }
            else if(ai.getCurrent() == 0)
            {
                System.out.println("Stopped russian sound "+ai.getClass() +ai.getSpawned());
                russianenginesound.stop();
                russiantanksoundplaying = false;
            }
            
            
            
            for(Tank t : neutrals)
            {
                if(t.getMoveCoolDown() <= 0)
                {
                Random moveGenerator = new Random();
                t.setRandomMove(moveGenerator.nextInt(4));
                t.setMoveCoolDown(150);
                }
                else
                {
                    switch(t.getRandomMove())
                    {
                        case 0 :
                        {
                            t.rotate(0);
                            if(!terrain.checkCollision(t.getRect(delta, 0)))
                            {
                                t.changePosX(delta);
                            }
                            else
                                t.setMoveCoolDown(0);
                            t.decreaseMoveCoolDown(delta*0.1f);
                        }
                        break;
                        case 1 :
                        {
                             t.rotate(180);
                            if(!terrain.checkCollision(t.getRect(-delta, 0)))
                            {
                                t.changePosX(-delta);
                            }
                            else
                                t.setMoveCoolDown(0);
                            t.decreaseMoveCoolDown(delta*0.1f);
                        }
                        break;
                        case 2 :
                        {
                            t.rotate(270);
                            if(!terrain.checkCollision(t.getRect(0, -delta)))
                            {
                                t.changePosY(-delta);
                            }
                            else
                                t.setMoveCoolDown(0);
                            t.decreaseMoveCoolDown(delta*0.1f);
                        }
                        break;
                        case 3 :
                        {
                            t.rotate(90);
                            if(!terrain.checkCollision(t.getRect(0, delta)))
                            {
                                t.changePosY(delta);
                            }
                            else
                                t.setMoveCoolDown(0);
                            t.decreaseMoveCoolDown(delta*0.1f);                        
                        }
                        break;
                            
                    }
                }
                if(t.getShootCoolDown() <= 0)
                {
                    Random fireGenerator = new Random();
                    
                    t.shoot(fireGenerator.nextInt(50));
                }
                else t.decreaseShootCoolDown(delta * 0.1f);
            }

            for(Iterator<Bullet> iterator = objects.iterator(); iterator.hasNext(); )
            {
                Bullet b = iterator.next();
                if(!terrain.checkCollision(b.getRect(delta)) && 
                        b.getRect(delta).getX() < margin+416 &&
                        b.getRect(delta).getX() > margin && 
                        b.getRect(delta).getY() < margin+416 && 
                        b.getRect(delta).getY() > margin)
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
        else if(isgameover)
        {
            counters.drawGameOver();
        }
        else
        {
            g.fill(battlefieldbackground);
                g.setColor(org.newdawn.slick.Color.black);


            tank.draw();
            
            for(Tank t : neutrals)
            {
                t.draw();
            }
            
            for(Bullet b : objects)
            {
                b.draw();
            }
            terrain.draw();
            counters.drawCounters();
            

        }
    }
    
    public static void addObject(Bullet b)
    {
        objects.add(b);
    }
    
    public static void addNeutralTank(Tank t)
    {
        neutrals.add(t);
    }
}
