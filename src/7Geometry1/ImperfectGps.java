import java.util.Scanner;


// Solution for https://open.kattis.com/problems/imperfectgps
public final class ImperfectGps {

    private static final Scanner input = new Scanner(System.in);
    private int numberOfPositions;
    private int timeInterval;
    private int maxTime;
    private Point[] realPoints;
    private Point[] resolutionArray;
    private double distDiffPercentage;


    public static void main(String... args){
        final ImperfectGps game = new ImperfectGps();
        game.init();
        game.initResolutionArray();
        game.solve();
        System.out.println(game.distDiffPercentage);
    }

    private void init(){
        this.numberOfPositions = input.nextInt();
        this.timeInterval = input.nextInt();
        this.realPoints = new Point[this.numberOfPositions];
        for(int i = 0; i < this.numberOfPositions; i++){
            final Point point = new Point(input.nextInt(),input.nextInt(),input.nextInt());
            this.realPoints[i] = point;
        }
        this.maxTime = (int) this.realPoints[this.numberOfPositions-1].time;
        this.resolutionArray = new Point[this.maxTime+1];
    }

    private void initResolutionArray(){
        int arrPtr = 0;
        int part = 0;
        double xDiff = 0;
        double yDiff = 0;
        double timeDiff = 0;
        for(int i = 0; i < this.maxTime; i++){
            if(i >= this.realPoints[arrPtr].time || arrPtr == 0){
                arrPtr++;
                part = 0;
                xDiff = this.realPoints[arrPtr].x - this.realPoints[arrPtr-1].x;
                yDiff = this.realPoints[arrPtr].y - this.realPoints[arrPtr-1].y;
                timeDiff = this.realPoints[arrPtr].time - this.realPoints[arrPtr-1].time;
            }
            double x = this.realPoints[arrPtr-1].x + (xDiff / timeDiff) * part;
            double y = this.realPoints[arrPtr-1].y + (yDiff / timeDiff) * part;
            final Point point = new Point(x,y,i);
            this.resolutionArray[i] = point;
            part++;
        }
        this.resolutionArray[this.maxTime] = new Point(this.realPoints[this.numberOfPositions-1],this.maxTime);
    }

    private void solve(){
        double realDistance = 0;
        double gpsDistance = 0;
        for(int i = 1; i <= this.maxTime; i++){
            realDistance += Point.distance(this.resolutionArray[i],this.resolutionArray[i-1]);
        }
        for(int i = 0; i <= this.maxTime; i+=this.timeInterval){
            if(i+this.timeInterval > maxTime){
                gpsDistance += Point.distance(this.resolutionArray[i],this.resolutionArray[this.maxTime]);
                break;
            }
            gpsDistance += Point.distance(this.resolutionArray[i],this.resolutionArray[i+this.timeInterval]);
        }
        double one = realDistance / 100;
        this.distDiffPercentage = (realDistance - gpsDistance) / one;
    }

    private static final class Point {
        private final double x;
        private final double y;
        private double time;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        private Point(double x, double y, double time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        private Point(Point point, double time) {
            this.x = point.x;
            this.y = point.y;
            this.time = time;
        }

        private static double distance(Point p1, Point p2){
            double a = Math.abs(p1.x - p2.x);
            double b = Math.abs(p1.y - p2.y);
            return Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
        }

    }

}

