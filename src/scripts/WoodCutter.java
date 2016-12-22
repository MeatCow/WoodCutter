package scripts;

import java.util.ArrayList;
import java.util.List;
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

    private boolean bank = true;

    private List<Task> tasks;

    @Override
    public void start() {
        tasks = new ArrayList();
        tasks.add(new Chop(ctx));
        if (bank) {
            tasks.add(new Bank(ctx));
        } else {
            tasks.add(new Drop(ctx));
        }
    }

    @Override
    public void poll() {
        boolean didNothing = false;
        for (Task t : tasks) {
            if (t.shouldActivate()) {
                t.execute();
                didNothing = true;
            }
            if (didNothing) {
                switchTabs();
            }
        }
    }

    /**
     * Switches tabs randomly
     */
    private void switchTabs() {
        if (Random.nextInt(0, 100) == 100 && ctx.game.tab() != Game.Tab.EQUIPMENT) {
            ctx.game.tab(Game.Tab.EQUIPMENT);
        } else if (Random.nextInt(0, 50) == 50 && ctx.game.tab() != Game.Tab.STATS) {
            ctx.game.tab(Game.Tab.STATS);
            ctx.input.move(680 + Random.nextInt(0, 50), 364 + Random.nextInt(0, 25));
        } else if (Random.nextInt(0, 20) == 20 && ctx.game.tab() != Game.Tab.INVENTORY) {
            ctx.game.tab(Game.Tab.INVENTORY);
        }
    }
}
