package scripts;

import org.powerbot.script.Locatable;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.BasicQuery;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.awt.*;

/**
 *
 * @author matthieu
 */
public class Bank extends Task {

    public Bank(ClientContext ctx) {
        super(ctx);
    }
    private int[] banks = {6943, 24101, 10060, 7409};
    private int[] bankable = {1511,1521}; // Logs

    @Override
    public boolean shouldActivate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {

        if (farFromObject(ctx.bank.nearest().tile(), 2)) {
            ctx.movement.step(ctx.bank.nearest());
            ctx.camera.turnTo(ctx.bank.nearest());
        } else {
            clickOnBank();
            depositInBank();
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

    private void bankAllLogs(){
        for (Item inventoryItem: ctx.inventory.select().id(bankable)) {
            inventoryItem.interact("Deposit-All");
        }
    }

    private void clickOnBank(){
        if (ctx.players.local().animation() == -1) {
            ctx.input.move(ctx.input.getLocation().x, ctx.input.getLocation().y - 10);
        }
        if (!ctx.bank.opened()) {
            BasicQuery<GameObject> allBanks = ctx.objects.select().id(banks);
            ctx.camera.turnTo(allBanks.nearest().poll());
            allBanks.nearest().poll().interact("Bank");
            ctx.camera.pitch(5);
        }
    }

    private void depositInBank(){
        if (ctx.bank.opened()) {
            bankAllLogs();
            ctx.bank.close();
        }
    }

}
