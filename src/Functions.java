import java.util.*;

import processing.core.PImage;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions
{
    public static final Random rand = new Random();


    public static final int PROPERTY_KEY = 0;

    public static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right",
            "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));



    public static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);}
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }



    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */


    public static boolean parseBackground(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Background.BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Background.BGND_COL]),
                    Integer.parseInt(properties[Background.BGND_ROW]));
            String id = properties[Background.BGND_ID];
            world.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == Background.BGND_NUM_PROPERTIES;
    }

    public static boolean parseSapling(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.SAPLING_COL]),
                    Integer.parseInt(properties[EntityKind.SAPLING_ROW]));
            String id = properties[EntityKind.SAPLING_ID];
            int health = Integer.parseInt(properties[EntityKind.SAPLING_HEALTH]);
            Entity entity = new Entity(EntityKind.SAPLING, id, pt, imageStore.getImageList(EntityKind.SAPLING_KEY), 0, 0,
                    EntityKind.SAPLING_ACTION_ANIMATION_PERIOD, EntityKind.SAPLING_ACTION_ANIMATION_PERIOD, health, EntityKind.SAPLING_HEALTH_LIMIT);
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.SAPLING_NUM_PROPERTIES;
    }

    public static boolean parseDude(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.DUDE_COL]),
                    Integer.parseInt(properties[EntityKind.DUDE_ROW]));
            Entity entity = createDudeNotFull(properties[EntityKind.DUDE_ID],
                    pt,
                    Integer.parseInt(properties[EntityKind.DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[EntityKind.DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[EntityKind.DUDE_LIMIT]),
                    imageStore.getImageList(EntityKind.DUDE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.DUDE_NUM_PROPERTIES;
    }

    public static boolean parseFairy(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.FAIRY_COL]),
                    Integer.parseInt(properties[EntityKind.FAIRY_ROW]));
            Entity entity = createFairy(properties[EntityKind.FAIRY_ID],
                    pt,
                    Integer.parseInt(properties[EntityKind.FAIRY_ACTION_PERIOD]),
                    Integer.parseInt(properties[EntityKind.FAIRY_ANIMATION_PERIOD]),
                    imageStore.getImageList(EntityKind.FAIRY_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.FAIRY_NUM_PROPERTIES;
    }

    public static boolean parseTree(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.TREE_COL]),
                    Integer.parseInt(properties[EntityKind.TREE_ROW]));
            Entity entity = createTree(properties[EntityKind.TREE_ID],
                    pt,
                    Integer.parseInt(properties[EntityKind.TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[EntityKind.TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[EntityKind.TREE_HEALTH]),
                    imageStore.getImageList(EntityKind.TREE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.TREE_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.OBSTACLE_COL]),
                    Integer.parseInt(properties[EntityKind.OBSTACLE_ROW]));
            Entity entity = createObstacle(properties[EntityKind.OBSTACLE_ID], pt,
                    Integer.parseInt(properties[EntityKind.OBSTACLE_ANIMATION_PERIOD]),
                    imageStore.getImageList(
                            EntityKind.OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseHouse(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EntityKind.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[EntityKind.HOUSE_COL]),
                    Integer.parseInt(properties[EntityKind.HOUSE_ROW]));
            Entity entity = createHouse(properties[EntityKind.HOUSE_ID], pt,
                    imageStore.getImageList(
                            EntityKind.HOUSE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == EntityKind.HOUSE_NUM_PROPERTIES;
    }



    public static Entity createStump(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Entity(EntityKind.STUMP, id, position, images, 0, 0,
                0, 0, 0, 0);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Entity createSapling(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0,
                EntityKind.SAPLING_ACTION_ANIMATION_PERIOD, EntityKind.SAPLING_ACTION_ANIMATION_PERIOD, 0, EntityKind.SAPLING_HEALTH_LIMIT);
    }
    public static Entity createHouse(
            String id, Point position, List<PImage> images)
    {
        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0,
                0, 0, 0);
    }

    public static Entity createObstacle(
            String id, Point position, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0,
                animationPeriod, 0, 0);
    }

    public static Entity createTree(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new Entity(EntityKind.TREE, id, position, images, 0, 0,
                actionPeriod, animationPeriod, health, 0);
    }


    public static Entity createFairy(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    // need resource count, though it always starts at 0
    public static Entity createDudeNotFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    // don't technically need resource count ... full
    public static Entity createDudeFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }
}