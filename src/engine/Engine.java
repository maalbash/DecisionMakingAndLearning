package engine;

import Learning.Learner;
import behavior.BehaviorTree;
import objects.*;
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
    public static boolean useBehaviorTree;
    public static boolean learningAlgo;
    public static Logger logger;
    public static Learner learner;
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


        environment = new Environment(this);
        staticObjects = new ArrayList<>();
        player = new Player(this);
        monster = new Monster(this);
        behaviorTree = new BehaviorTree();
        logger = new Logger(this);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);

        useBehaviorTree = false;
        player.setMonster(monster);
        learner = new Learner();

        //learner.loadData();

        //learner.calculateNetEntropy();
    }


    public void draw()
    {
        background(130, 130, 130);
        environment.update();
        player.update();

        if(useBehaviorTree)
            behaviorTree.runAllNodes();
        else{
            monster.behaviour();
            monster.update();
        }

        if(monster.reachedPlayer()) {
            reset();
        }
        if(learningAlgo)
            learner.runLearning();
        //logger.populateJsonArray();
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
        useBehaviorTree = !useBehaviorTree;
    }

    public static void reset(){
        monster.reset();
        player.reset();
        //System.out.println(logger.numOfData);
        //logger.writeJsonToFile();
    }
}