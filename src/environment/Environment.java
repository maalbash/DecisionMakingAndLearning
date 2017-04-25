package environment;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mohz2 on 4/25/2017.
 */
public class Environment
{
    private static PApplet app;

    public static List<Obstacle> obstacles;
    public static Set<Integer> invalidNodes;
    public static PVector tileSize;
    public static PVector numTiles;

    public PVector centerColor = new PVector(255, 0, 0);

    public static Graph gameGraph;

    public Environment(PApplet app)
    {
        this.app = app;
        tileSize = GameConstants.TILE_SIZE;
        numTiles = GameConstants.NUM_TILES;

        invalidNodes = new HashSet<>();
        obstacles = new ArrayList<>();

        createObstacles();

        gameGraph = new Graph();
        gameGraph.buildGraph(invalidNodes);

    }

    public void update()
    {
        drawObstacles();
    }


    /* Getters and Setters */

    public Set<Integer> getInvlaidNodes()
    {
        return invalidNodes;
    }

    public List<Obstacle> getObstacles()
    {
        return obstacles;
    }

    public Graph getGameGraph() {
        return gameGraph;
    }


    /* Helper methods */

    public void drawGraph()
    {
        app.rectMode(PConstants.CORNER);

        for (int i = 0; i < numTiles.y; i++)
        {
            for (int j = 0; j < numTiles.x; j++)
            {
                app.noFill();
                app.rect(j * tileSize.x, i * tileSize.y, tileSize.x, tileSize.y);
            }
        }

        app.rectMode(PConstants.CENTER);

    }

    private void createObstacles()
    {

        /* Clockwise from left */

        /* Outer layer */
        obstacles.add(new Obstacle(app, new PVector(0.2f * numTiles.x, 0.5f * numTiles.y), new PVector(4, 40))); //left wall
        obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.2f * numTiles.y), new PVector(22, 4))); //back seats
        obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.8f * numTiles.y), new PVector(22, 4))); //front seats
        obstacles.add(new Obstacle(app, new PVector(0.8f * numTiles.x, 0.5f * numTiles.y), new PVector(4, 40))); //right wall

        /* Tooth nodes are not considered invalid */

        for (Obstacle obstacle : obstacles)
            invalidNodes.addAll(obstacle.getTileIndices());
    }

    private void drawObstacles()
    {
        obstacles.forEach(Obstacle::draw);
    }

    private void drawInvalidNodes()
    {
        for (int i : invalidNodes)
            colorNode(i, new PVector(255, 0, 0));
    }

    public static void colorNode(int index, PVector color)
    {
        app.fill(color.x, color.y, color.z);
        app.rect((index % numTiles.x) * tileSize.x, (float)Math.floor(index / numTiles.y) * tileSize.y, tileSize.x, tileSize.y);
        app.noFill();
    }

}