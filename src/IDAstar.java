import java.util.*;

public class IDAstar extends FindSolutionAlgo{


    public IDAstar(char[][] start, char[][] goal, boolean open) {
        super(start,goal,open);

    }

    @Override
    public State findPath() {

        Hashtable<State, State> open = new Hashtable<>();
        Stack<State> stack = new Stack<>();
        State start = new State(getStartState());
        heuristic(start);
        double t = start.getHeuristic();
        while (t != Double.MAX_VALUE) {
            double minf = Double.MAX_VALUE;
            start.setOut(false);
            stack.push(start);
            open.put(start, start);

            while (!stack.isEmpty()) {
                if (isWithOpen()) {
                    System.out.println("WITH OPEN\n" + stack);
                }
                State n = stack.pop();
                if (n.isOut()) {
                    open.remove(n, n);
                } else {
                    n.setOut(true);
                    stack.push(n);
                    if (isWithOpen()) {
                        System.out.println("open\n" + stack);
                    }

                    Queue<State> opertion = n.getLegalOperators();
                    while (!opertion.isEmpty()) {
                        State son = opertion.poll();
                        heuristic(son);
                        if (son.getHeuristic() > t) {
                            minf = Math.min(son.getHeuristic(), minf);
                            continue;
                        } else if (open.get(son) != null && open.get(son).isOut()) {
                            continue;
                        } else if (open.get(son) != null && !open.get(son).isOut()) {
                            if (open.get(son).getHeuristic() > son.getHeuristic()) {
                                stack.remove(open.get(son));
                                open.remove(open.get(son), open.get(son));
                            } else {
                                continue;
                            }
                        }
                        if (Arrays.deepEquals(son.getBoard(), getGoal())) {
                            return son;
                        }
                        stack.push(son);
                        open.put(son, son);
                    }
                }
            }
            t = minf;

        }
        return null;
    }
}
