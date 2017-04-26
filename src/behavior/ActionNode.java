package behavior;

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
                somethingWasPerformed = true;
                break;
            case SHOOT:
                shootTarget();
                somethingWasPerformed = true;
                break;
        }
        return somethingWasPerformed;
    }

    public void seekTarget()
    {

    }

    public void shootTarget(){

    }
}
