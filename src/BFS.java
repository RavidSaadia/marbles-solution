import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends FindSolutionAlgo {


    public BFS(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }







    /***
     * BFS algorithm - with open list and close list.
     *
     * @return the gaol state
     */
    @Override
    public State findPath() {
        Hashtable<State, State> closeList = new Hashtable<>();
        Hashtable<State, State> openList = new Hashtable<>();
        State start = new State(getStartState());
        Queue<State> queue = new LinkedList<>();
        queue.add(start);
        openList.put(start, start);
        if (Arrays.deepEquals(start.getBoard(), getGoal())) {//if start == goal
            return start;
        }
        while (!queue.isEmpty()) {
            if (isWithOpen()) {
                System.out.println("OPEN:\n" + queue);
            }
            State currState = queue.poll();
            assert currState != null;
            openList.remove(currState,currState);
            closeList.put(currState, currState);
            Queue<State> operation = currState.getLegalOperators();
            while (!operation.isEmpty()) {
                State son = operation.poll();
                if (closeList.get(son) != null || openList.get(son) != null) { // in closeList list
                    continue;
                }
                if (Arrays.deepEquals(son.getBoard(), getGoal())) {
                    return son;
                }
                queue.add(son);
                openList.put(son, son);
            }
        }
        return null;
    }
}
