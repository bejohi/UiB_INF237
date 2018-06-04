import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;


// Solution for https://open.kattis.com/problems/paintball
public class Paintball3 {
    static final Scanner input = new Scanner(System.in);
    boolean[][] adjMatrix;
    List<List<Integer>> adjList;
    int[] parentArray;
    int sink;
    int source;
    int n;
    int realN;
    int m;

    public static void main(String... args){
        Paintball3 game = new Paintball3();
        game.init();
        game.furdFulkerson();
        game.findPath();
    }

    void init() {
        n = input.nextInt();
        m = input.nextInt();
        realN = n * 2 + 2;
        sink = realN - 1;
        source = 0;
        parentArray = new int[realN];
        adjMatrix = new boolean[realN][realN];
        adjList = new ArrayList<>(realN);
        for(int i = 0; i < realN; i++){
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            final int v1 = input.nextInt();
            final int v2 = input.nextInt();
            adjMatrix[v1][v2 + n] = true;
            adjMatrix[v2][v1 + n] = true;
            adjList.get(v1).add(v2 + n);
            adjList.get(v2).add(v1 + n);
            adjList.get(v2 + n).add(v1);
            adjList.get(v1 + n).add(v2);
        }

        for (int i = 1; i < n + 1; i++) {
            adjMatrix[i + n][sink] = true;
            adjMatrix[source][i] = true;
            adjList.get(i + n).add(sink);
            adjList.get(source).add(i);
            adjList.get(sink).add(i + n);
            adjList.get(i).add(source);

        }
    }

    void furdFulkerson(){
        while(bfs()){
            int v = sink;
            while(v != source){
                int u = parentArray[v];
                adjMatrix[u][v] = !adjMatrix[u][v];
                adjMatrix[v][u] = !adjMatrix[v][u];
                v = u;
            }
        }
    }

     void findPath(){
        List<Integer> resultList = new ArrayList<>();
        for(int i = 1; i <= n;i++){
            for(Integer x : adjList.get(i)){
                if(!adjMatrix[i][x]){
                    if(x-n < 1){
                        System.out.println("Impossible");
                        return;
                    }
                    resultList.add(x-n);
                }
            }
        }
        resultList.forEach(System.out::println);
    }

     boolean bfs(){
        boolean visited[] = new boolean[realN];
        final Queue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(source);
        visited[source] = true;

        while(!queue.isEmpty()){
            int v = queue.poll();
            for (final Integer i : adjList.get(v)) {
                if (!visited[i] && adjMatrix[v][i]){
                    queue.add(i);
                    visited[i] = true;
                    parentArray[i] = v;
                }
            }
        }
        return visited[sink];
    }

}