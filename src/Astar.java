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
        Hashtable<State, State> closeList = new Hashtable<>();
        Hashtable<State, State> openList = new Hashtable<>();
        State start = new State(getStartState());

        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(start);
        openList.put(start, start);
        while (!queue.isEmpty()) {
            if (isWithOpen()){
                System.out.println("OPEN LIST:\n" + queue);
            }
           // check every iteration the current state
            State currState = queue.poll();
            assert currState != null;
            openList.remove(currState, currState);
            if (Arrays.deepEquals(getGoal(), currState.getBoard())) {
                return currState;
            }
            closeList.put(currState, currState);
            Queue<State> operators = currState.getLegalOperators();
            while (!operators.isEmpty()) {
                State son = operators.poll();
                heuristic(son);


                if (openList.get(son) != null && closeList.get(son) != null) {
                   int f = openList.get(son ).getHeuristic() +openList.get(son ).getPrice();
                    if (openList.get(son ).getHeuristic() > son.getHeuristic()) {
                        openList.replace(son , son);
                    }
                } else {
                    openList.put(son , son);
                    queue.add(son);
                }
            }
        }
        return null;
    }
}



