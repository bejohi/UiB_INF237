import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Solution for https://open.kattis.com/problems/glyphrecognition
public final class GlyphRecognition {

    public static final Scanner input = new Scanner(System.in);
    public static final int MIN_NUM_EDGES = 3;
    public static final int MAX_NUM_EDGES = 8;
    public static final double UPPER_BOUND = 10e16;
    public static final Point ROOT_POINT = new Point(0,0);
    public static final double EPSILON = 10e-9;
    public int n;
    public List<Point> points;
    public int perfectNumberOfCorners;
    public double score;
    public GlyphRecognition(){}

    public static void main(String... args){
        final GlyphRecognition glyphRecognition = new GlyphRecognition();
        glyphRecognition.init();
        glyphRecognition.solve();
        System.out.print(glyphRecognition.n + " " + glyphRecognition.score);
    }

    public List<Point> createPolygon(final double length, final int numberOfEdges){
        final List<Point> points = new ArrayList<>();
        points.add(new Point(length,0));
        double max = 2 * Math.PI;
        double angle = max / numberOfEdges;
        for(double i = angle; i < max; i+= angle){
            points.add(this.rotatePoint(points.get(points.size()-1),angle));
        }

        return points;

    }

    public boolean isInside(List<Point> polygone, Point point){
        Path2D path = new Path2D.Double();

        path.moveTo(polygone.get(0).x, polygone.get(0).y);
        for(int i = 1; i < polygone.size(); ++i) {
            path.lineTo(polygone.get(i).x, polygone.get(i).y);
        }
        path.closePath();
        return path.contains(point.x,point.y);
    }

    public Point rotatePoint(final Point oldPoint, final double angle){
        double newX = oldPoint.x * Math.cos(angle) - oldPoint.y * Math.sin(angle);
        double newY = oldPoint.x * Math.sin(angle) + oldPoint.y * Math.cos(angle);
        return new Point(newX,newY);
    }

    public void init(){
        this.n = input.nextInt();
        this.points = new ArrayList<>(this.n);
        for(int i = 0; i < this.n; i++){
            this.points.add(new Point(input.nextDouble(),input.nextDouble()));
        }
    }

    public boolean allInside(final List<Point> polygone){
        for(final Point point : this.points){
            if(!isInside(polygone,point)){
                return false;
            }
        }
        return true;
    }

    public boolean allNotInside(final List<Point> polygone){
        for(final Point point : this.points){
            if(isInside(polygone,point)){
                return false;
            }
        }
        return true;
    }

    public double binarySearchUpperBound(final int numberOfEdges){
        double low = 0;
        double high = UPPER_BOUND;
        double mid;
        while(high - low > EPSILON){
            mid = (low + high) / 2;
            List<Point> polygone = createPolygon(mid,numberOfEdges);
            boolean allInside = allInside(polygone);
            if(allInside){
                high = mid;
            } else {
                low = mid;
            }
        }

        return high;
    }

    public double binarySearchLowerBound(final int numberOfEdges){
        double low = 0;
        double high = UPPER_BOUND;
        double mid;
        while(high - low > EPSILON){
            mid = (low + high) / 2;
            List<Point> polygone = createPolygon(mid,numberOfEdges);
            boolean allNotInside = allNotInside(polygone);
            if(allNotInside){
                low = mid;
            } else {
                high = mid;
            }
        }

        return low;
    }


    public double getScore(double highValue, double lowValue){
        lowValue *= lowValue;
        highValue *= highValue;
        return lowValue / highValue;

    }

    public void solve(){
        this.score = -1;
        for(int i = MIN_NUM_EDGES; i <= MAX_NUM_EDGES;i++){
            final double highValue = binarySearchUpperBound(i);
            final double lowValue = binarySearchLowerBound(i);
            double currentScore = getScore(highValue,lowValue);
            if(currentScore > this.score){
                this.score = currentScore;
                this.n = i;
            }

        }
    }

    public final static class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}

