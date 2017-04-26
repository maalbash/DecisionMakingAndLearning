package behavior;

import objects.GameObject;
import utility.GameConstants;
import utility.Utility;

/**
 * Created by mohz2 on 4/25/2017.
 */
public abstract class AbstractNode {
    protected Utility.NODETYPE nodetype;
    protected GameObject Monster, player;
    public abstract boolean perform();
}
