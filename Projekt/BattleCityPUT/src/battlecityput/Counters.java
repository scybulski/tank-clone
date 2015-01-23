/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlecityput;

import static battlecityput.BattleCityPUT.margin;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author szymon
 */
class Counters {
        
    private ArrayList<Rectangle> russianTanks;

    private Rectangle chooser;
    private Image countersFrame, russianTankIndicator, stageText, gameover;
    private Image num[];


    public static int TILESIZE;
    
    public int russianTanksLeft, lives1P, lives2P, level, points1P, points2P,
            russianDestroyed1P, russianDestroyed2P, opponentDestroyed1P, opponentDestroyed2P;

    
    private void showNumber(int number, int x, int y)  //cooordinates indicate top-right corner
    {
        if(number < 0)
            number = 0;  //avoid crash on below zero
        if(number == 0)
        {
            num[0].draw(x-16,y);
        }
        else
        {
            while(number != 0)
            {
                num[number % 10].draw(x-16,y);
                x -= 16;
                number /= 10;
            }            
        }
    }

    public Counters() throws SlickException
    {
        countersFrame = new Image("surowce/counters.png");
        russianTankIndicator = new Image("surowce/russiantankindicator.png");
        stageText = new Image("surowce/stage.png");
        gameover = new Image("surowce/gameoversummary.png");
        
        num = new Image[10];
        for(int i = 0; i < 10; i++)
        {
            num[i] = new Image("surowce/"+i+".png");
        }
        
        russianTanks = new ArrayList<Rectangle>();
    }
        
    public void startGame()
    {
        System.out.println(" Start Game");
        countersFrame.draw(margin+416+32, margin);
        russianTanksLeft=21;
        lives1P = 2;
        lives2P = 2;
        points1P = 0;
        points2P = 0;
        level = 1;
        
        
    }
    
    public void drawCounters()
    {
        countersFrame.draw(margin+416, 0);
        for(int i = 0; i < 10;i++)
            for(int j = 0; j < 2;j++)
            {
                if((2*i+j) < russianTanksLeft)
                {
                    russianTankIndicator.draw(margin+416+16+j*16, margin+16+16*i);
                }
                else
                    break;
            }
        showNumber(lives1P, margin+416+48, margin+256);
        showNumber(lives2P, margin+416+48, margin+304);
        showNumber(level, margin+416+48, margin+366);
    }

    public int tankSpawned()  //returns number of tanks to spawn
            //and decreases number of tanks shown in right panel
    {
        russianTanksLeft--;
        
        return russianTanksLeft;
    }
    
    public int getRussianTanksLeft()
    {
        return russianTanksLeft;
    }
    
    public int increaseLevelNumber()
    {
        System.out.println("Level up");
        File f = new File("surowce/stages/"+(level+1)+".tmx");
        if(f.exists() && !f.isDirectory())
        {
            level++;
        }
        return level;
    }

    public int decreaseLevelNumber()
    {
        if(level > 1)
            level--;
        return level;
    }
    
    public void setLevelNumber(int newNumber)
    {
        if(level < 0)
            level = 0;
        level = newNumber;
    }
    
    public int getLevelNuber()
    {
        return level;
    }
    
    public int getLives1P()
    {
        return lives1P;
    }
    
    public int setLives1P(int newLives)
    {
        lives1P = newLives;
        if(lives1P < 0)
            lives1P = 0;
        return lives1P;
    }
    
    public int takeLive1P()
    {
        lives1P--;
        if(lives1P < 0)
            lives1P = 0;
        return lives1P;
    }
    
    public int getLives2P()
    {
        return lives2P;
    }
    
    public int setLives2P(int newLives)
    {
        lives2P = newLives;
        if(lives2P < 0)
            lives2P = 0;
        return lives2P;
    }
    
    public int takeLive2P()
    {
        lives2P--;
        if(lives2P < 0)
            lives2P = 0;
        return lives2P;
    }
    
    public int update1PDestroyedRussian()
    {
        russianDestroyed1P++;
        return russianDestroyed1P;
    }
    
    public int update1PDestroyedOpponent()
    {
        opponentDestroyed1P++;
        return opponentDestroyed1P;
    }

    public int update2PDestroyedRussian()
    {
        russianDestroyed2P++;
        return russianDestroyed2P;
    }
    
    public int update2PDestroyedOpponent()
    {
        opponentDestroyed2P++;
        return opponentDestroyed2P;
    }

    public void drawLevelChooser()
    {
        stageText.draw(192, 224);
        showNumber(level, 310, 224);
    }
    
    public void drawGameOver()
    {
        gameover.draw();
    }
}
