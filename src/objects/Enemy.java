package objects;

import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PVector;


/**
 * Created by mohz2 on 4/25/2017.
 */
public abstract class Enemy extends GameObject
{

    public static int shootRadius = 150;
    public static PathFollower pathFollower;

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life)
    {
        super (app, color, size, posX, posY, orientation, life);
        pathFollower = new PathFollower(this);
    }

    public abstract void behaviour();

    public abstract void defaultBehaviour();

    public abstract void avoidObstacle();



}
