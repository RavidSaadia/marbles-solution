import java.util.*;

public class DFBnB extends FindSolutionAlgo {


    public DFBnB(char[][] start, char[][] goal, boolean open) {
        super(start, goal, open);
    }















    /***
     * DFBnB algorithm - without close list,
     * but with stack and loop-avoidance
     *
     * @return the gaol state
     */
    @Override
    public State findPath() {
        LinkedList<State> stack = new LinkedList<>();
        Hashtable<State, State> openList = new Hashtable<>();
        State start = new State(getStartState());
        stack.push(start);
        openList.put(start, start);
        double t = Double.MAX_VALUE;
        State res = null;
        while (!stack.isEmpty()) {
            if (isWithOpen()) {
                System.out.println("OPEN LIST:\n" + stack);
            }
            // check every iteration the current state
            State currState = stack.pop();
            if (currState.isOut()) {
                openList.remove(currState, currState);
            } else {
                currState.setOut(true);
                stack.push(currState);
                Queue<State> operators = currState.getLegalOperators();
                LinkedList<State> Stayed = new LinkedList<>();
                PriorityQueue<State> pq = new PriorityQueue();
                while (!operators.isEmpty()) {
                    State son = operators.poll();
                    heuristic(son);
                    pq.add(son);
                }
                // as long the PriorityQueue is not empty we continue
                while (!pq.isEmpty()) {
                    State son = pq.poll();
                    int f = son.getHeuristic() + son.getPrice();
                    if ( f >= t) {
//                        double finalT = t;
//                        pq.removeIf(state -> (state.getPrice()+state.getHeuristic()) >= finalT);
                        pq.clear();
                    } else if (openList.get(son) != null && son.isOut()) {
                        pq.remove(son);
                        continue;
                    } else if (openList.get(son) != null && son.isOut()) {
                        if (openList.get(son).getHeuristic() + openList.get(son).getPrice() <= son.getHeuristic()+son.getPrice()) {
                            //להוסיף F ןG
                            continue;
                        } else {
                            stack.remove(openList.get(son));
                            openList.remove(openList.get(son), openList.get(son));
                            Stayed.addFirst(son);
                        }
                    } else if (Arrays.deepEquals(son.getBoard(), getGoal())) {
                        t = son.getPrice();
                        res = son;
                        pq.clear();
                    } else if (openList.get(son) == null) {
                        Stayed.addFirst(son);
                    }
                }
                while (!Stayed.isEmpty()) {
                    State son = Stayed.poll();
                    stack.addAll(Stayed);
                    openList.put(son, son);
                }
            }
        }
        return res;
    }
}