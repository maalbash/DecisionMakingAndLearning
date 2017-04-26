package objects;

import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PVector;
import utility.*;

import java.util.*;

/**
 * Created by mohz2 on 4/25/2017.
 */
public class Monster extends GameObject{

    private enum State
    {
        SEEKTARGET, AVOID
    }

    private GameObject player;

    private static PVector color = new PVector(0, 0, 0);
    private static float size = 20;

    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;
    private PathFollower pathFollower;
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private Set<Bullet> bullets;
    private float bulletDamage = 10;
    private State state;
    private static boolean followingPath;

    private PVector MonsterTarget;

    private GameObject finalTarget;

    public Monster(PApplet app, GameObject player) {
        super(app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

        this.app = app;
        setMaxVel(3f);
        setMaxAngularAcc(0.001f);
        setAngularROS(1.5f);
        setAngularROD(2.5f);

        this.player = player;
        MonsterTarget = player.getPosition();
        finalTarget = player;

        bullets = new HashSet<>();
        pathFollower = new PathFollower(this);
        state = State.SEEKTARGET;
    }

    public GameObject getPlayer() {
        return player;
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }

    public void update()
    {
        behaviour();
        super.update();

        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); )
        {
            Bullet b = i.next();

            if (b.outOfBounds())
                i.remove();
            else
                b.update();
        }
    }

    public void behaviour()
    {
        if (obstacleCollisionDetected()) {
            state = State.AVOID;
        }else {
            state = State.SEEKTARGET;
        }

        switch(state)
        {
            case AVOID:
                avoidObstacle();
                break;

            case SEEKTARGET:
                seekPlayer();
                break;
        }
    }

    public void avoidObstacle(){
        pathFollower.findPath(getGridLocation(), Utility.getGridLocation(finalTarget.position));
        followingPath = true;
    }

    public void seekPlayer(){
        Align(finalTarget.getPosition());
        Seek(finalTarget.getPosition());
    }
}
