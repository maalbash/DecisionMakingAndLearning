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



    @Override
    public boolean perform() {
        boolean somethingWasPerformed = false;
        switch (actiontype){
            case SEEK:
                seekTarget();
//                System.out.println("SEEKING");
                somethingWasPerformed = true;
                break;
            case SHOOT:
                shootTarget();
//                System.out.println("SHOOTING");
                somethingWasPerformed = true;
                break;
        }
        return somethingWasPerformed;
    }


    public void seekTarget() {
        if(!this.Monster.isFollowingPath() || !this.Monster.getPathFollower().reachedTarget)
            this.Monster.seekPlayer();
        this.Monster.getPathFollower().followPath();
        this.Monster.update();
    }

    public void shootTarget(){
        this.Monster.shootAtPlayer();
        this.Monster.update();
    }

    @Override
    public void addChild(AbstractNode child) {
        this.ChildNodes.add(child);
    }

    @Override
    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void setMonster(Monster monster){
        this.Monster = monster;
    }

    @Override
    public Player getPlayer(){
        return this.player;
    }

    @Override
    public Monster getMonster(){
        return this.Monster;
    }
}
