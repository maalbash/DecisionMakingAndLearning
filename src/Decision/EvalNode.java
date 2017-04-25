package Decision;

import Other.Helper;

import java.util.ArrayList;

/**
 * Created by mohz2 on 4/16/2017.
 */
public class EvalNode extends DTnode {
    ArrayList<DTnode> childActions;

    public EvalNode() {
        this.nodetype = Helper.NODETYPE.EVALUATION;
    }
}
