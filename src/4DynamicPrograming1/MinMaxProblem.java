import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Solution for https://open.kattis.com/problems/maximizingwinnings
public final class MinMaxProblem {

    private static final List<TestCase> testCases = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String... args) {
        TestCase testCase = TestCase.createFromInput(input);
        while (testCase != null) {
            testCases.add(testCase);
            testCase = TestCase.createFromInput(input);
        }

        for (final TestCase tC : testCases) {
            tC.solve();
            System.out.print(tC.maxResult + " " + tC.minResult + "\n");
        }
    }


    private static class TestCase {
        private static final int NOT_MEMORIZED = -1;
        private int[][] adjMatrix;
        private int n;
        private int rounds;
        private int minResult;
        private int maxResult;
        private int[][] minCache;
        private int[][] maxCache;

        private TestCase() {
        }

        void solve(){
            this.maxResult = 0;
            this.minResult = Integer.MAX_VALUE;
            for(int room = 0; room < this.n; room++){
               int currentMax = subsolveMax(room,rounds);
               int currentMin = subsolveMin(room,rounds);
               if(currentMax > maxResult){
                   maxResult = currentMax;
               }
               if(currentMin < minResult){
                   minResult = currentMin;
               }
            }
        }

        int subsolveMin(int room, int round){
            if(round == 0){
                return 0;
            }
            if(round == 1){
                return this.adjMatrix[0][room];
            }

            int min = Integer.MAX_VALUE;
            for(int subroom = 0; subroom < this.n; subroom++){
                if(this.minCache[round-1][subroom] == NOT_MEMORIZED){
                    this.minCache[round-1][subroom] = subsolveMin(subroom,round-1);
                }
                int currentValue = this.minCache[round-1][subroom] + this.adjMatrix[subroom][room];
                if(currentValue < min){
                    min = currentValue;
                }
            }

            this.minCache[round][room] = min;
            return this.minCache[round][room];
        }

        int subsolveMax(int room, int round){
            if(round == 0){
                return 0;
            }
            if(round == 1){
                return this.adjMatrix[0][room];
            }

            int max = 0;
            for(int subroom = 0; subroom < this.n; subroom++){
                if(this.maxCache[round-1][subroom] == NOT_MEMORIZED){
                    this.maxCache[round-1][subroom] = subsolveMax(subroom,round-1);
                }
                int currentValue = this.maxCache[round-1][subroom] + this.adjMatrix[subroom][room];
                if(currentValue > max){
                    max = currentValue;
                }
            }

            this.maxCache[round][room] = max;
            return this.maxCache[round][room];
        }

        static TestCase createFromInput(Scanner input) {
            TestCase testCase = new TestCase();
            testCase.n = input.nextInt();
            testCase.adjMatrix = new int[testCase.n][testCase.n];
            if (testCase.n == 0) {
                return null;
            }
            for (int y = 0; y < testCase.n; y++) {
                for (int x = 0; x < testCase.n; x++) {
                    testCase.adjMatrix[y][x] = input.nextInt();
                }
            }
            testCase.rounds = input.nextInt();
            testCase.maxCache = new int[testCase.rounds + 1][testCase.n];
            testCase.minCache = new int[testCase.rounds + 1][testCase.n];
            for (int y = 0; y <= testCase.rounds; y++) {
                for (int x = 0; x < testCase.n; x++) {
                    if (y == 0) {
                        testCase.maxCache[y][x] = 0;
                        testCase.minCache[y][x] = 0;
                        continue;
                    }
                    testCase.maxCache[y][x] = NOT_MEMORIZED;
                    testCase.minCache[y][x] = NOT_MEMORIZED;
                }
            }
            return testCase;
        }

    }
}

