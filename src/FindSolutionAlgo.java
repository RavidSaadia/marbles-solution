import java.util.Hashtable;

public abstract class FindSolutionAlgo {
    private final String EMPTY = "_";
    private Hashtable<Character, int[]> indexGaol = new Hashtable<>();
    private Hashtable<Character, Integer> PRICE = new Hashtable<>();

    private char[][] startState;
    private char[][] goal;
    private boolean withOpen;
    private int size = 5;

    public FindSolutionAlgo(char[][] startState, char[][] goal, boolean withOpen) {
        this.startState = startState;
        this.goal = goal;
        this.withOpen = withOpen;
        PRICE.put('R', 1);
        PRICE.put('Y', 1);
        PRICE.put('G', 10);
        PRICE.put('B', 2);
    }


    /***
     * Make a map that contain for each string in the goal its location,
     * for support o(1) time when we need it in the Heuristic function.
     */
    protected void makeGoalMap() {
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                indexGaol.put(goal[i][j], new int[]{i, j});
            }
        }
    }

    /***
     * The heuristic function for the relevant algorithms.
     *
     * @param state
     */
    protected void heuristic(State state) {
        int size = state.getBoard().length;
        boolean[][] stateChecked = new boolean[size][size];
        boolean[][] goalIsCatched = new boolean[size][size];
        int[][] minPath = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                minPath[i][j] = Integer.MAX_VALUE; // impossible distance just for initialize
            }
        }
//        int linar = 0;
        double sum = 0, avg;
        int mindist = 0;
        char[][] curr = state.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {// for every cell in the state
                if (curr[i][j] == '_'){
                    continue;
                }
                int kTemp = 0, lTemp = 0;
                kLoop:
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) { // for every cell in goal
                        if (curr[i][j] == goal[k][l] && !goalIsCatched[k][l]) {
                            int dist = calcDistance(i, j, k, l, curr[i][j]);
                            if (minPath[i][j] > dist) {
                                minPath[i][j] = dist;
                                kTemp = k;
                                lTemp = l;
                            if (dist==0){break kLoop;} // 0 is the smallest value...
                            }

                        }

                    }
                }
                goalIsCatched[kTemp][lTemp] = true;

            }
        }
        // sum the minimum paths of all the marbles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minPath[i][j] != Integer.MAX_VALUE){
                    sum += minPath[i][j];
                }
            }
        }
        state.setHeuristic(sum);
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
//        if (state.isTwoEmpty()) {
//            linar *= 3;
//            sum *= 3;
//        } else {
//            linar *= 5;
//            sum *= 5;
//        }
//        state.setHeuristic(state.getPrice() + sum + 2 * linar);
    }

    private int calcDistance(int i, int j, int k, int l, char color) {

        return PRICE.get(color) * (Math.abs(i - k) + Math.abs(j - l));
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
