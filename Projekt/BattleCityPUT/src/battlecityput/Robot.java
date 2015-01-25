package battlecityput;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Maksym
 */
public interface Robot {
    int moveTank (ArrayList<Rectangle> blocks,ArrayList<GameObject> objects,ArrayList<Tank> neutrals,ArrayList<Player> players, ArrayList<Robot> robots);
    Tank get_tank();
}