package objects;

import engine.Engine;
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

    private long bulletInterval = 500;
    private static PVector Monstercolor = new PVector(255, 0, 0);
    private static float size = 20;

    private static float DEFAULT_X =  GameConstants.SCR_WIDTH/2 - 100;
    private static float DEFAULT_Y =  50;

    private PathFollower pathFollower;
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private Set<Bullet> bullets;
    private float bulletDamage = 10;
    private State state;


    private static boolean followingPath;
    private long lastBulletTime;




    public Monster(PApplet app) {
        super(app, Monstercolor, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

        this.app = app;
        setMaxVel(2.5f);
        setMaxAngularAcc(0.001f);
        setAngularROS(1.5f);
        setAngularROD(2.5f);


        bullets = new HashSet<>();
        pathFollower = new PathFollower(this);
        state = State.SEEKTARGET;
        lastBulletTime = System.currentTimeMillis();
    }


    public static boolean isFollowingPath() {
        return followingPath;
    }

    public static void setFollowingPath(boolean followingPath) {
        Monster.followingPath = followingPath;
    }

    public PathFollower getPathFollower() {
        return pathFollower;
    }

    public void setPathFollower(PathFollower pathFollower) {
        this.pathFollower = pathFollower;
    }

    public void update()
    {
        //behaviour();
        super.update();
        if(reachedPlayer())
            Engine.reset();

        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); )
        {
            Bullet b = i.next();

            if(b.hasHit(Engine.player))
            {
                i.remove();
                Engine.player.takeDamage(this.bulletDamage);

                break;
            }

            else if (b.outOfBounds())
                i.remove();
            else
                b.update();
        }
    }

    public void behaviour()
    {
        if(playerVisible()){
            followingPath = false;
            state = State.SHOOT;
        }else if(obstacleNearby()){
            state = State.SEEKTARGET;
        }

        switch(state)
        {
            case SHOOT:
                if(this.getPosition().dist(Engine.player.getPosition()) <= 300f)
                    shootAtPlayer();
                else {
                    Align(Engine.player.getPosition());
                    Seek(Engine.player.getPosition());
                }
                break;

            case SEEKTARGET:
                if(!followingPath || !pathFollower.reachedTarget)
                    seekPlayer();
                pathFollower.followPath();
                break;

        }
    }


    public void seekPlayer(){
        //TODO - change this to pursue if you find time
        pathFollower.findPath(getGridLocation(),Engine.player.getGridLocation());

        followingPath = true;
    }

    public void shootAtPlayer(){

        if(this.getPosition().dist(Engine.player.getPosition()) <= 500f) {
            followingPath = false;
            Seek(Engine.player.getPosition());
            shoot();
        }else {
            Align(Engine.player.getPosition());
            Seek(Engine.player.getPosition());
        }

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
        return this.getGridIndex() == Engine.player.getGridIndex();
    }

    //TODO - check why LOS is messed up.
    public boolean playerVisible(){
        return hasLOS(Utility.getGridLocation(Engine.player.getPosition()));
    }

    public void reset(){
        this.setPosition(new PVector(DEFAULT_X,DEFAULT_Y));
        followingPath = false;
        state = State.SEEKTARGET;
    }
}
