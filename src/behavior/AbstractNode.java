package behavior;

import objects.*;
import utility.GameConstants;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by mohz2 on 4/25/2017.
 */
public abstract class AbstractNode {
    protected Utility.NODETYPE nodetype;
    protected Monster Monster;
    protected Player player;
    protected ArrayList<AbstractNode> ChildNodes;
    public abstract boolean perform();
    public abstract void addChild(AbstractNode child);
    public abstract void setMonster(Monster monster);
    public abstract void setPlayer(Player player);
    public abstract Monster getMonster();
    public abstract Player getPlayer();
}
