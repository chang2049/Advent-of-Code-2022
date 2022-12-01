fun main(){
    val content = "day01.txt".loadFile().split("\n\n")[0]
    println(content)
}
fun String.asResource(work: (String) -> Unit) {
    val content = this.loadFile()
    work(content)
}

fun String.loadFile() = object {}.javaClass.getResource(this)!!.readText()