/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlecityput;

import java.net.MalformedURLException;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Maksym
 */
public class Player extends Tank{
    private int key_l,key_r,key_u,key_d,key_s;
    
    public Player (int id, int r, int l, int u, int d, int shoot) throws SlickException, MalformedURLException
    {
        super(id);
        key_l=l;
        key_r=r;
        key_u=u;
        key_d=d;
        key_s=shoot;
    }

    public int get_l()
    {
        return key_l;
    }
    public int get_r()
    {
        return key_r;
    }
    public int get_u()
    {
        return key_u;
    }
    public int get_d()
    {
        return key_d;
    }
    public int get_s()
    {
        return key_s;
    }
    
}
