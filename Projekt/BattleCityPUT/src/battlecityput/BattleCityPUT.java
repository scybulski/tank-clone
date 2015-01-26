package battlecityput;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//import javafx.scene.paint.Color;
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
//import sun.applet.Main;


public class BattleCityPUT extends BasicGame
{
    private Terrain terrain;
    private Tank tank1P, tank2P;
    private Ai ai;
    private static ArrayList<GameObject> objects;
    private static ArrayList<Tank> neutrals;
    private static ArrayList<Player> players;
    private static ArrayList<Robot> robots;
    private static ArrayList<Tank> tanks;
    private static boolean gameOver = false;
    
    private Counters counters;
    private org.newdawn.slick.geom.Rectangle battlefieldbackground, grayforeground;
    private Music startmusic, endmusic;
    private boolean playerenginesoundplaying, levelchooser;//,russiantanksoundplaying
    private int isPlayerMoving, tank1PImmune, tank2PImmune;
    private long lasttimetanksspawned;
    
    public final static Integer margin = 32;
    private Clip startmusicclip;
    private AudioInputStream inputStream;
    private AudioClip playerenginesound, playerExploded, russianExploded;//, russianenginesound;
    Random posGenerator = new Random();
    
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
        
        counters = new Counters();
        lasttimetanksspawned = System.currentTimeMillis();
        
        
        battlefieldbackground = new org.newdawn.slick.geom.Rectangle(margin, margin, 416, 416);
        startmusic = new Music("surowce/start.ogg");

