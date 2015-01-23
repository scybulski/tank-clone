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
    private Image countersFrame, russianTankIndicator, stageText, gameover,
            num_bg[], num_wb[], num_ob[];


    public static int TILESIZE;
    
    public int russianTanksLeft, lives1P, lives2P, level, points1P, points2P,
            russianDestroyed1P, russianDestroyed2P, opponentDestroyed1P, opponentDestroyed2P;

    public Counters() throws SlickException
    {
        countersFrame = new Image("surowce/counters.png");
        russianTankIndicator = new Image("surowce/russiantankindicator.png");
        stageText = new Image("surowce/stage.png");
        gameover = new Image("surowce/gameoversummary.png");
        
        num_bg = new Image[10];
        num_wb = new Image[10];
        num_ob = new Image[10];
        for(int i = 0; i < 10; i++)
        {
            num_bg[i] = new Image("surowce/numbers_black_gray/"+i+".png");
            num_wb[i] = new Image("surowce/numbers_white_black/"+i+".png");
            num_ob[i] = new Image("surowce/numbers_orange_black/"+i+".png");
        }
        
        russianTanks = new ArrayList<Rectangle>();
    }
    
    private void showNumber_blackgray(int number, int x, int y)  //cooordinates indicate top-right corner
    {
        if(number < 0)
            number = 0;  //avoid crash on below zero
        if(number == 0)
        {
            num_bg[0].draw(x-16,y);
        }
        else
        {
            while(number != 0)
            {
                num_bg[number % 10].draw(x-16,y);
                x -= 16;
                number /= 10;
            }            
        }
    }
    
    private void showNumber_whiteblack(int number, int x, int y)  //cooordinates indicate top-right corner
    {
        if(number < 0)
            number = 0;  //avoid crash on below zero
        if(number == 0)
        {
            num_wb[0].draw(x-16,y);
        }
        else
        {
            while(number != 0)
            {
                num_wb[number % 10].draw(x-16,y);
                x -= 16;
                number /= 10;
            }            
        }
    }
    
    private void showNumber_orangeblack(int number, int x, int y)  //cooordinates indicate top-right corner
    {
        if(number < 0)
            number = 0;  //avoid crash on below zero
        if(number == 0)
        {
            num_ob[0].draw(x-16,y);
        }
        else
        {
            while(number != 0)
            {
                num_ob[number % 10].draw(x-16,y);
                x -= 16;
                number /= 10;
            }            
        }
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
        showNumber_blackgray(lives1P, margin+416+48, margin+256);
        showNumber_blackgray(lives2P, margin+416+48, margin+304);
        showNumber_blackgray(level, margin+416+48, margin+366);
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
        showNumber_blackgray(level, 310, 224);
    }
    
    public void drawGameOver()
    {
        gameover.draw();
        showNumber_whiteblack(russianDestroyed1P, 224,176);
        showNumber_whiteblack(russianDestroyed1P*100, 112,176);
        showNumber_whiteblack(russianDestroyed2P, 320,176);
        showNumber_whiteblack(russianDestroyed2P*100, 400,176);
        showNumber_whiteblack(opponentDestroyed1P, 224,224);
        showNumber_whiteblack(opponentDestroyed1P*500, 112,224);
        showNumber_whiteblack(opponentDestroyed2P, 320,224);
        showNumber_whiteblack(opponentDestroyed2P*500, 400,224);
        showNumber_whiteblack(opponentDestroyed1P+russianDestroyed1P,224,256);
        showNumber_orangeblack(russianDestroyed1P*100+opponentDestroyed1P*500,175,128);
        showNumber_orangeblack(russianDestroyed2P*100+opponentDestroyed2P*500,464,128);
    }
}
