package behavior;

import objects.Monster;
import objects.Player;
import utility.Utility;

/**
 * Created by mohz2 on 4/26/2017.
 */
public class BehaviorTree {
    CompositeNode root;

    public BehaviorTree() {
        createTree();
    }

    public void createTree(){
        //root node
        this.root = new Selector();

        // level 2 children of root
        this.root.addChild(new Sequence());
        this.root.addChild(new Sequence());

        //level 3
        //children of left child
        this.root.ChildNodes.get(0).addChild(new EvaluationNode());
        this.root.ChildNodes.get(0).addChild(new ActionNode(Utility.ACTIONTYPE.SHOOT));

        //children of right child
        this.root.ChildNodes.get(1).addChild(new Decorator());
        this.root.ChildNodes.get(1).addChild(new ActionNode(Utility.ACTIONTYPE.SHOOT));

        //level 4
        //child of decorator
        this.root.ChildNodes.get(1).ChildNodes.get(0).addChild(new ActionNode(Utility.ACTIONTYPE.SEEK));
    }

    public void runAllNodes(){
        root.perform();
    }

    public void traverse(Monster monster, Player player){
        this.root.setMonster(monster);
        this.root.setPlayer(player);
        this.root.ChildNodes.get(0).setMonster(monster);
        this.root.ChildNodes.get(0).setPlayer(player);
        this.root.ChildNodes.get(1).setMonster(monster);
        this.root.ChildNodes.get(1).setPlayer(player);
        this.root.ChildNodes.get(0).ChildNodes.get(0).setMonster(monster);
        this.root.ChildNodes.get(0).ChildNodes.get(0).setPlayer(player);
        this.root.ChildNodes.get(0).ChildNodes.get(1).setMonster(monster);
        this.root.ChildNodes.get(0).ChildNodes.get(1).setPlayer(player);
        this.root.ChildNodes.get(1).ChildNodes.get(0).setMonster(monster);
        this.root.ChildNodes.get(1).ChildNodes.get(0).setPlayer(player);
        this.root.ChildNodes.get(1).ChildNodes.get(1).setMonster(monster);
        this.root.ChildNodes.get(1).ChildNodes.get(1).setPlayer(player);
        this.root.ChildNodes.get(1).ChildNodes.get(0).ChildNodes.get(0).setMonster(monster);
        this.root.ChildNodes.get(1).ChildNodes.get(0).ChildNodes.get(0).setPlayer(player);
    }

}
