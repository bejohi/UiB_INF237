import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

// Solution for https://open.kattis.com/problems/bing

public final class BingItOn {
    private static Word start = new Word();
    private static final Kattio input = new Kattio(System.in);

    public static void main(String... args){
        fillWord(start);

        int n = Integer.parseInt(input.getWord());
        for(int count = 0; count < n; count++){
            String inStr = input.getWord();
            int length = inStr.length();
            Word currentWord = start;
            boolean newWord = false;
            for(int i = 0; i < length; i++){
                currentWord = currentWord.next[inStr.charAt(i)-'a'];
                if(currentWord.word == null){
                    newWord = true;
                    currentWord.word = inStr.charAt(i);
                    fillWord(currentWord);
                }
                currentWord.occurs++;
            }
            if(newWord){
                System.out.println(0);
            } else {
                System.out.println(currentWord.occurs-1);
            }
        }
    }

    private static void fillWord(Word word){
        word.next = new Word[26];
        for(int i = 0; i < 26; i++){
            word.next[i] = new Word();
        }
    }

    private static final class Word {
        private Character word;
        private int occurs;
        private Word[] next;
    }

    private static final class Kattio extends PrintWriter {
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

}