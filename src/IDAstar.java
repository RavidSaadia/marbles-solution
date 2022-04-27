import java.util.*;

public class IDAstar extends FindSolutionAlgo{


    public IDAstar(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }














    /***
     * IDA* algorithm - without close list,
     * but with stack and loop-avoidance
     *
     * @return the gaol state
     */
    @Override
    public State findPath() {

        Stack<State> stack = new Stack<>();
        Hashtable<State, State> openList = new Hashtable<>();
        State start = new State(getStartState());
        heuristic(start);
        double t = start.getHeuristic();
        while (t != Double.MAX_VALUE) {
            double minF = Double.MAX_VALUE;
            start.setOut(false);
            stack.push(start);
            openList.put(start, start);

            while (!stack.isEmpty()) {
                if (isWithOpen()) {
                    System.out.println("OPEN LIST:\n" + stack);
                }
                State currState = stack.pop();
                if (currState.isOut()) {
                    openList.remove(currState, currState);
                } else {
                    currState.setOut(true);
                    stack.push(currState);
                    if (isWithOpen()) {
                        System.out.println("OPEN LIST:\n" + stack);
                    }

                    Queue<State> operators = currState.getLegalOperators();
                    while (!operators.isEmpty()) {
                        State son = operators.poll();
                        heuristic(son);
                        if (son.getHeuristic() > t) {
                            minF = Math.min(son.getHeuristic(), minF);
                            continue;
                        } else if (openList.get(son) != null && openList.get(son).isOut()) {
                            continue;
                        } else if (openList.get(son) != null && !openList.get(son).isOut()) {
                            if (openList.get(son).getHeuristic() > son.getHeuristic()) {
                                stack.remove(openList.get(son));
                                openList.remove(openList.get(son), openList.get(son));
                            } else {
                                continue;
                            }
                        }
                        if (Arrays.deepEquals(son.getBoard(), getGoal())) {
                            return son;
                        }
                        stack.push(son);
                        openList.put(son, son);
                    }
                }
            }
            t = minF;

        }
        return null;
    }
}
