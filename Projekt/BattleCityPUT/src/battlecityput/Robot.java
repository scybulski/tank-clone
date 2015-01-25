package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public interface Robot {

    int moveTank (ArrayList<Rectangle> blocks,ArrayList<Rectangle> obj,ArrayList<Rectangle> neut,ArrayList<Rectangle> enem);
    Tank get_tank();
}