package scripts;

import java.awt.*;
import java.util.*;
import java.util.List;

import EnumsScript.*;
import gui.*;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

@Script.Manifest(
        name = "WoodCuttingChopChop", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> implements PaintListener{

    private ProgressPaint painter = new ProgressPaint(ctx); // If initialised in constructor, repaint() throws IOException
    private Options optionsWindow;
    private List<Task> tasks;

    @Override
    public void start() {
        optionsWindow = new Options();
        optionsWindow.setVisible(true);

        waitForOptions();

        int[] treeIds = getCutableTreeIds();
        int[] logIds = getDropableLogIds();
        Banks bank = getBankChoice();

        tasks = new ArrayList();

        tasks.add(new Chop(ctx, treeIds));
        tasks.add(new Return(ctx));
        if (bank != null) {
            tasks.add(new Bank(ctx, logIds));
        } else {
            tasks.add(new Drop(ctx, logIds));
        }

        tasks.add(new RandomEvent(ctx));
    }

    @Override
    public void poll() {
        for (Task t : tasks) {
            if (t.shouldActivate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        painter.drawProgress(g);
    }

    private void waitForOptions() {
        while (!optionsWindow.isReady()) ;
    }

    private int[] getCutableTreeIds() {
        List<Integer> cutableIds = new LinkedList();
//        if (optionsWindow.getYews()) {
//            for(int i:Trees.YEW.getTreeIds()) {
//                cutableIds.add(i);
//            }
//        }
//        if (optionsWindow.getWillows()) {
//            for(int i:Trees.WILLOW.getTreeIds()) {
//                cutableIds.add(i);
//            }
//        }
//        if (optionsWindow.getOaks()) {
//            for (int i:Trees.OAK.getTreeIds()) {
//                cutableIds.add(i);
//            }
//        }
//        if (optionsWindow.getNormalTrees()) {
//            for (int i:Trees.NORMAL.getTreeIds()){
//                cutableIds.add(i);
//            }
//        }
        return cutableIds.stream().mapToInt(i->i).toArray();
    }

    private int[] getDropableLogIds() {
        int[] dropableLogs = new int[Logs.AMOUNT_TYPES];
//        if (optionsWindow.getNormalTrees()) {
//            dropableLogs[0] = Logs.Normal.getId();
//        }
//        if (optionsWindow.getOaks()) {
//            dropableLogs[1] = Logs.Oak.getId();
//        }
//        if (optionsWindow.getWillows()) {
//            dropableLogs[2] = Logs.Willow.getId();
//        }
//        if (optionsWindow.getYews()) {
//            dropableLogs[3] = Logs.Yew.getId();
//        }
        return dropableLogs;
    }

    private Banks getBankChoice() {
        return optionsWindow.getBankChoices();
    }

}
