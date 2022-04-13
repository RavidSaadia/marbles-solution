import java.util.Hashtable;

public abstract class FindSolutionAlgo {
    private final String EMPTY = "_";
    private Hashtable<Character, int[]> indexGaol = new Hashtable<>();
    private char[][] startState;


    private char[][] goal;
    private boolean withOpen;
    private int size = 5;

    public FindSolutionAlgo(char[][] startState, char[][] goal, boolean withOpen) {
        this.startState = startState;
        this.goal = goal;
        this.withOpen = withOpen;
    }


    /***
     * Make a map that contain for each string in the gaol its location,
     * for support o(1) time when we need it in the Heuristic function.
     */
    protected void makeGoalMap() {
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal[0].length; j++) {
                indexGaol.put(goal[i][j], new int[]{i, j});
            }
        }
    }
    /***
     * The heuristic function for the relevant algorithms.
     * sum(Manhattan(state))+2xlinear conflict(state)
     * @param state
     */
    protected void heuristic(State state) {




//
//        int linar = 0;
//        double sum = 0, avg;
//        char[][] curr = state.getBoard();
//        for (int i = 0; i < curr.length; i++) {
//            for (int j = 0; j < curr[0].length; j++) {
//                int[] index = indexGaol.get(curr[i][j]);
//                double m = manhattan(i, j, index[0], index[1]);
//                if (m != 0) {
//                    if (!curr[i][j].equals(EMPTY)) {
//                        sum += m;
//
//                    }
//                }
//            }
//        }
//        linar = findConflict(curr);
//        if(state.isTwoEmpty()){
//            linar*=3;
//            sum*=3;
//        }else{
//            linar*=5;
//            sum*=5;
//        }
//        state.setHeuristic(state.getPrice() + sum + 2 * linar);
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

    public boolean isWithOpen() {
        return withOpen;
    }

    public void setWithOpen(boolean withOpen) {
        this.withOpen = withOpen;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
