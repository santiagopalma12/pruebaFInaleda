package src.smarthire.structures;

public class NodeTrie {
    protected char key;
    protected boolean stop;
    protected NodeTrie left, center, right;

    public NodeTrie(char key) {
        this.key = key;
        this.stop = false;
        this.left = null;
        this.center = null;
        this.right = null;
    }
}
