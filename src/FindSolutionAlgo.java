import java.util.Hashtable;

public abstract class FindSolutionAlgo {
    private final String EMPTY = "_";
    private Hashtable<String, int[]> indexGaol = new Hashtable<>();
    private char[][] start;


    private char[][] goal;
    private boolean printOpenList;
    private int size = 5;

    public FindSolutionAlgo(char[][] start, char[][] goal, boolean printOpenList) {
        this.start = start;
        this.goal = goal;
        this.printOpenList = printOpenList;
    }




    public abstract State findPath();

     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////    GETTERS & SETTERS     ////////////////////////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public char[][] getStart() {
        return start;
    }

    public void setStart(char[][] start) {
        this.start = start;
    }

    public char[][] getGoal() {
        return goal;
    }

    public void setGoal(char[][] goal) {
        this.goal = goal;
    }

    public boolean isPrintOpenList() {
        return printOpenList;
    }

    public void setPrintOpenList(boolean printOpenList) {
        this.printOpenList = printOpenList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
