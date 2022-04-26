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
        //contains for etch color, the indexes in the <current state & goal state>
        HashSet<Character> colors = new HashSet<>();
        double sum = Double.MAX_VALUE, avg;
        Hashtable<ColorIndexes, ColorIndexes> compareMap = new Hashtable<>();
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

//        for (int i = 0; i < 4; i++) {
//
//
//        }


//        boolean[][] stateChecked = new boolean[size][size];
//        boolean[][] goalIsCatched = new boolean[size][size]; // mark the places that was taken already
//        int[][] minPath = new int[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                minPath[i][j] = Integer.MAX_VALUE; // impossible distance just for initialize
//            }
//        }
//        int mindist = 0;
//        char[][] curr = state.getBoard();
////        double sum = 0, avg;
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                // for every cell in the state
//                if (curr[i][j] == '_') {
//                    continue;
//                }
//
//                int kTemp = 0, lTemp = 0; // K/L = row/colum index of the goal state
//                kLoop:
//                for (int k = 0; k < size; k++) {
//                    for (int l = 0; l < size; l++) {
//                        // for every cell in goal
//                        if (curr[i][j] == goal[k][l] && !goalIsCatched[k][l]) {
//                            int dist = calcDistance(i, j, k, l, curr[i][j]);
//                            if (minPath[i][j] > dist) {
//                                minPath[i][j] = dist;
//                                kTemp = k;
//                                lTemp = l;
//                                if (dist == 0) {
//                                    break kLoop;
//                                } // 0 is the smallest value...
//                            }
//
//                        }
//
//                    }
//                }
//                goalIsCatched[kTemp][lTemp] = true;
//
//            }
//        }
//        // sum the minimum paths of all the marbles
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (minPath[i][j] != Integer.MAX_VALUE) {
//                    sum += minPath[i][j];
//                }
//            }
//        }
//        state.setHeuristic(sum);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static void permute(LinkedList<int[]> intArray, int start, Integer sum) {
//
//        for (int i = start; i < intArray.size(); i++) {
//            int[] temp = intArray.get(start);
//            intArray.set(start, intArray.get(i));
//            intArray.set(i, temp);
//            permute(intArray, start + 1, sum);
//            intArray.set(i, intArray.get(start));
//            intArray.set(start, temp);
//        }
//        if (start == intArray.size() - 1) {
//            int tempSum = ;
//            if (sum < tempSum) {
//                sum = tempSum;
//            }
//        }
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    private Integer setSum24(ColorIndexes curStateIndx, ColorIndexes goalStateIndx, char color) {
        Integer sum = Integer.MAX_VALUE;
        Integer tempSum;

        for (int i = 0; i < 4; i++) {
//            tempSum = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
//            if (sum > tempSum) {
//                sum = tempSum;
//            }
            for (int j = 0; j < 3; j++) {
//                tempSum = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
//                if (sum > tempSum) {
//                    sum = tempSum;
//                }
                for (int k = 0; k < 2; k++) {
                    tempSum = calcColorSum(curStateIndx.indxList, goalStateIndx.indxList, color);
                    if (sum > tempSum) {
                        sum = tempSum;
                    }
                    int[] temp = curStateIndx.indxList.remove(2);
                    curStateIndx.indxList.add(temp);
                }
                int[] temp = curStateIndx.indxList.remove(1);
                curStateIndx.indxList.add(temp);
            }
            int[] temp = curStateIndx.indxList.removeFirst();
            curStateIndx.indxList.add(temp);
        }

        return sum;
    }

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

    private Integer calcColorSum(LinkedList<int[]> currStates, LinkedList<int[]> goalStates, char color) {
        int sum = 0;
        for (int i = 0; i < currStates.size(); i++) {
            sum += calcDistance(currStates.get(i)[0], currStates.get(i)[1], goalStates.get(i)[0], goalStates.get(i)[1], color);
        }
        return sum;
    }

//    private void findBestMatch(LinkedList<int[]> indxList, LinkedList<int[]> indxList1, int sum, int k) {
//        for(int i = k; i < indxList.size(); i++){
//            java.util.Collections.swap(indxList, i, k);
//            findBestMatch(indxList,indxList1,sum, k+1);
//            java.util.Collections.swap(indxList, k, i);
//        }
//        if (k == indxList.size() -1){
//            if (calcDistance(indxList.)){
//
//            };
//        }
//
//    }


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
