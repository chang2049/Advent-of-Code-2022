package day07

import asResource

fun main(){
    println("Q1: ${sumAllFoldersLessThan(processFile(),100000)}")
    println("Q2: ${findSmallestFolderLargerThan(processFile(),30000000)}")
}

fun sumAllFoldersLessThan(node: Node, threshold: Int) = mutableSetOf<Node>().let {
        node.traverseNode(it,threshold, isNodeSizeLessThan)
        return@let it
    }.sumOf { it.getValue() }


fun findSmallestFolderLargerThan(node: Node, space:Int)= mutableSetOf<Node>().let {
        node.traverseNode(it, node.getValue()+space-70000000, isNodeSizeLargerThan)
        return@let it
    }.minBy { it.getValue() }.getValue()

val isNodeSizeLessThan:(Node, Int)->Boolean = { node: Node, thred: Int ->
    node.getValue()<=thred && node.next.isNotEmpty()
}

val isNodeSizeLargerThan:(Node, Int)->Boolean = { node: Node, thred: Int ->
    node.getValue()>=thred && node.next.isNotEmpty()
}

fun Node.traverseNode(set: MutableSet<Node>,  thred:Int, condition: (Node, Int)->Boolean){
    if(this.next.isEmpty()) return
    else{
        set.addAll(next.filter {condition(it,thred) })
        next.forEach { it.traverseNode(set, thred, condition) }
    }
}

class Node(val name:String, val prev: Node?, val next: MutableList<Node>, var value: Int?){
    fun getValue(): Int  {
        if(value==null) value = next.sumOf { it.getValue() }
        return value!!
    }
}

fun processFile() = "day07.txt".asResource {
    val head = Node("head", null, mutableListOf(),null)
    var currentNode = head
    it.split("\$ ").forEach { command->
        val lines = command.split("\n")
        when (lines[0]){
            "ls" -> { lines.subList(1,lines.size-1).forEach { file->
                val node = if (file.startsWith("dir")){
                    Node(file.split(" ")[1],currentNode, mutableListOf(),null)
                }else{
                    Node(file.split(" ")[1],currentNode, mutableListOf(),file.split(" ")[0].toInt())
                }
                currentNode.next.add(node)
            }
            }
            "cd /"-> currentNode = head
            "cd .." -> currentNode = currentNode.prev?:head
            else-> {
                if (lines[0].startsWith("cd"))currentNode = currentNode.next.first { it.name ==  lines[0].split(" ")[1]}
            }
        }
    }
    head.getValue()
    return@asResource head
}