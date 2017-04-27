package behavior;

import engine.Engine;
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
        if(!Engine.monster.isFollowingPath() || !Engine.monster.getPathFollower().reachedTarget)
            Engine.monster.seekPlayer();
        Engine.monster.getPathFollower().followPath();
        Engine.monster.update();
    }

    public void shootTarget(){
        Engine.monster.shootAtPlayer();
        Engine.monster.update();
    }

    @Override
    public void addChild(AbstractNode child) {
        this.ChildNodes.add(child);
    }

}
