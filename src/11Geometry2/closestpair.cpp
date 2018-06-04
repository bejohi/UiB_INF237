#include <iostream>
#include <cmath>
#include <algorithm>

// Solution for https://open.kattis.com/problems/closestpair2
// With help from https://github.com/amartop
struct Point {
    double x, y;
};

struct Pair {
    Point a, b;
    double dist;

    Pair(Point &newA, Point &newB, double newDist) {
        a = newA;
        b = newB;
        dist = newDist;
    }
};


Point points[100001];

bool sX(const Point& a, const Point& b){
    return a.x < b.x || (a.x == b.x && a.y < b.y);
}

bool sY(const Point& a, const Point& b){
    return a.y < b.y || (a.y == b.y && a.x < b.x);
}


Pair divideAndConquer(int low, int high) {

    if (high - low <= 3) {
        Pair rectPair(points[low], points[low], INFINITY);
        for (int i = low; i < high; i++) {
            for (int j = i + 1; j <= high; j++) {

                double distance = std::pow(points[j].x - points[i].x, 2) +
                                  std::pow(points[i].y - points[j].y, 2);

                if (distance < rectPair.dist) {
                    rectPair.a = points[i];
                    rectPair.b = points[j];
                    rectPair.dist = distance;
                }
            }
        }
        return rectPair;
    }

    int mid = (high + low) / 2;
    Pair s1 = divideAndConquer(low, mid);
    Pair s2 = divideAndConquer(mid + 1, high);
    Pair best = s1.dist < s2.dist ? s1 : s2;

    double distance = std::sqrt(best.dist);

    Point leftPoints[high];
    Point rightPoints[high];

    int rightPtr = 0;
    int leftPtr = 0;

    for(int i = mid + 1; i <= high && points[i].x - points[mid].x < distance; i++){
        rightPoints[rightPtr] = points[i];
        rightPtr++;
    }

    for(int i = mid; i >= low && points[mid].x - points[i].x < distance; i--){
        leftPoints[leftPtr] = points[i];
        leftPtr++;
    }

    std::stable_sort(rightPoints,rightPoints+rightPtr,sY);
    std::stable_sort(leftPoints,leftPoints+leftPtr,sY);

    for(int lp = 0, rp = 0; lp < leftPtr;lp++){

        while(rp < rightPtr && rightPoints[rp].y + distance < leftPoints[lp].y){
            rp++;
        }

        int end = std::min(rp+10,rightPtr);
        for(int rpp = rp; rpp < end; rpp++){
            double dist = std::pow(leftPoints[lp].x - rightPoints[rpp].x,2) +
                          std::pow(leftPoints[lp].y - rightPoints[rpp].y,2);
            if(dist < best.dist){
                best.dist = dist;
                best.a = leftPoints[lp];
                best.b = rightPoints[rpp];
            }
        }
    }

    return best;

}

int main() {


    while (true) {
        int n;
        scanf("%d", &n);

        if (n == 0) {

            break;
        }
        for (int i = 0; i < n; i++) {
            scanf("%lf %lf", &points[i].x, &points[i].y);
        }

        // (Point(*)[128])points
        std::stable_sort(points,points+n,sX);
        Pair result = divideAndConquer(0, n - 1);
        printf("%.2lf %.2lf %.2lf %.2lf\n",result.a.x,result.a.y,result.b.x,result.b.y);

    }

    return 0;
}