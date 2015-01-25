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
//import sun.applet.Main;


public class BattleCityPUT extends BasicGame
{
    private Terrain terrain;
    private Tank tank1P, tank2P;
    private Ai ai;
    private static ArrayList<GameObject> objects;
    private static ArrayList<Tank> neutrals;
    private static ArrayList<Tank> players;
    private static boolean gameOver = false;
    
    private Counters counters;
    private org.newdawn.slick.geom.Rectangle battlefieldbackground, grayforeground;
    private Music startmusic, endmusic;
    private boolean playerenginesoundplaying, levelchooser;//,russiantanksoundplaying
    private int isPlayerMoving, tank1PTillRespawn, tank2PTillRespawn;
    private long lasttimetanksspawned;
    
    public final static Integer margin = 32;
    private Clip startmusicclip;
    private AudioInputStream inputStream;
    private AudioClip playerenginesound;//, russianenginesound;
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
        try {
            tank1P = new Tank(1);
            tank2P = new Tank(2);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
        counters = new Counters();
        lasttimetanksspawned = System.currentTimeMillis();
        
        
        battlefieldbackground = new org.newdawn.slick.geom.Rectangle(margin, margin, 416, 416);
        startmusic = new Music("surowce/start.ogg");

        URL url;
        try {
            playerenginesound = Applet.newAudioClip(new URL("file:surowce/playerengine.wav"));
            //russianenginesound = Applet.newAudioClip(new URL("file:surowce/russiantank.wav"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(BattleCityPUT.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // lista obiektow do sprawdzania kolizji
        objects = new ArrayList<>();
        neutrals = new ArrayList<>();
        // lista czolgow graczy
        players = new ArrayList<>();
        players.add(tank1P);
        players.add(tank2P);
        
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
                if(input.isKeyDown(Input.KEY_RIGHT))
                {
                    tank1P.rotate(0);
                    if(!terrain.checkCollision(tank1P.getRect(delta, 0)) &&
                            !BattleCityPUT.checkTanksCollisions(tank1P, tank1P.getRect(2*delta, 0)))
                    {
                        //tank.setDirection(1);
                        tank1P.changePosX(delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_LEFT))
                {
                    tank1P.rotate(180);
                    if(!terrain.checkCollision(tank1P.getRect(-delta, 0)) &&
                            !BattleCityPUT.checkTanksCollisions(tank1P, tank1P.getRect(-2*delta, 0)))
                    {
                        tank1P.changePosX(-delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_UP))
                {
                    tank1P.rotate(270);
                    if(!terrain.checkCollision(tank1P.getRect(0, -delta)) &&
                            !BattleCityPUT.checkTanksCollisions(tank1P, tank1P.getRect(0, -2*delta)))
                    {
                        tank1P.changePosY(-delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_DOWN))
                {
                    tank1P.rotate(90);
                    if(!terrain.checkCollision(tank1P.getRect(0, delta)) &&
                            !BattleCityPUT.checkTanksCollisions(tank1P, tank1P.getRect(0, 2*delta)))
                    {
                        tank1P.changePosY(delta);
                    }
                    isPlayerMoving = 11;
                }
                else
                    isPlayerMoving--;

                if(input.isKeyPressed(Input.KEY_RCONTROL)) 
                {
                    //tank.shoot(0);
                    tank1P.shoot(0, !startmusic.playing());
                }

//PLAYER 2
                
                if(input.isKeyDown(Input.KEY_D))
                {
                    tank2P.rotate(0);
                    if(!terrain.checkCollision(tank2P.getRect(delta, 0)) &&
                            !BattleCityPUT.checkTanksCollisions(tank2P, tank2P.getRect(2*delta, 0)))
                    {
                        //tank.setDirection(1);
                        tank2P.changePosX(delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_A))
                {
                    tank2P.rotate(180);
                    if(!terrain.checkCollision(tank2P.getRect(-delta, 0)) &&
                            !BattleCityPUT.checkTanksCollisions(tank2P, tank2P.getRect(-2*delta, 0)))
                    {
                        tank2P.changePosX(-delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_W))
                {
                    tank2P.rotate(270);
                    if(!terrain.checkCollision(tank2P.getRect(0, -delta)) &&
                            !BattleCityPUT.checkTanksCollisions(tank2P, tank2P.getRect(0, -2*delta)))
                    {
                        tank2P.changePosY(-delta);
                    }
                    isPlayerMoving = 11;
                }
                else if(input.isKeyDown(Input.KEY_S))
                {
                    tank2P.rotate(90);
                    if(!terrain.checkCollision(tank2P.getRect(0, delta)) &&
                            !BattleCityPUT.checkTanksCollisions(tank2P, tank2P.getRect(0, 2*delta)))
                    {
                        tank2P.changePosY(delta);
                    }
                    isPlayerMoving = 11;
                }
                else
                    isPlayerMoving--;

                if(input.isKeyPressed(Input.KEY_F)) 
                {
                    //tank.shoot(0);
                    tank2P.shoot(0, !startmusic.playing());
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
                            if((obj.getParentTank() == tank1P) || (obj.getParentTank() == tank2P))
                            {
                               neutral.decreaseLives();                                
                            }
                            if(neutral.getLives() <= 0)
                            {
                                //usuniecie czolgu
                                if(!neutral.getIsDestroyed())
                                {
                                    neutral.setIsDestroyed(true);
                                    counters.update1PDestroyedRussian();
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

                    if(!collides && tank1PTillRespawn < 1)
                    {
                        for(Iterator<Tank> playerIterator = players.iterator(); playerIterator.hasNext(); )
                        {
                            Tank playerTank = playerIterator.next();
                            if(obj.collides(playerTank))
                            {
                                tank1PTillRespawn = 400;
                                System.out.println("Took life 1P");
          //                      do
//                                {
  //                                  randomX = posGenerator.nextInt(353) + margin;
    //                                randomY = posGenerator.nextInt(353) + margin;
      //                          }
        //                        while(tank.getRect(0, 0).intersects(new Rectangle(randomX, randomY, 32, 32))); collision ++;
                                tank1P.setPosX(randomX);
                                tank1P.setPosY(randomY);

                                //playerTank.decreaseLives();
                                // ROZROZNIANIE PLAYEROW JAK JUZ BEDZIE WIECEJ
                                if(counters.takeLive1P() == 0)  //(playerTank.getLives() <= 0)
                                {
                                    //koniec
                                    // jakos to zrobic
//                                    if(bullet.parentTank C players)
//                                    counters.update2PDestroyedOpponent();
                                    gameOver = true;
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

            //tank.draw();
            for(Tank p : players)
            {
                p.draw();
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
            if(tank1PTillRespawn > 0)
            {
                System.out.println("DRAW IMMUNE");
                tank1PTillRespawn--;
                tank1P.drawImmune();
            }
            if(tank2PTillRespawn > 0)
            {
                System.out.println("DRAW IMMUNE");
                tank2PTillRespawn--;
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
        
        for(Iterator<Tank> iterator = players.iterator(); iterator.hasNext();)
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
