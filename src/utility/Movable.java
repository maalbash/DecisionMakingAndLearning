package utility;

import objects.GameObject;
import processing.core.PVector;

/**
 * Created by mohz2 on 4/25/2017.
 */
public interface Movable
{
    void Seek(PVector target);

    void Arrive(PVector target);

    void Align(PVector target);

    void Wander();

    void Pursue(GameObject target);

    void stopMoving();

    boolean outOfBounds();

}
