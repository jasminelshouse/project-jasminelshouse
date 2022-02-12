import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }
    public Viewport getViewport(){
        return this.viewport;
    }


    public void drawBackground() {
        for (int row = 0; row < viewport.getNumRows(); row++) {
            for (int col = 0; col < viewport.getNumCols(); col++) {
                Point worldPoint = viewport.viewportToWorld(col, row);
                Optional<PImage> image =
                        world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    screen.image(image.get(), col * tileWidth,
                            row * tileHeight);
                }
            }
        }
    }

    public void drawEntities() {
        for (Entity entity : world.getEntities()) {
            Point pos = entity.getPosition();

            if (viewport.contains(pos)) {
                Point viewPoint = viewport.worldToViewport(pos.getX(), pos.getY());
                screen.image(entity.getCurrentImage(),
                        viewPoint.getX() * tileWidth,
                        viewPoint.getY() * tileHeight);
            }
        }
    }

    public void drawViewport() {
        drawBackground();
        drawEntities();
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = Functions.clamp(viewport.getCol() + colDelta, 0,
                world.getNumCols() - viewport.getNumCols());
        int newRow = Functions.clamp(viewport.getRow() + rowDelta, 0,
                world.getNumRows() - viewport.getNumRows());

        viewport.shift(newCol, newRow);
    }
}
