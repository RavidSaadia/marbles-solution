import java.util.Arrays;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar extends FindSolutionAlgo {


    public Astar(char[][] start, char[][] goal, boolean withOpen) {
        super(start, goal, withOpen);

    }






    /***
     * A* algorithm - with open list and close list.
     *
     * @return the gaol state
     */
    @Override
    public State findPath() {
        Hashtable<Integer, State> closeList = new Hashtable<>();
        Hashtable<Integer, State> openList = new Hashtable<>();
        State start = new State(this.getStartState());

        PriorityQueue<State> queue = new PriorityQueue();
        queue.add(start);
        openList.put(start.getId(), start);
        while (!queue.isEmpty()) {
            if (isWithOpen()){
                System.out.println("OPEN:\n" + queue);
            }
            State currState = queue.poll();
            assert currState != null;
            openList.remove(currState.getId(), currState);
            if (Arrays.deepEquals(getGoal(), currState.getBoard())) {
                return currState;
            }
            closeList.put(currState.getId(), currState);
            Queue<State> operation = currState.getLegalOperators();
            while (!operation.isEmpty()) {
                State son = operation.poll();
                heuristic(son);


                if (openList.get(son.getId()) != null && closeList.get(son.getId()) != null) {
                    if (openList.get(son.getId()).getHeuristic() > son.getHeuristic()) {
                        openList.replace(son.getId(), son);
                    }
                } else {
                    openList.put(son.getId(), son);
                    queue.add(son);
                }
            }
        }
        return null;
    }
}



