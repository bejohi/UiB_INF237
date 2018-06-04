import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// @see https://en.wikipedia.org/wiki/Stirling%27s_approximation

// Solution for https://open.kattis.com/problems/howmanydigits
public final class HowManyDigits {

    private static int[] store = new int[1000001];

    public static void main(String... args){
        final Scanner input = new Scanner(System.in);
        final List<Integer> resultList = new ArrayList<>();
        final List<Integer> inputList = new ArrayList<>();
        while(true){
            try{
                int number = Integer.parseInt(input.nextLine());
                inputList.add(number);
            } catch(Exception exception){
                break;
            }
        }
        precompute();
        inputList.forEach(i -> resultList.add(getNumberOfDigits(i)));
        resultList.forEach(System.out::println);

    }

    private static void precompute(){
        store[0] = 1;
        double lnSum = 0;
        for(int i = 1; i < store.length; i++){
            lnSum += Math.log10(i);
            store[i] = (int) Math.floor(lnSum) + 1 ;
        }
    }

    private static int getNumberOfDigits(final int number){
        return store[number];
    }
}

