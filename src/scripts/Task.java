package scripts;

import org.powerbot.script.rt4.*;

/**
 *
 * @author matthieu
 */
public abstract class Task extends ClientAccessor {

    public Task(ClientContext ctx) {
        super(ctx);
    }
    
    public abstract boolean shouldActivate();
    public abstract void execute();
}
