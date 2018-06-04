import java.io.*;
import java.util.StringTokenizer;

// Solution for https://open.kattis.com/problems/guess
public class GuessTheNumber {

    public static void main(String... args) {
        int currentNumber = 500;
        int highBorder = 1000;
        int lowBorder = 1;
        System.out.println(currentNumber);
        try (Kattio io = new Kattio(System.in, System.out)) {
            while(true){
                String input = io.getWord();
                switch (input){
                    case "lower":
                        highBorder = currentNumber -1;
                        currentNumber = (highBorder + lowBorder) / 2;
                        System.out.println(currentNumber);
                        break;
                    case "higher":
                        lowBorder = currentNumber + 1;
                        currentNumber = (highBorder + lowBorder) / 2;
                        System.out.println(currentNumber);
                        break;
                    case "correct":
                        return;
                    default:
                        throw new IllegalArgumentException(input + " is not supported.");
                }
            }
        }
    }
}

class Kattio extends PrintWriter {
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



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}