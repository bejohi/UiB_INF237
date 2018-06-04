import java.util.*


val resultList = mutableListOf<String>()
var done = false

// Solution for https://open.kattis.com/problems/mapcolouring
// With help from https://github.com/amartop

fun main(args: Array<String>){
    val input = Scanner(System.`in`)
    val numberOfTestCases = input.nextInt()

    for(testCase in 1..numberOfTestCases){
        val countries = input.nextInt()
        val borders = input.nextInt()
        val adjMatrix = Array(countries+1,{IntArray(countries+1)})
        var coloredArr = IntArray(countries+1, { _ -> 1})

        for(border in 0 until borders){
            val c1 = input.nextInt()
            val c2 = input.nextInt()
            adjMatrix[c1][c2] = 1
            adjMatrix[c2][c1] = 1
        }

        if(borders == 0){
            resultList.add("1")
            continue
        }
        done = false
        for(i in 1..countries+1){
            solve(i,adjMatrix,0,coloredArr)
        }

    }

    resultList.forEach {println(it)}
}

fun solve(numberOfColors: Int, adjMatrix: Array<IntArray>, currentVertex: Int, vertColored: IntArray){

    if(done){
        return
    }

    val colorOk = checkColor(adjMatrix,currentVertex,vertColored)

    if(!colorOk){
        return
    }

    if(numberOfColors+1 > 4){
        resultList.add("many")
        done = true
        return
    }

    if(currentVertex == adjMatrix.size-1){
        resultList.add((numberOfColors+1).toString())
        done = true
        return
    }

    for(i in 1..numberOfColors+1) {
        vertColored[currentVertex+1] = i
        solve(numberOfColors,adjMatrix,currentVertex+1,vertColored)
    }
    return
}

fun checkColor(adjMatrix: Array<IntArray>, currentVertex: Int, vertColored: IntArray): Boolean {

    for(i in 0 until currentVertex){
        if(adjMatrix[i][currentVertex] != 0 && vertColored[i] == vertColored[currentVertex]){
            return false;
        }
    }
    return true
} 