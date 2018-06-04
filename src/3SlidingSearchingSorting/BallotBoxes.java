import java.io.*;
import java.util.*;

// Solution for https://open.kattis.com/problems/ballotboxes
public final class BallotBoxes {
    private final Kattio3 input = new Kattio3(System.in,System.out);
    private Queue<City> cities;
    private int numberOfCites;
    private int numberOfBallots;


    public static void main(String... args) {
        final BallotBoxes ballotBoxes = new BallotBoxes();
        final List<Integer> results = new ArrayList<>();
        while (!ballotBoxes.readNextCase()) {
            results.add(ballotBoxes.calculateResult());
        }
        results.forEach(System.out::println);

    }

    private boolean readNextCase() {
        this.numberOfCites = input.getInt();
        this.numberOfBallots = input.getInt();
        if (this.endOfTestCases()) {
            return true;
        }
        cities = new PriorityQueue<>();
        for (int i = 0; i < this.numberOfCites; i++) {
            cities.add(new City(input.getInt()));
        }
        return false;
    }

    private int calculateResult() {
        this.numberOfBallots -= numberOfCites;
        while (this.numberOfBallots > 0) {
            City city = this.cities.poll();
            city.addBallot();
            this.cities.add(city);
            this.numberOfBallots--;
        }
        return cities.poll().getPopPerBallot(); // return the maximum value.
    }

    private boolean endOfTestCases() {
        return this.numberOfBallots == -1 && this.numberOfCites == -1;
    }

    private static class City implements Comparable<City>{
        private final int population;
        private int numberOfBallots;

        private City(int population) {
            this.population = population;
            this.numberOfBallots = 1;
        }

        private int getPopPerBallot(){
            return (this.population + this.numberOfBallots - 1) / this.numberOfBallots;
        }

        private void addBallot(){
            this.numberOfBallots++;
        }

        @Override
        public int compareTo(City o) {
            int result = o.getPopPerBallot() - this.getPopPerBallot();
            return result;
        }
    }
}

class Kattio3 extends PrintWriter {
    public Kattio3(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null) {
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}