package scripts;

import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.BasicQuery;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

/**
 *
 * @author matthieu
 */
public class Bank extends Task {

    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldActivate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {

        if (farFromObject(ctx.bank.nearest().tile(), 10)) {
            ctx.movement.step(ctx.bank.nearest());
            ctx.camera.turnTo(ctx.bank.nearest());
        } else {
            BasicQuery<GameObject> allBanks = ctx.objects.select().id(6943);
            ctx.camera.turnTo(allBanks.nearest().poll());
            allBanks.nearest().poll().interact("Bank");
            ctx.bank.depositInventory();
            ctx.bank.close();

        }
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

}
