package behavior;

import objects.Monster;
import objects.Player;

/**
 * Created by mohz2 on 4/26/2017.
 */
public class Sequence extends CompositeNode {
    public Sequence() {
        super();
    }

    @Override
    public void addChild(AbstractNode abstractNode) {
        super.addChild(abstractNode);
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

    @Override
    public boolean perform() {
        for(AbstractNode node: this.ChildNodes)
            if(!node.perform())
                return false;
        return true;
    }
}
