package behavior;

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
        while(!this.Monster.playerVisible())
            this.ChildNodes.get(0).perform();
        return true;
    }

    //getters and setters

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }


    public void setMonster(Monster monster){
        this.Monster = monster;
    }

    public Monster getMonster(){
        return this.Monster;
    }
}
