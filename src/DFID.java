import java.util.Arrays;
import java.util.Hashtable;
import java.util.Queue;

public class DFID extends FindSolutionAlgo {
    private final State cutOff = new State(getGoal().length);

    public DFID(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }

    private State LimitDFS(State currState, int limit, Hashtable<Integer, State> openList) {
        if (Arrays.deepEquals(currState.getBoard(), getGoal())) {
            return currState;
        } else if (limit ==0) {
            return cutOff;
        } else {
            if (isWithOpen()) {
                System.out.println("OPEN LIST:\n" + openList.values());
            }

            openList.put(currState.getId(), currState);
            Queue<State> operators = currState.getLegalOperators();
            boolean isCutOff = false;

            while (!operators.isEmpty()) {
                State son = operators.poll();
                if (openList.get(son.getId()) == null) {
                    State res = LimitDFS(son, limit-1, openList);
                    if (res == cutOff) {
                        isCutOff = true;
                    } else if (res != null) {
                        return res;
                    }
                }
            }
            openList.remove(currState.getId(), currState);
            if (isCutOff) {
                return cutOff;
            }
            return null;
        }

    }


    /***
     * DFID algorithm - without close list,
     * but with loop-avoidance
     * @return the gaol state
     */
    @Override
    public State findPath() {

        State start = new State(getStartState());
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Hashtable<Integer, State> openList = new Hashtable<>();
            State res = LimitDFS(start, i, openList);
            if (res != cutOff) {
                return res;
            }
        }
        return null;
    }


}
