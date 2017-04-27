package behavior;

import objects.Monster;
import objects.Player;
import org.jetbrains.annotations.Contract;
import processing.core.PVector;
import utility.Utility;

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
        conditionToCheck = Monster.playerVisible();
        return conditionToCheck;
    }

    @Override
    public void addChild(AbstractNode child) {
        this.ChildNodes.add(child);
    }

    @Override
    public void setMonster(objects.Monster monster) {
        this.Monster = monster;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Monster getMonster() {
        return this.Monster;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
