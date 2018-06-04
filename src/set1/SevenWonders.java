import java.io.*;
import java.util.StringTokenizer;

// Solution for https://open.kattis.com/problems/sevenwonders
public class SevenWonders {

    public static int countPoints(CharSequence inputSequenz) {
        int tCounter = 0;
        int cCounter = 0;
        int gCounter = 0;
        for (int i = 0; i < inputSequenz.length(); i++) {
            switch (inputSequenz.charAt(i)) {
                case 'T':
                    tCounter++;
                    break;
                case 'G':
                    gCounter++;
                    break;
                case 'C':
                    cCounter++;
                    break;
                default:
                    throw new IllegalArgumentException("A not supported char was found");
            }
        }

        int result = 0;
        result += tCounter * tCounter;
        result += cCounter * cCounter;
        result += gCounter * gCounter;
        result += Math.min(Math.min(tCounter, gCounter), cCounter) * 7;

        return result;

    }

    public static void main(String... args) {
        try (Kattio io = new Kattio(System.in, System.out)) {
            io.println(countPoints(io.getWord()));
        }
    }
}

class Kattio extends PrintWriter {
    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
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

