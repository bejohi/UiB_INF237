import java.util.*
import kotlin.system.exitProcess


// Solution for https://open.kattis.com/problems/coloring
// With help from https://github.com/amartop

fun main(args: Array<String>){
    val input = Scanner(System.`in`)
    val vertices = input.nextLine().toInt()
    val adjMatrix = Array(vertices,{IntArray(vertices)})
    var coloredArr = IntArray(vertices, { x -> 1})

    for(vert in 0 until vertices){
        val line = input.nextLine()
        val numberList = line.split(" ").map { x -> x.toInt() }
        for(number in numberList){
            adjMatrix[vert][number] = 1
            adjMatrix[number][vert] = 1
        }
    }

    for(i in 1..vertices+1){
        recursiveSolve(i,adjMatrix,0,coloredArr)
    }

}

fun recursiveSolve(numberOfColors: Int, adjMatrix: Array<IntArray>, currentVertex: Int, vertColored: IntArray){
    // 1. Verify valid color.
    val colorOk = colorOk(adjMatrix,currentVertex,vertColored)

    if(!colorOk){
        return
    }

    if(currentVertex == adjMatrix.size-1){
        println(numberOfColors+1)
        exitProcess(0)
    }

    for(i in 1..numberOfColors+1) {
        vertColored[currentVertex+1] = i // new color
        recursiveSolve(numberOfColors,adjMatrix,currentVertex+1,vertColored)
    }
}

fun colorOk(adjMatrix: Array<IntArray>, currentVertex: Int, vertColored: IntArray): Boolean {

    for(i in 0..currentVertex-1){
        if(adjMatrix[i][currentVertex] != 0 && vertColored[i] == vertColored[currentVertex]){
            return false;
        }
    }

    /*for(i in 0 until adjMatrix.size){
        if(i == currentVertex){
            continue
        }
        if(adjMatrix[currentVertex][i] != 0){ // Has neighbour?
            if(vertColored[i] == vertColored[currentVertex]){ // Same color?
                return false
            }
        }
    }*/
    return true
}

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start