import java.util.Scanner;


// Solution for https://open.kattis.com/problems/muzicari
public final class Muzicari {

    private final Scanner input = new Scanner(System.in);
    private int[] musicians;
    private int durationOfConcert;
    private int numberOfMusicans;
    private int[][] knapsack;
    private boolean[][] keep;
    private int[] breakBegin;

    public static void main(String... args) {
        final Muzicari muzicari = new Muzicari();
        muzicari.initConcert();
        muzicari.fillKnapsack();
        muzicari.backtrackKnapsack();
        muzicari.fillRestBreaks();
        muzicari.printOutResult();
    }

    private void initConcert() {
        this.durationOfConcert = input.nextInt();
        this.numberOfMusicans = input.nextInt();
        this.musicians = new int[this.numberOfMusicans];
        for (int i = 0; i < this.numberOfMusicans; i++) {
            this.musicians[i] = input.nextInt();
        }
    }

    private void backtrackKnapsack(){
        this.breakBegin = new int[this.numberOfMusicans];
        for(int i = 0; i < this.breakBegin.length; i++){
            this.breakBegin[i] = -1;
        }
        int breakCapacity = this.durationOfConcert;
        int currentMusican = this.numberOfMusicans;
        int timeCounter = 0;
        while(currentMusican > 0){
            if(!this.keep[currentMusican][breakCapacity]){
                currentMusican--;
            } else {
                breakCapacity -= this.musicians[currentMusican-1];
                this.breakBegin[currentMusican-1] = timeCounter;
                timeCounter += this.musicians[currentMusican-1];
                currentMusican--;
            }
        }
    }

    private void fillRestBreaks(){
        int timeCounter = 0;
        for(int i = 0; i < this.breakBegin.length;i++){
            if(breakBegin[i] == -1){
                breakBegin[i] = timeCounter;
                timeCounter += this.musicians[i];
            }
        }
    }

    private void printOutResult(){
        for(int i = 0; i  < this.breakBegin.length; i++){
            System.out.print(this.breakBegin[i] + " ");
        }
    }

    private void fillKnapsack() {

        this.knapsack = new int[this.numberOfMusicans + 1][this.durationOfConcert + 1];
        this.keep = new boolean[this.numberOfMusicans + 1][this.durationOfConcert + 1];

        for (int i = 0; i <= this.numberOfMusicans; i++) {
            for (int w = 0; w <= this.durationOfConcert; w++) {
                if (i == 0 || w == 0) {
                    knapsack[i][w] = 0;
                    keep[i][w] = false;
                } else if (this.musicians[i - 1] <= w) {
                    if (this.musicians[i - 1] + knapsack[i - 1][w - this.musicians[i - 1]] > knapsack[i - 1][w]) {
                        knapsack[i][w] = this.musicians[i - 1] + knapsack[i - 1][w - this.musicians[i - 1]];
                        keep[i][w] = true;
                    } else {
                        knapsack[i][w] = knapsack[i - 1][w];
                        keep[i][w] = false;
                    }
                } else {
                    knapsack[i][w] = knapsack[i - 1][w];
                    keep[i][w] = false;
                }
            }
        }
    }

}