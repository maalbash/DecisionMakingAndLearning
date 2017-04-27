package engine;

import behavior.BehaviorTree;
import objects.GameObject;
import objects.Monster;
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
    public static Monster monster;
    public static Environment environment;
    public static BehaviorTree behaviorTree;

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
        monster = new Monster(this,player);
        environment = new Environment(this);
        staticObjects = new ArrayList<>();
        behaviorTree = new BehaviorTree();
        behaviorTree.traverse(monster,player);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);


        player.setMonster(monster);

    }


    public void draw()
    {
        background(130, 130, 130);
        environment.update();
        player.update();
        //Monster.update();

        if(monster.reachedPlayer())
            noLoop();

        behaviorTree.runAllNodes();
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
        monster.setPosition(mouseloc);
        monster.setOrientation((PVector.sub(player.getPosition(), mouseloc)).heading());
    }
}