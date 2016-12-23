package scripts;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by andy2great on 12/23/2016.
 */
public class Return extends Task{

    private Tile initialTile;
    private final int RADIUS = 15;

    public Return(ClientContext ctx) {
        super(ctx);
        initialTile = ctx.players.local().tile();
    }

    @Override
    public boolean shouldActivate() {
        return shouldStepBack();
    }

    @Override
    public void execute() {
        ctx.movement.step(initialTile);
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
}
