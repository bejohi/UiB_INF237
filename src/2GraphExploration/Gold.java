import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Solution for https://open.kattis.com/problems/gold
public class Gold {


    private final Scanner input = new Scanner(System.in);
    private FieldGraph graph;
    private Field startField;

    public static void main(String... args) {
        Gold gold = new Gold();
        gold.readCmLineParams();
        gold.setMatrixFieldsSafe();
        int points = gold.graph.breadthFirstSearchForPoints(gold.startField);
        System.out.println(points);
    }

    void readCmLineParams() {
        int width = input.nextInt();
        int height = input.nextInt();
        this.graph = new FieldGraph(height, width);

        for (int y = 0; y < height; y++) {
            final String lineValue = input.next();
            for (int x = 0; x < width; x++) {
                String fieldValue = String.valueOf(lineValue.charAt(x));
                FieldCategories categories = FieldCategories.getMatchingCategories(fieldValue);
                Field field = new Field(categories, x, y);
                this.graph.matrix.get(y).add(x, field);
                if(categories == FieldCategories.PLAYER){
                    this.startField = field;
                }
            }
        }
    }

    void setMatrixFieldsSafe(){
        for(final List<Field> row : this.graph.matrix){
            for(final Field field : row){
                field.isSafe = this.graph.isSafe(field.x,field.y);
            }
        }
    }
}

class Field {
    final FieldCategories fieldCategories;
    final int x;
    final int y;
    boolean visited;
    boolean isSafe;


    Field(FieldCategories fieldCategories, int x, int y) {
        this.fieldCategories = fieldCategories;
        this.x = x;
        this.y = y;
        this.visited = false;
        this.isSafe = true;
    }

    @Override
    public String toString() {
        return (this.y +1) + "|" + (this.x +1);
    }
}

class FieldGraph {
    final List<List<Field>> matrix;
    final int height;
    final int width;

    FieldGraph(final int height, final int width) {
        this.height = height;
        this.width = width;
        this.matrix = new ArrayList<>();
        for (int y = 0; y < this.height; y++) {
            matrix.add(new ArrayList<>());
        }
    }

    boolean isSafe(int x, int y){
        if (x > 0) {
            Field field = this.matrix.get(y).get(x-1);
            if (field.fieldCategories == FieldCategories.TRAP) {
                return false;
            }
        }
        if (y > 0) {
            Field field = this.matrix.get(y-1).get(x);
            if (field.fieldCategories == FieldCategories.TRAP) {
                return false;
            }
        }

        if (y < height - 1) {
            Field field = this.matrix.get(y + 1).get(x);
            if (field.fieldCategories == FieldCategories.TRAP) {
                return false;
            }
        }

        if (x < width - 1) {
            Field field = this.matrix.get(y).get(x + 1);
            if (field.fieldCategories == FieldCategories.TRAP) {
                return false;
            }
        }

        return true;
    }

    List<Field> getNeighbours(int x, int y) {
        boolean safe = true;
        List<Field> neighbours = new ArrayList<>();
        if (x > 0) {
            Field field = this.matrix.get(y).get(x-1);
            if (field.fieldCategories != FieldCategories.WALL && field.fieldCategories != FieldCategories.TRAP) {
                neighbours.add(field);
            }
        }
        if (y > 0) {
            Field field = this.matrix.get(y-1).get(x);
            if (field.fieldCategories != FieldCategories.WALL && field.fieldCategories != FieldCategories.TRAP) {
                neighbours.add(field);
            }
        }

        if (y < height - 1) {
            Field field = this.matrix.get(y + 1).get(x);
            if (field.fieldCategories != FieldCategories.WALL && field.fieldCategories != FieldCategories.TRAP) {
                neighbours.add(field);
            }
        }

        if (x < width - 1) {
            Field field = this.matrix.get(y).get(x + 1);
            if (field.fieldCategories != FieldCategories.WALL && field.fieldCategories != FieldCategories.TRAP) {
                neighbours.add(field);
            }
        }

        return neighbours;
    }

    public int breadthFirstSearchForPoints(final Field startNode) {
        startNode.visited = true;
        int points = 0;

        Stack<Field> queue = new Stack<>();
        queue.add(startNode);
        List<Field> lastNeighbours = new ArrayList<>();
        while (!queue.isEmpty()) {
            Field currentNode = queue.pop();
            if(currentNode.fieldCategories == FieldCategories.GOLD){
                //System.out.println("Point");
                points++;
            }
            if(!currentNode.isSafe){
                //queue.removeAll(lastNeighbours);
                lastNeighbours.clear();
                continue;
            }
            //System.out.println("Way: " + currentNode);
            List<Field> neighbours = this.getNeighbours(currentNode.x,currentNode.y);
            for (final Field neighbour : neighbours) {
                if (!neighbour.visited) {
                    lastNeighbours.add(neighbour);
                    queue.push(neighbour);
                    neighbour.visited = true;
                }
            }
        }
        return points;
    }


    void printGraph() {
        for (int y = 0; y < this.height; y++) {
            this.matrix.get(y).forEach(field -> System.out.print(field.fieldCategories));
            System.out.println("\n");

        }
    }
}

enum FieldCategories {
    WALL("#"),
    TRAP("T"),
    PLAYER("P"),
    NORMAL("."),
    GOLD("G"),;

    private final String value;

    FieldCategories(String value) {
        this.value = value;
    }

    static FieldCategories getMatchingCategories(final String symbol) {
        switch (symbol) {
            case "#":
                return WALL;
            case "T":
                return TRAP;
            case "P":
                return PLAYER;
            case ".":
                return NORMAL;
            case "G":
                return GOLD;
            default:
                throw new IllegalArgumentException("No matching FieldCategorie found");
        }
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}