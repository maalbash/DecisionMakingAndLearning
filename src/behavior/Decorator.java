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

    Vision checkLOS = (PVector a, PVector b) -> Utility.hasLOS(a,b);


    @Override
    public void addChild(AbstractNode abstractNode) {
        super.addChild(abstractNode);
    }

    @Override
    public boolean perform() {
        while(!checkLOS.foo(this.Monster.getPosition(),this.player.getPosition()))
            this.ChildNodes.get(0).perform();
        return true;
    }

    //getters and setters

    public Vision getCheckLOS() {
        return checkLOS;
    }

    public void setCheckLOS(Vision checkLOS) {
        this.checkLOS = checkLOS;
    }

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
