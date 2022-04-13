import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends FindSolutionAlgo {


    public BFS(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }

    @Override
    public State findPath() {
        Queue<State> q = new LinkedList<>();
//        Hashtable<State, State> open = new Hashtable<>();
//        Hashtable<State, State> close = new Hashtable<>();
        Hashtable<State, State> open = new Hashtable<>();
        Hashtable<State, State> close = new Hashtable<>();
        State start = new State(getStartState());
        q.add(start);
        open.put(start, start);
        if (Arrays.deepEquals(start.getBoard(), getGoal())) {
            return start;
        }
        while (!q.isEmpty()) {
            if (isWithOpen()) {
                System.out.println("open\n" + q);
            }
            State n = q.poll();
            open.remove(n,n);
            close.put(n, n);
            assert n != null;
            Queue<State> operation = n.getLegalOperators();
            while (!operation.isEmpty()) {
                State son = operation.poll();
                if (close.get(son) != null || open.get(son) != null) { // in close list
                    continue;
                }

                if (Arrays.deepEquals(son.getBoard(), getGoal())) {
                    return son;
                }
                q.add(son);
                open.put(son, son);
            }
        }
        return null;
    }

}
