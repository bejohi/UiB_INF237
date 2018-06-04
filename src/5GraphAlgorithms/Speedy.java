import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


// Solution for https://open.kattis.com/problems/speedyescape
public final class Speedy {

    private static final double POLICE_SPEED = 160;
    private static final int NOT_VISITED = -1;
    private final static Scanner input = new Scanner(System.in);
    private final static double EPSILON = 0.0000001;
    private final static String IMPOSSIBLE_OUTPUT = "IMPOSSIBLE";
    private int nIntersections;
    private int nHighwayExits;
    private int nRoads;
    private int startPolice;
    private int startBrothers;
    private List<List<Node>> adjList;
    private int[] highwayExits;
    private double[] policeArrival;
    private int totalDistanceCounter;

    public static void main(String... args) {
        final Speedy speedy = new Speedy();
        speedy.init();
        speedy.dijkstraPolice();
        speedy.binarySearch();
    }

    private void init() {
        this.nIntersections = input.nextInt();
        this.nRoads = input.nextInt();
        this.nHighwayExits = input.nextInt();
        this.highwayExits = new int[this.nHighwayExits];

        this.adjList = new ArrayList<>(this.nIntersections);

        for (int i = 0; i < this.nIntersections; i++) {
            this.adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < this.nRoads; i++) {
            final int nodeIndex1 = input.nextInt() - 1;
            final int nodeIndex2 = input.nextInt() - 1;
            final int distance = input.nextInt();
            this.totalDistanceCounter += distance;
            final Node node1 = new Node(nodeIndex1, distance);
            final Node node2 = new Node(nodeIndex2, distance);
            this.adjList.get(nodeIndex1).add(node2);
            this.adjList.get(nodeIndex2).add(node1);
        }

        for (int i = 0; i < this.nHighwayExits; i++) {
            this.highwayExits[i] = input.nextInt() - 1;
        }

        this.startBrothers = input.nextInt() - 1;
        this.startPolice = input.nextInt() - 1;


        this.policeArrival = new double[this.nIntersections];
    }

    private void dijkstraPolice() {
        double[] distances = new double[this.nIntersections];
        for (int i = 0; i < this.nIntersections; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[this.startPolice] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((i1, i2) -> (int) (distances[i1] - distances[i2]));
        for (int i = 0; i < this.nIntersections; i++) {
            queue.add(i);
        }
        while (!queue.isEmpty()) {
            final int currentNodeIndex = queue.poll();
            final List<Node> neighbours = this.adjList.get(currentNodeIndex);
            for (final Node neighbour : neighbours) {
                final double newDistance = neighbour.distance + distances[currentNodeIndex];
                if (newDistance < distances[neighbour.index]) {
                    distances[neighbour.index] = newDistance;
                    queue.remove(neighbour.index);
                    queue.add(neighbour.index);
                }
            }
        }

        for (int i = 0; i < this.nIntersections; i++) {
            this.policeArrival[i] = (distances[i] / 10) / POLICE_SPEED; // TODO: Check formula
        }

    }

    private boolean dijkstraBrothers(final double currentSpeed) {
        double[] distances = new double[this.nIntersections];
        double[] biggestDifference = new double[this.nIntersections];
        for (int i = 0; i < this.nIntersections; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            biggestDifference[i] = Double.NEGATIVE_INFINITY;
        }
        distances[this.startBrothers] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((i1, i2) -> (int) (distances[i1] - distances[i2]));
        for (int i = 0; i < this.nIntersections; i++) {
            queue.add(i);
        }

        while (!queue.isEmpty()) {
            final int currentNodeIndex = queue.poll();
            final List<Node> neighbours = this.adjList.get(currentNodeIndex);
            for (final Node neighbour : neighbours) {
                final double newDistance = neighbour.distance + distances[currentNodeIndex];
                final double newTime = (newDistance / 10) / currentSpeed;
                if (newTime >= this.policeArrival[neighbour.index]) {
                    biggestDifference[neighbour.index] = newTime - this.policeArrival[neighbour.index];
                    continue;
                }
                if (newDistance < distances[neighbour.index]) {
                    distances[neighbour.index] = newDistance;
                    biggestDifference[neighbour.index] = this.policeArrival[neighbour.index] - newTime;
                    queue.remove(neighbour.index);
                    queue.add(neighbour.index);
                }
            }
        }

        for (int i = 0; i < this.nHighwayExits; i++) {
            final int highwayIndex = this.highwayExits[i];
            if (distances[highwayIndex] != Double.POSITIVE_INFINITY) {
                return true;
            }
        }
        return false;

    }

    private void binarySearch() {
        double highSpeed = Math.pow(this.totalDistanceCounter,2) * Math.pow(POLICE_SPEED,2);
        double lowSpeed = 0.0000001;
        double currentSpeed = lowSpeed + ((highSpeed - lowSpeed) / 2);
        boolean isPossible = false;


        while(highSpeed - lowSpeed > EPSILON) {
            final boolean escapePossible = this.dijkstraBrothers(currentSpeed);
            if (escapePossible) {
                highSpeed = currentSpeed;
                currentSpeed = lowSpeed + ((currentSpeed - lowSpeed) / 2);
                isPossible = true;
            } else {
                lowSpeed = currentSpeed;
                currentSpeed = currentSpeed + ((highSpeed - currentSpeed) / 2);
            }
        }

        if (!isPossible) {
            System.out.println(IMPOSSIBLE_OUTPUT);
        } else {
            System.out.println(currentSpeed);
        }

    }

    private static final class Node {
        private final int index;
        private final int distance;

        private Node(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }
    }
}