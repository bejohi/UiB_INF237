import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


// Solution for https://open.kattis.com/problems/turbo
public class Turbo {

    private static final Scanner input = new Scanner(System.in);
    private int[] arr;
    private SegTree segTree;
    private int[] answers;
    private int[] positions;
    public static void main(String... args){
        final Turbo turbo = new Turbo();
        turbo.init();
        turbo.solve();
        turbo.print();
    }

    private void print(){
        for(int i : answers){
            System.out.println(i);
        }
    }

    private void solve(){
        int low = 0;
        int high = this.arr.length -1;
        for(int i = 0; i < this.arr.length;i++){
            if(i % 2 == 0){
                final int realPosition = this.positions[low+1] + this.segTree.val(this.positions[low+1]);
                final int answer = Math.abs(low-realPosition);
                this.answers[i] = answer;
                this.segTree.update(0,this.positions[low+1],1);
                low++;
            } else {
                final int realPosition = this.positions[high+1] + this.segTree.val(this.positions[high+1]);
                final int answer = Math.abs(high-realPosition);
                this.answers[i] = answer;
                this.segTree.update(this.positions[high+1],this.arr.length-1,-1);
                high--;
            }
        }
        /*for(int x = 0; x < this.arr.length; x++){
            System.out.println(x + ": " + this.positions[x] + this.segTree.val(this.positions[x]));
        }*/
    }

    private void init(){
        final int arrSize = input.nextInt();
        this.arr = new int[arrSize];
        this.positions = new int[arrSize+1];
        for(int i = 0; i < arrSize; i++){
            this.arr[i] = input.nextInt();
            this.positions[this.arr[i]] = i;
        }
        this.segTree = new SegTree(arrSize);
        this.answers = new int[arrSize];
    }


    private static final class SegTree {
        private long[] tree;
        private int offset;

        private SegTree(final int length) {
            int size = 2;
            while ((length * 2) + 3 > size) {
                size *= 2;
            }
            this.tree = new long[size];
            this.offset = 2;
            while (2 + length > this.offset) {
                this.offset *= 2;
            }
        }

        int val(int position){
            position += this.offset;
            int answer = 0;
            while(position > 1){
                answer += this.tree[position];
                position /= 2;
            }

            return answer;
        }

        void update(final int left, final int right, int value) {
            int leftPos = left + this.offset - 1;
            int rightPos = right + this.offset + 1;
            while (true) {
                final boolean leftMove = leftPos % 2 == 0;
                final boolean rightMove = rightPos % 2 == 1;
                leftPos /= 2;
                rightPos /= 2;
                if (leftPos == rightPos) {
                    break;
                }
                if (leftMove) {
                    this.tree[leftPos * 2 + 1] += value;
                }
                if (rightMove) {
                    this.tree[rightPos * 2] += value;
                }
            }
        }


    }
}