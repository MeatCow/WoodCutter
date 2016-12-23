package EnumsScript;


/**
 * Created by Matt on 23/12/2016.
 */
public enum Logs {
    Normal(1511),
    Oak(1521),
    Willow(1519),
    Yew(1515);

    int id;

    private Logs(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

}
