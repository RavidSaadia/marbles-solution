import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class SolutionManager {


    public static final int NUN = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int BLUE = 3;
    public static final int GREEN = 4;
    public static final int SMALL_BOARD = 3;
    public static final int BIG_BOARD = 5;


    private char[][] start;
    private char[][] goal;
    private double time;
    private State ans;
    private FindSolutionAlgo findSolAlgo;

    public SolutionManager(String inputFile) {
        this.findSolAlgo = readInput(inputFile);
        double start=(double)(System.currentTimeMillis())/1000;
        this.ans = findSolAlgo.findPath();
        double end=(double)(System.currentTimeMillis())/1000;
        this.time=end-start;

    }

    private FindSolutionAlgo readInput(String inputFile) {
        try {
            String algoType = "";
            boolean printOpenList = true;
            int boardSize = BIG_BOARD;

            File myObj = new File(inputFile);
            Scanner myReader = new Scanner(myObj);

            int n, m;
            String line = "";
            line = myReader.nextLine(); // line 1
            algoType = line;
            line = myReader.nextLine(); // line 2
            if (Objects.equals(line, "no open")) { //Objects.equals(line, "no open")
                printOpenList = false; // else, stay true
            }
            line = myReader.nextLine(); // line 3
            if (Objects.equals(line, "small")) {
                boardSize = SMALL_BOARD;
            }
            start = new char[boardSize][boardSize];
            goal = new char[boardSize][boardSize];

            for (int i = 0; i < boardSize; i++) {
                this.start[i] = myReader.nextLine().replaceAll(",", "").toCharArray();
            }
            line = myReader.nextLine(); // "Goal state:" line
            for (int i = 0; i < boardSize; i++) {
                this.goal[i] = myReader.nextLine().replaceAll(",", "").toCharArray();
            }
            myReader.close();

            switch (algoType) {
                case "BFS":
                    return new BFS(start, goal, printOpenList);
                case "DFID":
                    return new DFID(start, goal, printOpenList);
                case "A*":
                    return new Astar(start, goal, printOpenList);
                case "IDA*":
                    return new IDAstar(start, goal, printOpenList);
                case "DFBnB":
                    return new DFBnB(start, goal, printOpenList);
            }

        } catch (FileNotFoundException ex) {
            System.err.println("Error, file not found : " + ex);
        }
        catch (Exception ex){
            System.err.println("Error while reading the file : " + ex);

        }
        return null;
    }
}
