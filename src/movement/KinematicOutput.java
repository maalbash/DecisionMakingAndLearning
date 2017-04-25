package movement;

import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

/**
 * Created by mohz2 on 4/25/2017.
 */

public class KinematicOutput
{
    public PVector velocity;
    public float rotation;

    public KinematicOutput()
    {
        velocity = new PVector(0, 0);
        rotation = 0f;
    }
}

