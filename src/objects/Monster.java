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

    private Player player;
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



    public Monster(PApplet app, Player player) {
        super(app, Monstercolor, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

        this.app = app;
        setMaxVel(2f);
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

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void update()
    {
        behaviour();
        super.update();
        reachedPlayer();

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
        if(playerVisible()){
            state = State.SHOOT;
        }else{
            state = State.SEEKTARGET;
        }

        switch(state)
        {
            case SEEKTARGET:
                seekPlayer();
                break;

            case SHOOT:
                shootAtPlayer();
                break;

        }
    }


    public void seekPlayer(){

        //TODO - change this to pursue if you find time
        pathFollower.findPath(getGridLocation(),Utility.getGridLocation(this.player.getPosition()));
        followingPath = true;
        pathFollower.followPath();
    }

    public void shootAtPlayer(){
        stopMoving();
        Align(this.player.getPosition());
        shoot();
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

    public boolean reachedPlayer(){
        return this.getPosition().dist(this.player.getPosition()) <= 1f;
    }

    public boolean playerVisible(){
        return hasLOS(this.player.getPosition()) || this.getPosition().dist(this.player.getPosition()) <= GameConstants.MONSTER_PERCEPTION;
    }
}
