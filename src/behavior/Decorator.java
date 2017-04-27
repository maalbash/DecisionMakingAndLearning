package behavior;

import engine.Engine;
import objects.GameObject;
import objects.Monster;
import objects.Player;
import processing.core.PVector;
import utility.*;
/**
 * Created by mohz2 on 4/26/2017.
 */
public class Decorator extends CompositeNode {

    public Decorator() {
        super();
    }

    //Vision checkLOS = (PVector a, PVector b) -> Utility.hasLOS(a,b);


    @Override
    public void addChild(AbstractNode abstractNode) {
        super.addChild(abstractNode);
    }

    @Override
    public boolean perform() {
        if(!Engine.monster.playerVisible()) {
            this.ChildNodes.get(0).perform();
            return false;
        }
        Engine.monster.setFollowingPath(false);
        return true;
    }

}
