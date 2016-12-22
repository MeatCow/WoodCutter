package scripts;

import com.sun.javafx.geom.Area;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import java.util.List;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> {

    private BasicQuery<GameObject> allTrees;
    private final int DOING_NOTHING = -1;
    private final int RADIUS = 15;
    private int[] cutable = {1278,1276,1751};
    private int[] dropable = {1511,1521};

    private Tile initialTile;

    @Override
    public void start() {
        allTrees = ctx.objects.select().id(cutable);
        initialTile = ctx.players.local().tile();
    }

    @Override
    public void poll() {
        allTrees = ctx.objects.select().id(cutable);

        if (inventoryIsFull()) {
            dropAllLogs();
        } else if (shouldCut()) {
            if (shouldStepBack()) {
                ctx.movement.step(initialTile);
            } else {
                GameObject tree = allTrees.nearest().poll();
                ctx.camera.turnTo(tree);
                ctx.movement.step(tree);
                tree.interact("Chop");
            }
        }
    }

    private boolean shouldStepBack() {
        int locationX = ctx.players.local().tile().x();
        int locationY = ctx.players.local().tile().y();
        return locationX < initialTile.x() - RADIUS
                || locationX > initialTile.x() + RADIUS
                || locationY < initialTile.y() - RADIUS
                || locationY > initialTile.y() + RADIUS;
    }

    private void dropAllLogs() {
        for (Item inventoryItem: ctx.inventory.select().id(dropable)) {
            inventoryItem.interact("Drop");
        }
    }

    private boolean shouldCut() {
        return !inventoryIsFull()
                && ctx.players.local().animation() == DOING_NOTHING
                && !ctx.players.local().inMotion();
    }

    private boolean inventoryIsFull() {
        return ctx.inventory.select().count() == 28;
    }
}