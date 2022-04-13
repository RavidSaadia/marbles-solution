import java.util.Arrays;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar extends FindSolutionAlgo {


    public Astar(char[][] start, char[][] goal, boolean withOpen) {
        super(start, goal, withOpen);

    }

    /***
     * The A* algorithm.
     * @return state gaol
     */
    @Override
    public State findPath() {
        makeGoalMap();
        Hashtable<State, State> open = new Hashtable<>();
        Hashtable<State, State> close = new Hashtable<>();
        State start = new State(this.getStartState());


        PriorityQueue<State> pq = new PriorityQueue();

        pq.add(start);
        open.put(start, start);
        while (!pq.isEmpty()) {
            if (isWithOpen()) {
                System.out.println("open\n" + pq);
            }

            State n = pq.poll();
            open.remove(n, n);
            if (Arrays.deepEquals(getGoal(), n.getBoard())) {
                return n;
            }
            close.put(n, n);
            Queue<State> operation = n.getLegalOperators();
            while (!operation.isEmpty()) {
                State son = operation.poll();
                heuristic(son);


                if (open.get(son) != null && close.get(son) != null) {
                    if (open.get(son).getHeuristic() > son.getHeuristic()) {
                        open.replace(son, son);
                    }
                } else {
                    open.put(son, son);
                    pq.add(son);
                }

            }
        }
        return null;
    }
}
