package Learning;

import utility.GameConstants;

/**
 * Created by mohz2 on 4/27/2017.
 */
public class Record {
    public boolean LOS, SHOOTINGDISTANCE;
    public GameConstants.MONSTER_STATE STATE;

    public Record(){
        this.LOS = false;
        this.SHOOTINGDISTANCE = false;
    }
}
