import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("showProjectDetails") {
            group = "MyPlugin"
            description = "Shows project details"

            doLast {
                println("PROJECT DETAILS")
                println("------------------------------------------")
                println("Project name: ${project.name}")
                println("Project group: ${project.group}")
                println("Project version: ${project.version}")
                println("Java version: ${System.getProperty("java.version")}")
                println("Project directory: ${project.rootDir}")

                println("\nPlugins:")
                project.plugins.forEach { plugin ->
                    println(" - ${plugin::class.java.simpleName}")
                }

                val implemConfig = project.configurations.findByName("implementation")
                if (implemConfig != null && implemConfig.dependencies.isNotEmpty()) {
                    println("Dependencies: ")
                    implemConfig.dependencies.forEach { dep ->
                        println(" - ${dep.group}:${dep.name}:${dep.version}")
                    }
                } else {
                    println("\nDependencies: ")
                    println(" dependencies not found")
                }
                println("------------------------------------------")
            }
        }

        project.tasks.register("countSourceLines") {
            group = "MyPlugin"
            description = "Counts the source lines of a project"

            doLast {
                val srcDir = File(project.rootDir, "src")

                if (!srcDir.exists()) {
                    println("srcDir does not exist")
                    return@doLast
                }

                val javaFiles = srcDir.walkTopDown()
                    .filter { it.isFile && it.extension == "java" }
                    .toList()

                val kotlinFiles = srcDir.walkTopDown()
                    .filter { it.isFile && it.extension == "kt" }
                    .toList()

                val allFiles = javaFiles + kotlinFiles

                var totalLines = 0
                var blankLines = 0
                var commentLines = 0
                var codeLines = 0

                allFiles.forEach { file ->
                    file.readLines().forEach { line ->
                        val trimmed = line.trim()
                        totalLines++
                        when {
                            trimmed.isEmpty() -> blankLines++
                            trimmed.startsWith("//")
                                    || trimmed.startsWith("*")
                                    || trimmed.startsWith("/*") -> commentLines++
                            else -> codeLines++
                        }
                    }
                }

                println("CODE STATISTICS")
                println("------------------------------------------")
                println("Files:")
                println("   Java files: ${javaFiles.size}")
                println("   Kotlin files: ${kotlinFiles.size}")
                println("   Total files: ${allFiles.size}")
                println("\nCode lines statistics:")
                println("   Total lines: $totalLines")
                println("   Code lines $codeLines")
                println("   Comment lines: $commentLines")
                println("   Empty lines: $blankLines")

                if (totalLines > 0) {
                    val codePercent = (codeLines * 100) / totalLines
                    println("\n   Code density: $codePercent%")
                }
                println("------------------------------------------")
            }
        }
    }
}