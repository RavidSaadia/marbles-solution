import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class State {

    private class EmptyPos {
        private int i;
        private int j;
        private char color = ' ';


        private char direction;

        public EmptyPos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public EmptyPos(EmptyPos other) {
            this.i = other.i;
            this.j = other.j;
            this.color = other.color;
            this.direction = other.direction;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public char getDirection() {
            return direction;
        }

        public void setDirection(char direction) {
            this.direction = direction;
        }

        public char getColor() {
            return color;
        }

        public void setColor(char color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x1=" + i +
                    ", y1=" + j +
                    ", color=" + color +
                    ", direction=" + direction +
                    '}';
        }
    }

    public static int MOVES_NUMBER = 0;
    private char[][] board;
    private State father = null;


    private int id;
    private int price = 0;
    private double heuristic = 0;
    private String step = "";
    private String path = "";
    private int level = 0;
    private LinkedList<EmptyPos> emptyPos;//num of empty position
    //    private boolean[][] emptyMatrix;
    private boolean out = false;
    private Hashtable<Character, Character> isOpposite = new Hashtable<>();
    private final char EMPTY = '_';
    private int boardSize;

//private int

    public State(char[][] board, State father, int level, int price, double heuristic, String path, String step) {
        boardSize = board.length;
        this.board = board;
        this.father = father;
        this.price = price;
        this.heuristic = heuristic;
        this.path = path;
        this.level = level + 1;
        this.step = step;
        emptyPos = new LinkedList<>();
//        emptyMatrix = new boolean[boardSize][boardSize];

        this.id = ++MOVES_NUMBER; //+1 to MOVES_NUMBER and save as id of this state.
        updateEmptyPos();
//        updateOpposite();

    }


    public State(char[][] board) {
        this.board = board;
        boardSize = board.length;
        emptyPos = new LinkedList<>();
//        emptyMatrix = new boolean[boardSize][boardSize];
//        positions = new Position[(boardSize-3)*3+3]; // num of empty places: if(boardSize=3)->3 if(boardSize=5)->9
        this.id = ++MOVES_NUMBER; //+1 to MOVES_NUMBER and save as id of this state.
        updateEmptyPos();

    }

    /**
     * @return the states that we can go to.
     */
    public Queue<State> getLegalOperators() { //getSuccessors
        Queue<State> legalOperators = new LinkedList<>();

        for (EmptyPos p : emptyPos) {
            left(legalOperators, p);
            right(legalOperators, p);
            up(legalOperators, p);
            down(legalOperators, p);
        }

        return legalOperators;
    }


    private void down(Queue<State> leagalOperators, EmptyPos p) {
        p.setDirection('D');
        changeAndInsert(leagalOperators, p);


    }

    private void up(Queue<State> leagalOperators, EmptyPos p) {
        p.setDirection('U');
        changeAndInsert(leagalOperators, p);

    }

    private void right(Queue<State> legalOperators, EmptyPos p) {
        p.setDirection('R');
        changeAndInsert(legalOperators, p);

    }

    private void left(Queue<State> leagalOperators, EmptyPos p) {
        p.setDirection('L');
        changeAndInsert(leagalOperators, p);
    }

    /**
     * Calculate the new state and insert him to the legalOperators.
     *
     * @param legalOperators
     * @param p
     */
    private void changeAndInsert(Queue<State> legalOperators, EmptyPos p) {
        if (emptyOrEdge(p) || isFather(p)) {
            return;
        }


        State son = new State(next, this, this.level, price, w, path, step, _p.getX1(), _p.getY1(), _p.getX2(), _p.getY2());
        legalOperators.add(son);
    }

    private boolean isFather(EmptyPos p) {
        char[][] father = deepBoardCopy(this.board);
        makeMove(father, p);
        if (isBoardEquals(this.board, father)) {
            return true;
        }
        return false;
    }

    /**
     * check if the two boards are equals.
     *
     * @param board1 board1
     * @param board2 board2
     * @return true if so.
     */
    private boolean isBoardEquals(char[][] board1, char[][] board2) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board1[i][j] != board2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * transfer the old board to the new one according the pos.
     *
     * @param oldBoard board before change
     * @param p        position
     * @return the new board.
     */
    private char[][] makeMove(char[][] oldBoard, EmptyPos p) {

        switch (p.getDirection()) {
            case 'U':
                swap(oldBoard, p.getI(), p.getJ(), p.getI() - 1, p.getJ());
            case 'D':
                swap(oldBoard, p.getI(), p.getJ(), p.getI() + 1, p.getJ());

            case 'L':
                swap(oldBoard, p.getI(), p.getJ(), p.getI(), p.getJ() - 1);

            case 'R':
                swap(oldBoard, p.getI(), p.getJ(), p.getI(), p.getJ() + 1);

        }
        return oldBoard;
    }

    /**
     * swap the "chars" in the oldBoard.
     *
     * @param oldBoard board before change
     * @param i        char1
     * @param j        char1
     * @param i1       char2
     * @param j1       char2
     */
    private void swap(char[][] oldBoard, int i, int j, int i1, int j1) {
        char temp = oldBoard[i][j];
        oldBoard[i][j] = oldBoard[i1][j1];
        oldBoard[i1][j1] = temp;
    }

    private char[][] deepBoardCopy(char[][] board) {
        char[][] ans = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    /**
     * check if the action is legal - if we try to switch with empty place,
     * or we are edge in the board edge
     *
     * @param p the empty place we want to move according to p.getDirection()
     * @return true if the action is not legal.
     */
    private boolean emptyOrEdge(EmptyPos p) {
        switch (p.getDirection()) {
            case 'U':
                if (p.getI() == 0 || (board[p.getI() - 1][p.getJ()] == EMPTY)) {
                    return true;
                }
            case 'D':
                if (p.getI() == boardSize - 1 || (board[p.getI() + 1][p.getJ()] == EMPTY)) {
                    return true;
                }
            case 'L':
                if (p.getJ() == 0 || (board[p.getI()][p.getJ() - 1] == EMPTY)) {
                    return true;
                }
            case 'R':
                if (p.getJ() == boardSize - 1 || (board[p.getI()][p.getJ() + 1] == EMPTY)) {
                    return true;
                }
                break;
        }
        return false;
    }


    private void updateEmptyPos() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == EMPTY) {
                    emptyPos.add(new EmptyPos(i, j));
//                    emptyMatrix[i][j] = true;
                }
            }
        }
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    /***
     * @return the father of this state
     */
    public State getFather() {
        return father;
    }

    /***
     * @return the path to the state
     */
    public String getPath() {
        return path;
    }

    /***
     * set the path to the state
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /***
     * @return the greed of this state
     */
    public char[][] getBoard() {
        return board;
    }

    /***
     * @return the id of this state(which is the amount of stats).
     */
    public int getId() {
        return id;
    }

    public double getHeuristic() {
        return heuristic;
    }


//    public boolean[][] getEmpty_pos() {
//        return empty_pos;
//    }
//
//    public void setEmpty_pos(boolean[][] empty_pos) {
//        this.empty_pos = empty_pos;
//    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /***
     * set he heuristic value of this state(only for informed algorithms).
     * @param heuristic heuristic value
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }


    /***
     * @return the price up to this state.
     */
    public int getPrice() {
        return price;
    }

    /***
     * set the ptice of this state
     * @param price price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /***
     * @return deep copy of this board.
     */
    private char[][] copyBoard() {
        char[][] res = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                res[i][j] = board[i][j];

            }
        }
        return res;
    }


    //
    //
    public void State1(char[][] board, State father, int level, int price, double heuristic, String path, String step) {
    }


    private boolean isNotFather() {
        return false;
    }

}
