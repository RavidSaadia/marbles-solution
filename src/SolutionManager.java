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


    private String algoType = "";
    private boolean printOpenList = true;
    private int size = 5;
    private char[][] start;
    private char[][] goal;


    public SolutionManager(String inputFile) {
        readInput(inputFile);


    }

    private FindSolutionAlgo readInput(String inputFile) {
        try {

            File myObj = new File(inputFile);
            Scanner myReader = new Scanner(myObj);
            boolean open = false;
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
                size = 3;
            }
            start = new char[size][size];
            goal = new char[size][size];

            for (int i = 0; i < size; i++) {
                this.start[i] = myReader.nextLine().replaceAll(",", "").toCharArray();
            }
            line = myReader.nextLine(); // "Goal state:" line
            for (int i = 0; i < size; i++) {
                this.goal[i] = myReader.nextLine().replaceAll(",", "").toCharArray();
            }
            myReader.close();

            switch (algoType) {
                case "BFS":
                    return new BFS(start, goal, open);
                case "DFID":
                    return new DFID(start, goal, open);
                case "A*":
                    return new Astar(start, goal, open);
                case "IDA*":
                    return new IDAstar(start, goal, open);
                case "DFBnB":
                    return new DFBnB(start, goal, open);
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
