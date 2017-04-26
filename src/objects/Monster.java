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
        SEEKTARGET, AVOID, SHOOT
    }

    private GameObject player;
    private long bulletInterval = 500;
    private static PVector Monstercolor = new PVector(255, 0, 0);
    private static float size = 30;

    private static float DEFAULT_X =  100;
    private static float DEFAULT_Y =  10;
    private PathFollower pathFollower;
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private Set<Bullet> bullets;
    private float bulletDamage = 10;
    private State state;
    private static boolean followingPath;
    private long lastBulletTime;
    private PVector MonsterTarget;



    public Monster(PApplet app, GameObject player) {
        super(app, Monstercolor, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

        this.app = app;
        setMaxVel(3f);
        setMaxAngularAcc(0.001f);
        setAngularROS(1.5f);
        setAngularROD(2.5f);

        this.player = player;
        MonsterTarget = player.getPosition();


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
        }else if(Utility.hasLOS(this.getPosition(),MonsterTarget)){
            state = State.SHOOT;
        }else{
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

            case SHOOT:
                shoot();
                break;

        }
    }

    public void avoidObstacle(){
//        pathFollower.findPath(getGridLocation(), Utility.getGridLocation(finalTarget.position));
//        followingPath = true;
        state = State.AVOID;
        targetRotationWander = velocity.heading() + (float) Math.PI;
        Wander();
    }

    public void seekPlayer(){
        Align(getPlayer().getPosition());
        Seek(getPlayer().getPosition());
    }

    public void shoot()
    {
        long now = System.currentTimeMillis();
        if(now - lastBulletTime >= bulletInterval)
        {
            bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color));
            lastBulletTime = now;
        }
    }
}
