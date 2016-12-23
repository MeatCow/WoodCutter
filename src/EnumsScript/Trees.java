package EnumsScript;

/**
 * Created by Matt on 23/12/2016.
 */
public enum Trees {

    NORMAL(new int[]{1278,1276,2092,2091},0),
    OAK(new int[] {1751},15),
    WILLOW(new int[] {1750,1758,1756},30),
    YEW(new int[] {1753}, 60);

    int[] treeIds;
    int minLevel;
    public static final int MAX_IDS = 9; // Update this when you add more ids

    private Trees(int[] ids, int minLevel) {
        treeIds = ids;
        this.minLevel = minLevel;
    }

    public int[] getTreeIds() {
        return treeIds;
    }

    public int getMinLevel() {
        return minLevel;
    }

}
