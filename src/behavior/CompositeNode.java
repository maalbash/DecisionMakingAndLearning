package behavior;

import java.util.ArrayList;

/**
 * Created by mohz2 on 4/26/2017.
 */
public abstract class CompositeNode extends AbstractNode {


    public CompositeNode(){
        this.ChildNodes = new ArrayList<>();
    }

    public void addChild(AbstractNode abstractNode) {
        this.ChildNodes.add(abstractNode);
    }
}
