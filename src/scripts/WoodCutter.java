package scripts;

import org.powerbot.script.Locatable;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.concurrent.ThreadLocalRandom;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> {

    private BasicQuery<GameObject> allTrees;
    private final int DOING_NOTHING = -1;
    private final int RADIUS = 15;
    private final int WALKING_DISTANCE = 2;
    private int[] cutable = {1278, 1276, 1751};
    private int[] dropable = {1511, 1521};
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
                Locatable randomTree = getTreePositionRandom(tree.tile());
                if (!tree.inViewport() || treeFarAway(randomTree)) {
                    ctx.camera.turnTo(randomTree);
                    ctx.movement.step(randomTree);
                }
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
        for (Item inventoryItem : ctx.inventory.select().id(dropable)) {
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

    private Boolean treeFarAway(Locatable tree) {
        int playerLocationX = ctx.players.local().tile().x();
        int playerLocationY = ctx.players.local().tile().y();
        int treeLocationX = tree.tile().x();
        int treeLocationY = tree.tile().y();
        return playerLocationX < treeLocationX - WALKING_DISTANCE
                || playerLocationX > treeLocationX + WALKING_DISTANCE
                || playerLocationY < treeLocationY - WALKING_DISTANCE
                || playerLocationY > treeLocationY + WALKING_DISTANCE;
    }

    public Locatable getTreePositionRandom(Tile treePosition) {
        int randomPositionX = (treePosition.x() - 2) + getRandomInt(5);
        int randomPositionY = (treePosition.y() - 2) + getRandomInt(5);
        Tile tile = new Tile(randomPositionX, randomPositionY);
        return tile;
    }

    private int getRandomInt(int maxInt) {
        return ThreadLocalRandom.current().nextInt(0, maxInt + 1);
    }
    /*private Objects checkInventory(){

    }*/
}
