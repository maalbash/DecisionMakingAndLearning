package behavior;

import objects.*;
import utility.Utility;

/**
 * Created by mohz2 on 4/26/2017.
 */
public class ActionNode extends AbstractNode {

    private Utility.ACTIONTYPE actiontype;


    public ActionNode(Utility.ACTIONTYPE actiontype) {
        this.actiontype = actiontype;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setMonster(Monster monster){
        this.Monster = monster;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Monster getMonster(){
        return this.Monster;
    }

    @Override
    public boolean perform() {
        boolean somethingWasPerformed = false;
        switch (actiontype){
            case SEEK:
                seekTarget();
                somethingWasPerformed = true;
                break;
            case SHOOT:
                shootTarget();
                somethingWasPerformed = true;
                break;
        }
        return somethingWasPerformed;
    }

    @Override
    public void addChild(AbstractNode child) {
        this.ChildNodes.add(child);
    }

    public void seekTarget() {
        this.Monster.seekPlayer();
    }

    public void shootTarget(){
        this.Monster.shootAtPlayer();
    }
}
