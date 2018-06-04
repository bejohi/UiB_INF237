import java.util.*

// Solution for https://open.kattis.com/problems/citrusintern
// With help from https://github.com/amartop

var numberOfEmployees = 0
var input = Scanner(System.`in`)
var costs = mutableListOf<Int>()
var inL = arrayOf<Long>()
var outUpL = arrayOf<Long>()
var outDownL = arrayOf<Long>()
var root = -1

fun init() : Array<MutableList<Int>>{
    numberOfEmployees = input.nextInt()
    val adjList = Array(numberOfEmployees,{ _ -> mutableListOf<Int>()})
    val rootList = BooleanArray(numberOfEmployees,{_ -> true})
    for(i in 0 until numberOfEmployees){
        costs.add(input.nextInt())
        val childs = input.nextInt()
        for(x in 0 until childs){
            val currentChild = input.nextInt()
            adjList[i].add(currentChild)
            rootList[currentChild] = false
        }
    }

    for(i in 0 until numberOfEmployees){
        if(rootList[i]){
            root = i
            break
        }
    }

    return adjList
}


fun solve(adjList: Array<MutableList<Int>>){
    inL = Array<Long>(numberOfEmployees,{ _ -> 0}) // Init to weight
    for(i in 0  until costs.size){
        inL[i] = costs[i].toLong()
    }
    outDownL = Array<Long>(numberOfEmployees,{ _ -> 0})
    outUpL = Array<Long>(numberOfEmployees,{ _ -> 0})
    fillTable2(root,adjList)
    println(Math.min(outDownL[root],inL[root]))

}

fun fillTable2(currentVertex: Int, adjList: Array<MutableList<Int>>){

    if(adjList[currentVertex].isEmpty()){
        outDownL[currentVertex] = Long.MAX_VALUE
        return
    }

    var delta: Long = Long.MAX_VALUE

    for(neighbour in adjList[currentVertex]){
        fillTable2(neighbour,adjList)
        inL[currentVertex] += outUpL[neighbour]
        outUpL[currentVertex] += Math.min(inL[neighbour], outDownL[neighbour])
        outDownL[currentVertex] += Math.min(inL[neighbour], outDownL[neighbour])

        delta = Math.min(Math.max(inL[neighbour]- outDownL[neighbour],0),delta)
    }
    outDownL[currentVertex] += delta
}
fun main(args: Array<String>){
    val adjList = init()
    solve(adjList)
} 