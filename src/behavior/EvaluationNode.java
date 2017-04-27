package behavior;

import engine.Engine;

/**
 * Created by mohz2 on 4/26/2017.
 */
public class EvaluationNode extends AbstractNode {

    private boolean conditionToCheck;

    public EvaluationNode() {
    }


    public boolean isConditionToCheck() {
        return conditionToCheck;
    }

    public void setConditionToCheck(boolean conditionToCheck) {
        this.conditionToCheck = conditionToCheck;
    }

    @Override
    public boolean perform() {
        conditionToCheck = Engine.monster.playerVisible();
        return conditionToCheck;
    }

    @Override
    public void addChild(AbstractNode child) {
        this.ChildNodes.add(child);
    }

}
