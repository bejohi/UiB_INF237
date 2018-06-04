import java.util.Scanner;

// Solution for https://open.kattis.com/problems/pivot
public class Pivot {
    private final Scanner input = new Scanner(System.in);
    private int numberOfElements;
    private int[] elements;
    private boolean[] pivots;
    private int pivotCounter;

    public static void main(String... args) {
        final Pivot pivot = new Pivot();
        pivot.initArray();
        pivot.calculatePivot();
        pivot.countPivot();
        System.out.println(pivot.pivotCounter);
    }

    private void calculatePivot() {
        int biggestNumber = Integer.MIN_VALUE;
        for (int i = 0; i < this.numberOfElements; i++) {
            if(this.elements[i] > biggestNumber){
                biggestNumber = this.elements[i];
                    this.pivots[i] = true;
            }
        }
        int smallestNumber = this.elements[this.elements.length-1];
        for (int i = this.numberOfElements-1; i >= 0; i--) {
            if(this.elements[i] > smallestNumber){
                this.pivots[i] = false;
            } if(this.elements[i] < smallestNumber){
                smallestNumber = this.elements[i];
            }
        }
    }

    private void countPivot(){
        for(final Boolean pivot : this.pivots){
            if(pivot){
                this.pivotCounter++;
            }
        }
    }

    private void initArray() {
        this.numberOfElements = input.nextInt();
        this.elements = new int[this.numberOfElements];
        this.pivots = new boolean[this.numberOfElements];
        for (int i = 0; i < this.numberOfElements; i++) {
            this.elements[i] = input.nextInt();
        }
    }

    private boolean checkLeftSideIsSmallerOrEqual(final int pos) {
        for (int i = pos; i >= 0; i--) {
            if (this.elements[i] > this.elements[pos]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRightSideIsBigger(final int pos) {
        for (int i = pos; i < this.numberOfElements; i++) {
            if (this.elements[i] < this.elements[pos]) {
                return false;
            }
        }
        return true;
    }


} 