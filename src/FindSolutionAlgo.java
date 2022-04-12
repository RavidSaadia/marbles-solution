import java.util.Hashtable;

public abstract class FindSolutionAlgo {
    private final String EMPTY = "_";
    private Hashtable<String, int[]> indexGaol = new Hashtable<>();
    private char[][] startState;


    private char[][] goal;
    private boolean printOpenList;
    private int size = 5;

    public FindSolutionAlgo(char[][] startState, char[][] goal, boolean printOpenList) {
        this.startState = startState;
        this.goal = goal;
        this.printOpenList = printOpenList;
    }




    public abstract State findPath(); // implemented by the Algo classes.

     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////    GETTERS & SETTERS     ////////////////////////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public char[][] getStartState() {
        return startState;
    }

    public void setStartState(char[][] startState) {
        this.startState = startState;
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
