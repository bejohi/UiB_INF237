// Solution for https://open.kattis.com/problems/islandhopping

#include <iostream>
#include <vector>
#include <algorithm> // For sorting
#include <cmath>


struct Island {
    double x, y;
};

struct Edge {
    unsigned int index1, index2;
    double distance;
};

double distance(Island* island1, Island* island2){
    return sqrt(pow(island1->x - island2->x, 2) + pow(island1->y - island2->y,2));
}

bool edgeSort(Edge const& edge1, Edge const& edge2){
    return edge1.distance < edge2.distance;
}


unsigned int find(unsigned int i, unsigned int* graph){
    if(graph[i] == i){
        return i;
    }
    return find(graph[i],graph);
}

void unionFind(unsigned int i, unsigned int j, unsigned int* graph){
    unsigned int a = find(i,graph);
    unsigned int b = find(j,graph);
    graph[a] = b;
}



double kruskal(int size, Island* islandList){
    double result = 0;
    auto edgeSize = ((size * size) / 2);
    auto * edges = (Edge*) malloc(edgeSize * sizeof(Edge));
    auto * graph = (unsigned int*) malloc(size * sizeof(unsigned int));
    unsigned int edgeCounter = 0;
    for(unsigned int i = 0; i < size; i++){
        for(unsigned int y = i+1; y < size; y++){
            double dist = distance(&islandList[i],&islandList[y]);
            edges[edgeCounter] = Edge{index1:i,index2:y,distance:dist};
            edgeCounter++;
        }
        graph[i] = i;
    }



    std::sort(edges,edges + edgeSize,&edgeSort); // Check if this works.

    for(unsigned int i = 0; i < edgeSize; i++){
        //std::cout << edges[i].distance << std::endl;
        Edge edge = edges[i];
        if(find(edge.index1,graph) != find(edge.index2,graph)){
            result += edge.distance;
            unionFind(edge.index1,edge.index2,graph);
        }
    }

    return result;
}



int main() {
    unsigned int numberOfTestCases;
    unsigned int numberOfIslands;
    double x_read;
    double y_read;
    double* results;

    std::cin >> numberOfTestCases;
    results = (double*) malloc(sizeof(double) * numberOfTestCases);

    for (unsigned int testCase = 0; testCase < numberOfTestCases; testCase++) {
        std::cin >> numberOfIslands;
        auto *islands = static_cast<Island *>(malloc(numberOfIslands * sizeof(Island)));
        for (unsigned int island = 0; island < numberOfIslands; island++) {
            std::cin >> x_read >> y_read;
            islands[island] = Island{x: x_read, y: y_read};
        }
        results[testCase] = kruskal(numberOfIslands,islands);

        free(islands);
    }

    for (unsigned int testCase = 0; testCase < numberOfTestCases; testCase++) {
        std::cout << results[testCase] << std::endl;
    }

    free(results);
}