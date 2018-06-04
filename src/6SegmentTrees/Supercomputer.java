import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Solution for https://open.kattis.com/problems/supercomputer
public class Supercomputer {

    private List<Long> anwsers;
    private long numerOfQueries;
    private SegTree tree;

    private static final Scanner input = new Scanner(System.in);

    public static void main(final String... args) {
        final Supercomputer supercomputer = new Supercomputer();
        supercomputer.init();
        supercomputer.solve();
    }

    private void solve() {
        for (int i = 0; i < this.numerOfQueries; i++) {
            String controlChar = input.next();
            switch (controlChar) {
                case "F":
                    this.handleF();
                    break;
                case "C":
                    this.handleC();
                    break;
            }
        }

        this.anwsers.forEach(System.out::println);
    }

    private void handleF() {
        final int pos = input.nextInt();
        if (this.tree.is1(pos)) {
            this.tree.update(pos, 0);
        } else {
            this.tree.update(pos, 1);
        }
    }

    private void handleC() {
        final int left = input.nextInt();
        final int right = input.nextInt();
        this.anwsers.add(this.tree.sum(left, right));
    }

    private void init() {
        this.tree = new SegTree(input.nextInt());
        this.numerOfQueries = input.nextInt();
        this.anwsers = new ArrayList<>();
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

        boolean is1(int position) {
            return this.tree[position + this.offset] == 1;
        }

        void update(int position, final long value) {
            position += this.offset;
            this.tree[position] = value;
            while (position > 1) {
                position /= 2;
                this.tree[position] = this.tree[2 * position] + this.tree[2 * position + 1];
            }
        }

        long sum(final int left, final int right) {
            long sum = 0;
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
                    sum += this.tree[leftPos * 2 + 1];
                }
                if (rightMove) {
                    sum += this.tree[rightPos * 2];
                }
            }

            return sum;
        }


    }
}