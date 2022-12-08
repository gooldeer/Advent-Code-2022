package advent

import java.io.File

class Day7 : AdventDay {

    fun readInput() = File("input/day7.txt").readLines()

    override fun run(): Any {
        val fs = FileSystem(FsDirectory("/", null))
        populateFileSystem(fs)
//        return fs.root.findAllDirsWhere { it.findSize() <= 100000 }.sumOf { it.findSize() }

        val minSizeRequiredToUpgrade = 30000000 - 70000000 + fs.root.findSize()

        return fs.root.findAllDirsWhere { it.findSize() >= minSizeRequiredToUpgrade }.minOfOrNull { it.findSize() } ?: 0
    }

    fun FsDirectory.findAllDirsWhere(condition: (FsDirectory) -> Boolean): List<FsDirectory> {
        return children
            .filterIsInstance(FsDirectory::class.java)
            .map { it.findAllDirsWhere(condition) }
            .flatten().toMutableList().apply {
                if (condition(this@findAllDirsWhere)) {
                    add(0, this@findAllDirsWhere)
                }
            }
    }

    fun FsDirectory.findSize() : Long = children.sumOf {
        when (it) {
            is FsFile -> it.size
            is FsDirectory -> it.findSize()
            else -> 0
        }
    }

    fun populateFileSystem(fs: FileSystem) {
        var currentDir: FsDirectory = fs.root

        val iterator = readInput().iterator()

        while(iterator.hasNext()) {
            val line = iterator.next()
            if (line.startsWith("$")) {
                val command = line.removePrefix("$ ")
                when {
                    command.startsWith("cd") -> {
                        val arg = command.removePrefix("cd ")
                        currentDir = when (arg) {
                            ".." -> currentDir.parent ?: fs.root
                            "/" -> fs.root
                            else -> currentDir.children
                                .find {it.name == arg && it is FsDirectory } as FsDirectory? ?: FsDirectory(arg, currentDir)
                        }
                    }
                }
            } else when {
                line.startsWith("dir") -> currentDir
                    .children
                    .add(
                        FsDirectory(line.removePrefix("dir "), currentDir)
                    )
                else -> "(\\d+) (.*)"
                    .toRegex()
                    .find(line)
                    ?.let { FsFile(
                        it.destructured.component2(),
                        it.destructured.component1().toLong())
                    }
                    ?.let { currentDir.children.add(it) }
            }
        }
    }

    data class FileSystem(val root: FsDirectory)

    abstract class FsEntry(val name: String)

    class FsFile(name: String, val size: Long): FsEntry(name)

    class FsDirectory(name: String,
                      val parent: FsDirectory?,
                      val children: MutableList<FsEntry> = mutableListOf()): FsEntry(name)
}

