package behavior;

import objects.GameObject;
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


    Vision vision = new Vision() {
        @Override
        public boolean foo(PVector x, PVector y) {
            return Utility.hasLOS(x,y);
        }
    };

    @Override
    public void addChild(AbstractNode abstractNode) {
        super.addChild(abstractNode);
    }

    @Override
    public boolean perform() {
        while(!vision.foo(this.Monster.getPosition(),this.player.getPosition()))
            this.ChildNodes.get(0).perform();
        return true;
    }

    //getters and setters

    public void setPlayer(GameObject player){
        this.player = player;
    }

    public GameObject getPlayer(){
        return this.player;
    }


    public void setMonster(GameObject monster){
        this.Monster = monster;
    }

    public GameObject getMonster(){
        return this.Monster;
    }
}
