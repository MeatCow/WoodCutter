package scripts;

import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by andy2great on 12/22/2016.
 */
public class RandomEvent extends  Task{

    public RandomEvent(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldActivate() {
        return org.powerbot.script.Random.nextInt(0, 10) == 10;
    }

    @Override
    public void execute() {
        startRandomEvent(org.powerbot.script.Random.nextInt(0, 10));
    }

    private void startRandomEvent(int i) {
        switch (i) {
            //case
        }
    }
}