        URL url;
        try {
            playerenginesound = Applet.newAudioClip(new URL("file:surowce/playerengine.wav"));
            playerExploded = Applet.newAudioClip(new URL("file:surowce/playerdestroyed.wav"));
            russianExploded = Applet.newAudioClip(new URL("file:surowce/russiantankdestroyed.wav"));
            //russianenginesound = Applet.newAudioClip(new URL("file:surowce/russiantank.wav"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // lista obiektow do sprawdzania kolizji
        objects = new ArrayList<>();
        neutrals = new ArrayList<>();
        
        
        
        try {
            // lista czolgow graczy
            players = new ArrayList<>();
            players.add(new Player(1,Input.KEY_RIGHT,Input.KEY_LEFT,Input.KEY_UP,Input.KEY_DOWN,Input.KEY_RCONTROL));
            //players.add(new Player(2,Input.KEY_D,Input.KEY_A,Input.KEY_W,Input.KEY_S,Input.KEY_SPACE));
            
            // lista czolgow zawansowanych AI - Robot
            robots = new ArrayList<>();
            //robots.add(new Robot_Droid(new Tank(1)));
            robots.add(new Robot_Minim(new Tank(2)));
        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tanks = new ArrayList<>();
        tanks.add(players.get(0));
        tanks.add(robots.get(0).get_tank());
        //tanks.add(robots.get(1).get_tank());
        tank1P=tanks.get(0);
        //tank1P = robots.get(0).get_tank();
        //tank2P=robots.get(1).get_tank();
        tank2P=tanks.get(1);
        
        counters = new Counters();
        counters.startGame();
}
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        if(!gameOver)
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
                for(Player tank: players)
                {
                    if(input.isKeyDown(tank.get_r()))
                    {
                        m_right(tank,delta);
                    }
                    else if(input.isKeyDown(tank.get_l()))
                    {
                        m_left(tank,delta);
                    }
                    else if(input.isKeyDown(tank.get_u()))
                    {
                        m_up(tank,delta);
                    }
                    else if(input.isKeyDown(tank.get_d()))
                    {
                        m_down(tank,delta);
                    }
                    else
                        isPlayerMoving--;

                    if(input.isKeyPressed(tank.get_s())) 
                    {
                        shoot(tank);
                    }
                }


                for(Robot robot: robots)
                {
                    int action=robot.moveTank(terrain.get_blocks(), objects, neutrals, tanks, delta);
                    Tank tank=robot.get_tank();
                    if(action % 5==1)
                    {
                        m_right(tank,delta);
                    }
                    else if(action % 5==2)
                    {
                        m_left(tank,delta);
                    }
                    else if(action % 5==3)
                    {
                        m_up(tank,delta);
                    }
                    else if(action % 5==4)
                    {
                        m_down(tank,delta);
                    }
                    else
                        isPlayerMoving--;

                    if(action>4) 
                    {
                        shoot(tank);
                    }
                }

                
                // NEUTRALE
                if(ai.getCurrent() < 8 && counters.getRussianTanksLeft() > 0 && System.currentTimeMillis() > lasttimetanksspawned + 3579)
                {
                    do
                    {
                        randomX = posGenerator.nextInt(353) + margin;
                        randomY = posGenerator.nextInt(353) + margin;
                        if(!terrain.checkCollision(new Rectangle(randomX, randomY, 32, 32)))
                        {
                            for(Tank t : neutrals)
                            {
                                if(t.getRect(0, 0).intersects(new Rectangle(randomX, randomY, 32, 32))) collision ++;
                            }
                            for(Tank t : tanks)
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
                    try
                    {
                        ai.spawn(randomX, randomY);
                    }
                    catch (MalformedURLException ex)
                    {
                        Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ai.addCurrent();
                    ai.addSpawned();
                    counters.tankSpawned();
                    lasttimetanksspawned = System.currentTimeMillis();
                }
//                else if(ai.getCurrent() == 0)
  //              {
    //                russianenginesound.stop();
     //               russiantanksoundplaying = false;
      //          }


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
                                if(!terrain.checkCollision(t.getRect(delta, 0)) &&
                                        !BattleCityPUT.checkTanksCollisions(t, t.getRect(2*delta, 0)))
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
                                if(!terrain.checkCollision(t.getRect(-delta, 0)) &&
                                        !BattleCityPUT.checkTanksCollisions(t, t.getRect(-2*delta, 0)))
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
                                if(!terrain.checkCollision(t.getRect(0, -delta)) &&
                                        !BattleCityPUT.checkTanksCollisions(t, t.getRect(0, -2*delta)))
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
                                if(!terrain.checkCollision(t.getRect(0, delta)) &&
                                        !BattleCityPUT.checkTanksCollisions(t, t.getRect(0, 2*delta)))
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

                        t.shoot(fireGenerator.nextInt(50),false);  //false as russ tanks are silent
                    }
                    else t.decreaseShootCoolDown(delta * 0.1f);
                }

                // kolizje(tylko dla pociskow)
                ArrayList toRemove = new ArrayList();
                for(Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext(); )
                {
                    GameObject obj = iterator.next();
                    
                    if(terrain.checkCollision(obj.getHitBox()) || 
                            obj.getHitBox().getX() >= margin+416 ||
                            obj.getHitBox().getX() <= margin ||
                            obj.getHitBox().getY() >= margin+416 ||
                            obj.getHitBox().getY() <= margin)
                    {
                        obj.handleCollision();
                        toRemove.add(obj);
                        //iterator.remove();
                        continue;
                    }
                    else
                    for(Iterator<GameObject> iterator2 = objects.iterator(); iterator2.hasNext();)
                    {
                        GameObject obj2 = iterator2.next();
                        
                        if((obj != obj2 ) && (obj.getHitBox().intersects(obj2.getHitBox())))
                        {
                            obj.handleCollision();
                            obj2.handleCollision();
                            toRemove.add(obj);
                            toRemove.add(obj2);
                        }
                    }
                    // kolizja z neutralami
                    boolean collides = false;
                    for(Iterator<Tank> neutralIterator = neutrals.iterator(); neutralIterator.hasNext();)
                    {
                        Tank neutral = neutralIterator.next();

                        if(obj.collides(neutral))
                        {
                            //if((obj.getParentTank().equals(tank1P)) || (obj.getParentTank().equals(tank2P)))
                            if(!obj.getParentTank().getIsNeutral())
                            {
                               neutral.decreaseLives();                                
                            }
                            if(neutral.getLives() <= 0)
                            {
                                //usuniecie czolgu
                                if(!neutral.getIsDestroyed())
                                {
                                    neutral.setIsDestroyed(true);
                                    if (obj.getParentTank() == tank1P)
                                        counters.update1PDestroyedRussian();
                                    else if (obj.getParentTank() == tank2P)
                                        counters.update2PDestroyedRussian();
                                }
                                neutralIterator.remove();
                                ai.removeCurrent();
                                if((ai.getCurrent() == 0) && counters.getRussianTanksLeft() == 0)
                                {
                                    gameOver = true;
                                }
                            }
                            // ROZROZNIANIE PLAYEROW(jedna metoda do wszystkich by styknela)
                            obj.handleCollision();
                            collides = true;
                            break;
                        }
                    }

                    if(!collides)
                    {
                        //Robot
                        for(Tank t:tanks)
                        {
                            if(obj.collides(t))
                            {
//                                tank1PImmune = 400;
//                                System.out.println("Took life 1P");
          //                      do
//                                {
  //                                  randomX = posGenerator.nextInt(353) + margin;
    //                                randomY = posGenerator.nextInt(353) + margin;
      //                          }
        //                        while(tank.getRect(0, 0).intersects(new Rectangle(randomX, randomY, 32, 32))); collision ++;
                                if(!(t == tank1P && tank1PImmune > 1) &&
                                    !(t == tank2P && tank2PImmune > 1))
                                {
                                    playerExploded.play();
                                    t.setPosX(randomX);
                                    t.setPosY(randomY);
                                    if(t == tank1P)  //(playerTank.getLives() <= 0)
                                    {
                                        tank1PImmune = 400;
                                        if(obj.parentTank == tank2P)
                                            counters.update2PDestroyedOpponent();
                                         if(counters.takeLive1P() == 0)
                                         {
                                            gameOver = true;
                                         }
                                    }
                                    else if(t == tank2P)
                                    {
                                        if(obj.parentTank == tank1P)
                                            counters.update1PDestroyedOpponent();
                                        tank2PImmune = 400;
                                        if(counters.takeLive2P() == 0)
                                        {
                                            gameOver = true;
                                        }
                                    }
                                }
                                obj.handleCollision();
                                //iterator.remove();
                                collides = true;
                                break;
                            }
                        }
                    }

                    // porusza tylko pociski
                    if(!collides)
                        obj.move(delta);
                }
                objects.removeAll(toRemove);


                if((isPlayerMoving > 0) && !gameOver)
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
    }
    
    public void shoot(Tank tank) throws SlickException
    {
        tank.shoot(0, !startmusic.playing());
    }
    
    public void m_right(Tank tank, int delta)
    {
        tank.rotate(0);
        if(!terrain.checkCollision(tank.getRect(delta, 0)) &&
                !BattleCityPUT.checkTanksCollisions(tank, tank.getRect(delta, 0)))
        {
            //tank.setDirection(1);
            tank.changePosX(delta);
        }
        isPlayerMoving = 11;
    }
    
    public void m_left(Tank tank, int delta)
    {
        tank.rotate(180);
        if(!terrain.checkCollision(tank.getRect(-delta, 0)) &&
                !BattleCityPUT.checkTanksCollisions(tank, tank.getRect(-delta, 0)))
        {
            tank.changePosX(-delta);
        }
        isPlayerMoving = 11;
    }
    
    public void m_up(Tank tank, int delta)
    {
        tank.rotate(270);
        if(!terrain.checkCollision(tank.getRect(0, -delta)) &&
                !BattleCityPUT.checkTanksCollisions(tank, tank.getRect(0, -delta)))
        {
            tank.changePosY(-delta);
        }
        isPlayerMoving = 11;
    }
    
    public void m_down(Tank tank, int delta)
    {
        tank.rotate(90);
        if(!terrain.checkCollision(tank.getRect(0, delta)) &&
                !BattleCityPUT.checkTanksCollisions(tank, tank.getRect(0, delta)))
        {
            tank.changePosY(delta);
        }
        isPlayerMoving = 11;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.setBackground(org.newdawn.slick.Color.decode("#636363"));  //need2use Color from slick library
        if(levelchooser)
        {
            counters.drawLevelChooser();
        }
        else if(!gameOver)
        {
            g.fill(battlefieldbackground);
                g.setColor(org.newdawn.slick.Color.black);

            for(Player t: players)
            {
                t.draw();
            }
            
            for(Robot robot:robots)            
            {
                robot.get_tank().draw();
            }
            
            for(Tank t : neutrals)
            {
                t.draw();
            }
            
            for(Iterator<GameObject> iter = objects.iterator(); iter.hasNext();) //(GameObject obj : objects)
            {
                GameObject obj = iter.next();
                if(!obj.draw())
                    iter.remove();
            }
            if(tank1PImmune > 0)
            {
                tank1PImmune--;
                tank1P.drawImmune();
            }
            if(tank2PImmune > 0)
            {
                tank2PImmune--;
                tank2P.drawImmune();
            }
            terrain.draw();
            counters.drawCounters();
        }
        else
        {
            counters.drawGameOver();
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
    
    // kolizja miedzy danym czolgiem(gracz/neutral) a neutralami
    public static boolean checkTanksCollisions(Tank t, Rectangle tankRect)
    {
        for(Iterator<Tank> iterator = neutrals.iterator(); iterator.hasNext();)
        {
            Tank neutral = iterator.next();
            
            // gdy iterator nie wskazuje na przekazywany czolg
            if(!t.equals(neutral))
                if(tankRect.intersects(neutral.getHitBox()))
                {
                    return true;
                }
        }
        
        for(Robot robot:robots) 
        {            
            if(!t.equals(robot.get_tank()))
                if(tankRect.intersects(robot.get_tank().getHitBox()))
                {
                    return true;
                }
        }
        
        for(Iterator<Player> iterator = players.iterator(); iterator.hasNext();)
        {
            Tank playerTank = iterator.next();
            
            if(!t.equals(playerTank))
                if(tankRect.intersects(playerTank.getHitBox()))
                {
                    return true;
                }
        }
        return false;
    }
}
