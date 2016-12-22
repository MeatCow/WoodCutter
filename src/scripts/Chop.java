package scripts;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.*;
import org.powerbot.script.*;

/**
 *
 * @author matthieu
 */
public class Chop extends Task {

    private final int DOING_NOTHING = -1;
    private final int RADIUS = 15;
    private final int WALKING_DISTANCE = 2;
    
    private Tile initialTile;
    private BasicQuery<GameObject> allTrees;
    private int[] cutable = {1278,1276,1751};
    
    public Chop(ClientContext ctx) {
        super(ctx);
        allTrees = ctx.objects.select().id(cutable);
        initialTile = ctx.players.local().tile();
    }

    @Override
    public boolean shouldActivate() {
        return ctx.inventory.select().count() < 28
                && ctx.players.local().animation() == DOING_NOTHING
                && !ctx.players.local().inMotion();
    }

    @Override
    public void execute() {
        if (shouldStepBack()) {
                ctx.movement.step(initialTile);
            } else {
                GameObject tree = allTrees.nearest().poll();
                Locatable randomTree = getTreePositionRandom(tree.tile());
                if (!tree.inViewport() || farFromObject(randomTree,WALKING_DISTANCE)) {
                    ctx.camera.turnTo(randomTree);
                    ctx.movement.step(randomTree);
                }
                tree.interact("Chop");
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
    
    private boolean farFromObject(Locatable thing, int distance) {
        int playerLocationX = ctx.players.local().tile().x();
        int playerLocationY = ctx.players.local().tile().y();
        int objectLocationX = thing.tile().x();
        int objectLocationY = thing.tile().y();
        return playerLocationX < objectLocationX - distance
                || playerLocationX > objectLocationX + distance
                || playerLocationY < objectLocationY - distance
                || playerLocationY > objectLocationY + distance;

    }
    
    public Locatable getTreePositionRandom(Tile treePosition) {
        int randomPositionX = (treePosition.x() - 2) + Random.nextInt(0,5);
        int randomPositionY = (treePosition.y() - 2) + Random.nextInt(0,5);
        Tile tile = new Tile(randomPositionX, randomPositionY);
        return tile;
    }

}