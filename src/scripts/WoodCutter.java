package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Locatable;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.concurrent.ThreadLocalRandom;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> {

    private BasicQuery<GameObject> allTrees;
    private Boolean bank = true;
    private final int DOING_NOTHING = -1;
    private final int RADIUS = 15;
    private final int WALKING_DISTANCE = 2;
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
            if (bank) {
                banking();
            } else {
                dropAllLogs();
            }
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
        } else {
            switchTabs();
        }
    }

    private void banking() {
        if (ctx.bank.select().viewable().poll() != null) {
             ctx.movement.step(ctx.bank.nearest());
        } else {
            ctx.bank.open();
            ctx.bank.depositInventory();
            ctx.bank.close();
        }
    }

    /**
     * Switches tabs randomly
     */
    private void switchTabs() {
        if (getRandomInt(100) == 100 && ctx.game.tab() != Game.Tab.EQUIPMENT) {
            ctx.game.tab(Game.Tab.EQUIPMENT);
        } else if (getRandomInt(50) == 50 && ctx.game.tab() != Game.Tab.STATS) {
            ctx.game.tab(Game.Tab.STATS);
            ctx.input.move(680 + getRandomInt(50),364 + getRandomInt(25));
        } else if (getRandomInt(20) == 20 && ctx.game.tab() != Game.Tab.INVENTORY){
            ctx.game.tab(Game.Tab.INVENTORY);
        }
    }

    /**
     * Verify if the player should go back to square one
     * @return
     */
    private boolean shouldStepBack() {
        int locationX = ctx.players.local().tile().x();
        int locationY = ctx.players.local().tile().y();
        return locationX < initialTile.x() - RADIUS
                || locationX > initialTile.x() + RADIUS
                || locationY < initialTile.y() - RADIUS
                || locationY > initialTile.y() + RADIUS;
    }

    /**
     * Drops all the logs on the groud
     */
    private void dropAllLogs() {
        if (ctx.game.tab() != Game.Tab.INVENTORY)  {
            ctx.game.tab(Game.Tab.INVENTORY);
        }
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

    private int getRandomInt(int maxInt){
        return ThreadLocalRandom.current().nextInt(0, maxInt + 1);
    }

    /*private Objects checkInventory(){

    }*/
}
