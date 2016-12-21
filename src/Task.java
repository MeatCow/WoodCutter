import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Matt on 20/12/2016.
 */
public abstract class Task<C> extends ClientAccessor {

    public Task(ClientContext ctx) {
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();
}
