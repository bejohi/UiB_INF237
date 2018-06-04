import java.io.*;
import java.util.StringTokenizer;

// Solution for https://open.kattis.com/problems/oddities
public class Oddities {
    public static boolean isEven(int number){
        return number % 2 == 0;
    }

    public static void processInputNumber(Kattio io, int number){
        boolean isEven = isEven(number);
        io.println(isEven ? number + " is even" : number + " is odd");
    }


    public static void main(String... args){
        try (Kattio io = new Kattio(System.in, System.out)) {
            int nLines = io.getInt();
            while (io.hasMoreTokens() && nLines > 0) {
                processInputNumber(io,io.getInt());
                nLines--;
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

