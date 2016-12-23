package scripts;

import EnumsScript.RandomActions;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;

import java.awt.*;

/**
 * Created by andy2great on 12/22/2016.
 */
public class RandomEvent extends Task {

    private final int MISS_CLICK = 0;
    public RandomEvent(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldActivate() {
        return Random.nextInt(0, 50) == 0;
    }

    @Override
    public void execute() {
        startRandomEvent(Random.nextInt(0, 3));
    }

    private void startRandomEvent(int actionValue) {
        if (RandomActions.MISS_CLICK.getActionValue() == actionValue) {
            missClick();
        } else if (RandomActions.TURNING_CAMERA.getActionValue() == actionValue) {
            turningCamera();
        } else if (RandomActions.SWITCHING_TAB.getActionValue() == actionValue) {
            switchTabs();
        } else if (RandomActions.SWITCHING_TAB2.getActionValue() == actionValue) {
            switchTabs();
        }
    }

    private void missClick(){
        int mouseCurrentPositionX = ctx.input.getLocation().x;
        int mouseCurrentPositionY = ctx.input.getLocation().y;
        Point currentMousePosition = new Point(mouseCurrentPositionX, mouseCurrentPositionY);
        ctx.input.click(currentMousePosition, 0);
    }

    private void turningCamera(){
        ctx.camera.pitch(Random.nextInt(0,100));
        ctx.camera.angle(Random.nextInt(0,360));
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
