import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

public abstract class FindSolutionAlgo {

    public static final Hashtable<Character, Integer> PRICE = new Hashtable<>();

    private char[][] startState;
    private char[][] goal;
    private boolean withOpen;
    private int size;

    public FindSolutionAlgo(char[][] startState, char[][] goal, boolean withOpen) {
        size = goal.length;
        this.startState = startState;
        this.goal = goal;
        this.withOpen = withOpen;
        PRICE.put('R', 1);
        PRICE.put('Y', 1);
        PRICE.put('G', 10);
        PRICE.put('B', 2);
    }

    /**
     * This class is for save the indexes of all the marbles
     * by there color.
     * That for we can compare all the possibilities to match
     * each pair of marbles from the current state and the goal state
     * (in big board there are 24 possibilities for each color = 4!)
     */
    private class ColorIndexes {
        //contains the colors that we saw already
//      HashSet<Character> colors = new HashSet<>();
        char color;
        //contains for etch color, the indexes in the <current state & goal state>
        public LinkedList<int[]> indxList;

        public ColorIndexes(char color) {
            this.color = color;
            indxList = new LinkedList<int[]>();

        }


    }


    /***
     * The heuristic function for the relevant algorithms.
     *
     * @param state
     */
    protected void heuristic(State state) {
        //contains the colors that we already handle.
        HashSet<Character> colors = new HashSet<>();
        //contains for etch color, the indexes in the <current state & goal state>
        Hashtable<ColorIndexes, ColorIndexes> compareMap = new Hashtable<>();
        //set the compareMap
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char color = state.getBoard()[i][j];
                if (!colors.contains(color) && color != '_') {
                    colors.add(color);
                    ColorIndexes stateIndexes = setColorInMap(color, state.getBoard());
                    ColorIndexes goalIndexes = setColorInMap(color, goal);
                    compareMap.put(stateIndexes, goalIndexes);
                }
            }
        }
        state.setHeuristic(calcHeuristic(compareMap));// set the lower heuristic
    }

    /**
     * calculate the min heuristic by compare all matches.
     *
     * @param compareMap
     * @return
     */
    private int calcHeuristic(Hashtable<ColorIndexes, ColorIndexes> compareMap) {
        final int[] heuristic = {0};
        Hashtable<Character, Integer> sumMap = new Hashtable<>();
        compareMap.forEach((curStateIndx, goalStateIndx)
                -> {
            char color = curStateIndx.color;

            Integer sum;
            if (size == 3) {
                sum = setSum2(curStateIndx, goalStateIndx, color);
            } else {
                sum = setSum24(curStateIndx, goalStateIndx, color);
            }
            sumMap.put(color, sum);
            heuristic[0] += sumMap.get(color);
        });
        return heuristic[0];
    }

    /**
     * compare between 24 options: (x,y,z,w)...(w,z,y,x) = 4! = 24
     * by changing the curStateIndx order 24 times and check every time
     * if the indexes compare get better result then the others.
     * we assume that every color appears at least 4 times.
     * @param curStateIndx
     * @param goalStateIndx
     * @param color the color
     * @return
     */
    private Integer setSum24(ColorIndexes curStateIndx, ColorIndexes goalStateIndx, char color) {
        Integer sum = Integer.MAX_VALUE;
        Integer tempSum;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 2; k++) {
                    tempSum = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
                    if (sum > tempSum) {
                        sum = tempSum;
                    }
                    // swap between the third and forth places.
                    int[] temp = curStateIndx.indxList.remove(2);
                    curStateIndx.indxList.add(temp);
                }
                // swap between the second and third places.
                int[] temp = curStateIndx.indxList.remove(1);
                curStateIndx.indxList.add(temp);
            }
            // swap between the first and second places.
            int[] temp = curStateIndx.indxList.removeFirst();
            curStateIndx.indxList.add(temp);
        }
        return sum;
    }

    /**
     * compare between 2 options: (x,y) and (y,x) = 2!
     *
     * @param curStateIndx
     * @param goalStateIndx
     * @param color
     * @return
     */
    private Integer setSum2(ColorIndexes curStateIndx, ColorIndexes goalStateIndx, char color) {
        Integer sum1;
        Integer sum2;
        sum1 = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
        // swapping
        int[] temp = curStateIndx.indxList.removeFirst();
        curStateIndx.indxList.add(temp);
        sum2 = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
        return Math.min(sum1, sum2);
    }

    /**
     * calculate the price by the color and the distance.
     * calculate as the path is all clear, and we move the marbles
     * straight to the target.
     *
     * @param currStates
     * @param goalStates
     * @param color
     * @return
     */
    private Integer calcColorSum(LinkedList<int[]> currStates, LinkedList<int[]> goalStates, char color) {
        int sum = 0;
        for (int i = 0; i < currStates.size(); i++) {
            sum += calcDistance(currStates.get(i)[0], currStates.get(i)[1], goalStates.get(i)[0], goalStates.get(i)[1], color);
        }
        return sum;
    }

    private ColorIndexes setColorInMap(char color, char[][] board) {
        ColorIndexes res = new ColorIndexes(color);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == color) {
                    res.indxList.add(new int[]{i, j});
                }
            }
        }
        return res;
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
