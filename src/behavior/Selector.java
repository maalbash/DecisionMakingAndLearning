package behavior;

/**
 * Created by mohz2 on 4/26/2017.
 */
public class Selector extends CompositeNode {
    public Selector() {
        super();
    }

    @Override
    public void addChild(AbstractNode abstractNode) {
        super.addChild(abstractNode);
    }

    @Override
    public boolean perform() {
        for(AbstractNode node: this.ChildNodes)
            if(node.perform())
                return true;
        return false;
    }
}
