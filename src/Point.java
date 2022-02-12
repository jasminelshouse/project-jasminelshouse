/**
 * A simple class representing a location in 2D space.
 */
public final class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
    public int distanceSquared(Point p2) {
        int deltaX = x - p2.x;
        int deltaY = y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
    public boolean adjacent(Point p1) {
        return (x == p1.x && Math.abs(y - p1.y) == 1) || (y == p1.y
                && Math.abs(x - p1.x) == 1);
    }
}
