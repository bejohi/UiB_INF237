import java.util.*;

// Solution for: https://open.kattis.com/problems/bard
public class BardSolver {

    private static final Scanner input = new Scanner(System.in);
    private static final int BARD = 1;

    public static void main(String... args){
        solve();
    }

    // TODO: Split up to sub methods.
    public static void solve(){
        final int villagerCount = input.nextInt();
        final int nightCounter = input.nextInt();
        Set<Integer> wiseGuys = new HashSet<>();
        for(int night = 0; night < nightCounter; night++){
            final int villagersPresent = Integer.parseInt(input.next());
            boolean bardIsPresent = false;
            final Set<Integer> villigersInThisNight = new HashSet<>();
            for(int pos = 0; pos < villagersPresent; pos++){
                int currentVilliger = Integer.parseInt(input.next());
                if(currentVilliger == BARD){
                    bardIsPresent = true;
                }
                villigersInThisNight.add(currentVilliger);
            }
            if(!bardIsPresent){
                wiseGuys.addAll(villigersInThisNight);
            } else {
                wiseGuys.retainAll(villigersInThisNight);
            }
        }
        wiseGuys.add(1);
        wiseGuys.stream().sorted().forEach(System.out::println);
    }
}