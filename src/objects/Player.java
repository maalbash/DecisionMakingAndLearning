package objects;


import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.*;


/**
 * Created by mohz2 on 4/25/2017.
 */

public class Player extends GameObject
{
    public enum State
    {
        WANDER, FLEE, AVOID
    }

    private PApplet app;

    private GameObject Monster;

    /* Player properties */

    public static PVector color = new PVector(0, 0, 0);
    public static float size = 20;

    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;

    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;

    private PVector scrCenter = GameConstants.SCR_CENTER;

    private PathFollower pathfollower;

    private PVector prevPos;
    private PVector nextPos;

    public Set<Bullet> bullets;
    public PVector playerTarget;
    public static float BulletDamage = 5;


    public State state;


    public Player(PApplet app)
    {
        super (app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);
        this.app = app;

        setMaxVel(1f);
        setMaxAngularAcc(0.001f);
        setAngularROS(1.5f);
        setAngularROD(2.5f);

        bullets = new HashSet<>();
        playerTarget = new PVector(app.mouseX, app.mouseY);
        state = State.WANDER;
        pathfollower = new PathFollower(this);

    }


    public GameObject getMonster() {
        return Monster;
    }

    public void setMonster(GameObject monster) {
        Monster = monster;
    }

    public PVector getPrevPos() {
        return prevPos;
    }

    public PVector getNextPos() {
        return nextPos;
    }

    public void shoot()
    {
        bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color));
    }


    public void update()
    {
        prevPos = this.getPosition();
        behaviour();
        super.update();
        nextPos = PVector.add(this.getPosition(),PVector.mult(this.getPosition(),this.maxVel));
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
        }else if (monsterNearBy()) {
            state = State.FLEE;
        }else{
            state = State.WANDER;
        }

        switch(state)
        {
            case AVOID:
                avoidObstacle();
                break;

            case FLEE:
                fleeMonster();
                break;

            case WANDER:
                setMaxAngularAcc(0.001f);
                Wander();
                break;
        }
    }

    public void updateTarget()
    {
        playerTarget = new PVector(app.mouseX, app.mouseY);
        setMaxVel(3f);
        setMaxAngularAcc(GameConstants.DEFAULT_MAX_angularACC);
    }

    public void avoidObstacle()
    {
        state = state.AVOID;
        targetRotationWander = velocity.heading() + (float) Math.PI;
        Wander();
    }

    public boolean monsterNearBy(){
        return (this.position.dist(getMonster().position) <= GameConstants.PERCEPTION);
    }

    public void fleeMonster(){
        PVector dir = PVector.sub(this.position,getMonster().position);
        Align(dir);
        playerTarget = PVector.add(this.getPosition(),PVector.mult(dir,GameConstants.FLEE_OFFSET));
        Seek(playerTarget);
    }
}