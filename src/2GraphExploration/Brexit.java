import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;

// Solution for https://open.kattis.com/problems/brexit
public final class Brexit {

    private static final String STAY_CMD = "stay";
    private static final String LEAVE_CMD = "leave";
    private final Kattio input = new Kattio(System.in, System.out);
    private boolean[] alreadyChecked;
    private BrexitGraph graph;
    private int numberOfCountries;
    private int numberOfRelations;
    private int homeCountry;
    private int firstLeaver;

    public static void main(String... args) {
        final Brexit brexit = new Brexit();
        brexit.createAndInitGraphFromCmdLineInput();
        final boolean willLeave = brexit.willLeave();
        if (willLeave) {
            System.out.println(LEAVE_CMD);
        } else {
            System.out.println(STAY_CMD);
        }
    }

    private boolean willLeave() {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(this.firstLeaver);
        this.graph.addLeaver(this.firstLeaver);
        while (!queue.isEmpty()) {
            int currentCountry = queue.poll();
            final List<Integer> relations = this.graph.getRelations(currentCountry);
            for (final int relation : relations) {
                if (this.graph.isLeaving(relation)) {
                    continue;
                }
                if (this.graph.isHalfOfRelationsLeaving(relation)) {
                    if (relation == this.homeCountry) {
                        return true;
                    }
                    if (!this.alreadyChecked[relation]) {
                        queue.add(relation);
                        this.alreadyChecked[relation] = true;
                        this.graph.addLeaver(relation);
                    }
                }
            }
        }

        return this.graph.isLeaving(homeCountry);
    }

    private BrexitGraph createAndInitGraphFromCmdLineInput() {
        this.numberOfCountries = input.getInt();
        this.numberOfRelations = input.getInt();
        this.homeCountry = input.getInt();
        this.firstLeaver = input.getInt();
        final BrexitGraph graph = new BrexitGraph(this.numberOfCountries);
        for (int i = 0; i < this.numberOfRelations; i++) {
            final int country1 = input.getInt();
            final int country2 = input.getInt();
            graph.addRelation(country1, country2);
        }
        this.graph = graph;
        this.alreadyChecked = new boolean[this.numberOfCountries + 1];
        return graph;
    }

}


class BrexitGraph {

    private final List<List<Integer>> adjList;
    private final int numberOfCountries;
    private final int[] numberOfNeigboursLeaving;
    private final boolean[] leaver;

    BrexitGraph(int numberOfCountries) {
        this.numberOfCountries = numberOfCountries;
        this.adjList = new ArrayList<>();
        for (int y = 0; y < this.numberOfCountries; y++) {
            this.adjList.add(new ArrayList<>());
        }
        this.leaver = new boolean[this.numberOfCountries + 1];
        this.numberOfNeigboursLeaving = new int[this.numberOfCountries + 1];
    }

    void addRelation(int country1, int country2) {
        this.adjList.get(country1 - 1).add(country2);
        this.adjList.get(country2 - 1).add(country1);
    }

    List<Integer> getRelations(int country) {
        return this.adjList.get(country - 1);
    }

    void addLeaver(final int country) {
        this.leaver[country] = true;
        for (int relation : this.getRelations(country)) {
            this.numberOfNeigboursLeaving[relation]++;
        }
    }

    boolean isLeaving(final int country) {
        return this.leaver[country];
    }

    boolean isHalfOfRelationsLeaving(int country) {
        return this.numberOfNeigboursLeaving[country] * 2 >= this.getRelations(country).size();
    }
}

class Kattio extends PrintWriter {
    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;
    Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) {
            }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}