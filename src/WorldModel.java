import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    public int numRows;
    public int numCols;
    public Background background[][];
    public Entity occupancy[][];
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    public int getNumRows(){
        return this.numRows;
    }
    public int getNumCols(){
        return this.numCols;
    }
    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.getY()][pos.getX()];
    }
    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }
    public Optional<PImage> getBackgroundImage(
            Point pos)
    {
        Background b = getBackgroundCell(pos);
        if (withinBounds(pos)) {
            return Optional.of(b.getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public Background getBackgroundCell(Point pos) {
        return background[pos.getY()][pos.getX()];
    }
    public void setBackground(
            Point pos, Background background)
    {
        if (withinBounds(pos)) {

            setBackgroundCell(pos, background);
        }
    }

    public void setBackgroundCell(
            Point pos, Background background)
    {
        this.background[pos.getY()][pos.getX()] = background;
    }
    public void setOccupancyCell(
            Point pos, Entity entity)
    {
        occupancy[pos.getY()][pos.getX()] = entity;
    }



    public Set<Entity> getEntities() {
        return entities;
    }
    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.position)) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }
    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            Point i = entity.getPosition();
            i = new Point(-1, -1);
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }
    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public Optional<Entity> findNearest(
            Point pos, List<EntityKind> kinds)
    {
        List<Entity> ofType = new LinkedList<>();
        for (EntityKind kind: kinds)
        {
            for (Entity entity : entities) {
                if (entity.getKind() == kind) {
                    ofType.add(entity);
                }
            }
        }

        return this.nearestEntity(ofType, pos);
    }
    public Optional<Entity> nearestEntity(
            List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            Point p = nearest.getPosition();
            int nearestDistance = p.distanceSquared(pos);

            for (Entity other : entities) {
                Point k = other.getPosition();
                int otherDistance = k.distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }
    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < numRows && pos.getX() >= 0
                && pos.getX() < numCols;
    }
}
