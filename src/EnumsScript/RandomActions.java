package EnumsScript;

/**
 * Created by andy2great on 12/22/2016.
 */
public enum RandomActions {

        MISS_CLICK(0),
        TURNING_CAMERA(1),
        SWITCHING_TAB(2),
        SWITCHING_TAB2(3);

        private final int actionValue;

        private RandomActions(int actionValue) {
            this.actionValue = actionValue;
        }

        public int getActionValue() {
            return actionValue;
        }
}
