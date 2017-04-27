package behavior;

import engine.Engine;
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

        System.out.println("DONE CREATING TREE!");
    }

    public void runAllNodes(){
        root.perform();
    }

//    public void traverse(){
//        this.root.setMonster(Engine.monster);
//        this.root.setPlayer(Engine.player);
//        this.root.ChildNodes.get(0).setMonster(Engine.monster);
//        this.root.ChildNodes.get(0).setPlayer(Engine.player);
//        this.root.ChildNodes.get(1).setMonster(Engine.monster);
//        this.root.ChildNodes.get(1).setPlayer(Engine.player);
//        this.root.ChildNodes.get(0).ChildNodes.get(0).setMonster(Engine.monster);
//        this.root.ChildNodes.get(0).ChildNodes.get(0).setPlayer(Engine.player);
//        this.root.ChildNodes.get(0).ChildNodes.get(1).setMonster(Engine.monster);
//        this.root.ChildNodes.get(0).ChildNodes.get(1).setPlayer(Engine.player);
//        this.root.ChildNodes.get(1).ChildNodes.get(0).setMonster(Engine.monster);
//        this.root.ChildNodes.get(1).ChildNodes.get(0).setPlayer(Engine.player);
//        this.root.ChildNodes.get(1).ChildNodes.get(1).setMonster(Engine.monster);
//        this.root.ChildNodes.get(1).ChildNodes.get(1).setPlayer(Engine.player);
//        this.root.ChildNodes.get(1).ChildNodes.get(0).ChildNodes.get(0).setMonster(Engine.monster);
//        this.root.ChildNodes.get(1).ChildNodes.get(0).ChildNodes.get(0).setPlayer(Engine.player);
//
//        System.out.println("DONE POPULATING TREE");
//    }

}
