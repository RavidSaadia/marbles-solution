import java.util.Arrays;
import java.util.Hashtable;
import java.util.Queue;

public class DFID extends FindSolutionAlgo {
    private final State cutOff = new State(getGoal().length);//cutoff state

    public DFID(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }

    @Override
    public State findPath() {
        State start = new State(getStartState());
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Hashtable<State, State> open = new Hashtable<>();
            State res = Limited_DFS(start, i, open);

            if (res != cutOff) {
                return res;
            }
        }
        return null;
    }

    private State Limited_DFS(State n, int limit, Hashtable<State, State> open) {
        if (Arrays.deepEquals(n.getBoard(), getGoal())) {
            return n;
        } else if (limit ==0) {
            return cutOff;
        } else {
            if (isWithOpen()) {
                System.out.println("open\n" + open.values());
            }

            open.put(n, n);
            Queue<State> opertion = n.getLegalOperators();

            boolean isCutOff = false;

            while (!opertion.isEmpty()) {
                State son = opertion.poll();
                if (open.get(son) == null) {

                    State res = Limited_DFS(son, limit-1, open);
                    if (res == cutOff) {
                        isCutOff = true;
                    } else if (res != null) {

                        return res;
                    }
                }
            }

            open.remove(n, n);
            if (isCutOff) {
                return cutOff;
            }
            return null;
        }

    }
}
