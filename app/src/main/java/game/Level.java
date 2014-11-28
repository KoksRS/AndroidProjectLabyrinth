package game;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by artem_tereshko on 11/11/2014.
 */
public class Level {

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<CircularWall> getCircularWalls() {
        return circularWalls;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    private ArrayList<Wall> walls;
    private ArrayList<CircularWall> circularWalls;
    private ArrayList<Cannon> cannons;
    private ArrayList<Hole> holes;

    private Rect movementBounds;

    private int worldHeight;
    private int worldWidth;

    /**
     *
     * @param height Height of level in pixels
     * @param width Width of level in pixels
     */
    public Level(int height, int width) {
        this.worldHeight = height;
        this.worldWidth = width;

        circularWalls = new ArrayList<CircularWall>();
        cannons = new ArrayList<Cannon>();
        walls = new ArrayList<Wall>();
        holes = new ArrayList<Hole>();
        movementBounds = new Rect(0,0, width, height);

    }
    /**
     *
     * @param height Height of level in pixels
     * @param width Width of level in pixels
     */
    public static Level Level1(int height, int width){

        Level lvl = new Level(height, width);
        //lvl.addWall(new Wall(0,0, width, 10));
        //lvl.addWall(new Wall(0,0, 10, height));
       // lvl.addWall(new Wall(width-10,0, 10, height));
        lvl.addWall(new Wall(400,70,50,500));

        lvl.addWall(new Wall(700,400,50,500));

      //  lvl.addCircularWall(new CircularWall(50, 300,150));

        lvl.addHole(new Hole(1000,300,50,50));

        return lvl;
    }
    /**
     *
     * @param height Height of level in pixels
     * @param width Width of level in pixels
     */
    public static Level Level2(int height, int width){
        return null;
    }
    /**
     *
     * @param height Height of level in pixels
     * @param width Width of level in pixels
     */
    public static Level Level3(int height, int width){
        return null;
    }

    public void addWall(Wall w){
        walls.add(w);
    }

    public void addCircularWall(CircularWall cw){
        circularWalls.add(cw);
    }

    public void addCannon(Cannon c) {
        cannons.add(c);
    }

    public void addHole(Hole h) {
        holes.add(h);
    }

    public Rect getMovementBounds() {
        return this.movementBounds;
    }

}
