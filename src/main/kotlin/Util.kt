fun <T : Any>String.asResource(work: (String) -> T): T {
    val content = this.loadFile()
    return work(content)
}

fun String.loadFile() = object {}.javaClass.getResource(this)!!.readText()
