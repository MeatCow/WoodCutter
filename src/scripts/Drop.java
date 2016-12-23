package scripts;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;

/**
 *
 * @author matthieu
 */
public class Drop extends Task{

    private int[] dropable;
    
    public Drop(ClientContext ctx, int[] dropableIds) {
        super(ctx);
        dropable = dropableIds;
    }

    @Override
    public boolean shouldActivate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {
        if (ctx.game.tab() != Game.Tab.INVENTORY)  {
            ctx.game.tab(Game.Tab.INVENTORY);
        }
        for (Item inventoryItem: ctx.inventory.select().id(dropable)) {
            inventoryItem.interact("Drop");
        }
    }
    
}
