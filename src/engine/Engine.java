package engine;

import objects.GameObject;
import objects.Player;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;

import java.util.ArrayList;
import java.util.List;
import environment.*;

public class Engine extends PApplet
{

    public static Player player;        //Changed these 2 to static, since only one instance of each, and to provide ease of access
    public static GameObject Monster;
    public static Environment environment;

    public static List<Obstacle> staticObjects;

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings()
    {
        size(GameConstants.SCR_WIDTH, GameConstants.SCR_HEIGHT);
    }

    public void setup()
    {
        rectMode(PConstants.CENTER);

        player = new Player(this);
        environment = new Environment(this);
        staticObjects = new ArrayList<>();

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);

        Monster = new GameObject(GameConstants.SCR_CENTER,0);
        player.setMonster(Monster);

    }


    public void draw()
    {
        background(130, 130, 130);
        environment.update();
        player.update();

    }

    /*public void mouseMoved()
    {
        player.updateTarget();
    }
    public void mousePressed()
    {
        player.shoot();
    }*/

    public void mouseClicked()
    {
        PVector mouseloc = new PVector(mouseX,mouseY);
        Monster.setPosition(mouseloc);
        Monster.setOrientation((PVector.sub(player.getPosition(), mouseloc)).heading());
    }
}